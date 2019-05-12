package myproject.travelpms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import Kelas.PaketTour;
import Kelas.PemesananPaket;
import Kelas.SharedVariable;
import Kelas.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static Kelas.SharedVariable.email;

public class PesanPaketActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    Intent intent;
    PaketTour paketTour;
    UserPreference mUserpref;
    private SweetAlertDialog pDialogLoading;
    Button btnPesan,btnTanggal;
    EditText etTglBerangkat,etNamaRombongan,etJmlTransport,etJmlPeserta,etLokasiJemput,etAlamat;
    Spinner spJK,sp_transportasi,sp_status_jalan;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String jenisKelamin,statusJalan,jenisTransportasi;
    HttpResponse response;
    HttpEntity entity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_paket);
        mUserpref = new UserPreference(this);

        intent = getIntent();
        paketTour = (PaketTour) intent.getSerializableExtra("paketTour");
        mUserpref = new UserPreference(this);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle(paketTour.getNamaPaket());

        dateFormatter   = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        etTglBerangkat  = findViewById(R.id.etTglBerangkat);
        etNamaRombongan = findViewById(R.id.etNamaRombongan);
        etJmlPeserta    = findViewById(R.id.etJmlPeserta);
        etJmlTransport  = findViewById(R.id.etJmlTransport);
        etLokasiJemput  = findViewById(R.id.etLokasiJemput);
        etAlamat  = findViewById(R.id.etAlamat);
        spJK            = findViewById(R.id.spJK);
        sp_status_jalan = findViewById(R.id.sp_status_jalan);
        sp_transportasi = findViewById(R.id.sp_transportasi);
        btnPesan        = findViewById(R.id.btnPesan);
        btnTanggal      = findViewById(R.id.btnTanggal);

        jenisKelamin        = "";
        statusJalan         = "";
        jenisTransportasi   = "";

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        spJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisKelamin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_transportasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisTransportasi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_status_jalan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusJalan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getDataUser();

    }

    private void clearElement(){
        etTglBerangkat.setText("");
        etNamaRombongan.setText("");
        etJmlPeserta.setText("");
        etJmlTransport.setText("");
        etLokasiJemput.setText("");
        etAlamat.setText("");

        jenisTransportasi   = "";
        jenisKelamin        = "";
        statusJalan         = "";
    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etTglBerangkat.setText(""+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void pesanPaket(final PemesananPaket pemesananPaket){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {


                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("tanggal_berangkat", pemesananPaket.getTanggal_berangkat()));
                nameValuePairs.add(new BasicNameValuePair("nama", pemesananPaket.getNama()));
                nameValuePairs.add(new BasicNameValuePair("nama_rombongan", pemesananPaket.getNama_rombongan()));
                nameValuePairs.add(new BasicNameValuePair("jk", pemesananPaket.getJk()));
                nameValuePairs.add(new BasicNameValuePair("paket", pemesananPaket.getPaket()));
                nameValuePairs.add(new BasicNameValuePair("transportasi", pemesananPaket.getTransportasi()));
                nameValuePairs.add(new BasicNameValuePair("jumlah_peserta", pemesananPaket.getJumlah_peserta()));
                nameValuePairs.add(new BasicNameValuePair("jumlah_transportasi", pemesananPaket.getJumlah_transportasi()));
                nameValuePairs.add(new BasicNameValuePair("status", pemesananPaket.getStatus()));
                nameValuePairs.add(new BasicNameValuePair("hp", pemesananPaket.getHp()));
                nameValuePairs.add(new BasicNameValuePair("alamat", pemesananPaket.getAlamat()));
                nameValuePairs.add(new BasicNameValuePair("lokasi_penjemputan", pemesananPaket.getLokasi_penjemputan()));
                nameValuePairs.add(new BasicNameValuePair("idPelanggan", pemesananPaket.getIdPelanggan()));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            SharedVariable.ipServer+"/Paket/pemesananPaket/");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    response = httpClient.execute(httpPost);
                    entity = response.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }

                return "success";
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200){
                    pDialogLoading.dismiss();
                    clearElement();

                    try {
                        showPemesanan(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new SweetAlertDialog(PesanPaketActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("Staff kami akan segera menghubungi anda")
                            .setTitleText("Pemesanan Berhasil")
                            .show();
                }else {
                    pDialogLoading.dismiss();

                    try {
                        showPemesanan(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new SweetAlertDialog(PesanPaketActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Error")
                            .show();
                }

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    private void showPemesanan(String response) throws JSONException {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();


        JSONArray jsonArray2 = new JSONArray(response);
        Log.d("ukuranPemesananArray",""+jsonArray2.length());

        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);
            Log.d("jsonPemesanan:",""+jojo);
        }


    }

    private void checkValidation() {

        // Get all edittext texts
        String getTglBerangkat = etTglBerangkat.getText().toString();
        String getNamaRombongan = etNamaRombongan.getText().toString();
        String getJmlPeserta = etJmlPeserta.getText().toString();
        String getJmlTransport = etJmlTransport.getText().toString();
        String getLokasiJemput = etLokasiJemput.getText().toString();
        String getAlamat = etAlamat.getText().toString();

        // Check if all strings are null or not
        if (getTglBerangkat.equals("") || getTglBerangkat.length() == 0
                || getNamaRombongan.equals("") || getNamaRombongan.length() == 0
                || getJmlPeserta.equals("") || getJmlPeserta.length() == 0
                || getLokasiJemput.equals("") || getLokasiJemput.length() == 0
                || getAlamat.equals("") || getAlamat.length() == 0
                || jenisKelamin.equals("") || jenisKelamin.length() == 0 || jenisKelamin.equals("Pilih")
                || statusJalan.equals("") || statusJalan.length() == 0 || statusJalan.equals("Pilih")
                || jenisTransportasi.equals("") || jenisTransportasi.length() == 0 || jenisTransportasi.equals("Pilih")
                || getJmlTransport.equals("") || getJmlTransport.length() == 0) {

            new SweetAlertDialog(PesanPaketActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops..")
                    .setContentText("Semua data harus diisi")
                    .show();
        }else {
            PemesananPaket pemesananPaket = new PemesananPaket(
                getTglBerangkat,
                mUserpref.getNama(),
                getNamaRombongan,
                jenisKelamin,
                paketTour.getNamaPaket(),
                jenisTransportasi,
                getJmlPeserta,
                getJmlTransport,
                statusJalan,
                mUserpref.getNope(),
                getAlamat,
                getLokasiJemput,
                mUserpref.getIdUser()
            );
            pesanPaket(pemesananPaket);
        }
    }

    private void getDataUser(){
        String idUser = mUserpref.getIdUser().replace("\"", "");
        String url = SharedVariable.ipServer+"/User/getDataUser/"+idUser;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {
                    showPaket(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getDataUser:","eror "+error.getMessage().toString());
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showPaket(String response) throws JSONException {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();


        JSONArray jsonArray2 = new JSONArray(response);
        Log.d("ukuranJarray",""+jsonArray2.length());

        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);

            String namaUser = jojo.getString("namaPelanggan");
            String noHape = jojo.getString("no_hape");

            mUserpref.setNama(namaUser);
            mUserpref.setNope(noHape);

        }


    }
}
