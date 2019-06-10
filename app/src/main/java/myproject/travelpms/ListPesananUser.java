package myproject.travelpms;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.AdapterPemesanan;
import Kelas.PemesananPaket;
import Kelas.SharedVariable;
import Kelas.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListPesananUser extends AppCompatActivity {

    DatabaseReference ref,refUser;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private RecyclerView recyclerView;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    AdapterPemesanan adapter;
    UserPreference mUserpref;
    private List<PemesananPaket> pemesananList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pesanan_user);

        recyclerView    = findViewById(R.id.recycler_view);
        pemesananList   = new ArrayList<>();
        adapter         = new AdapterPemesanan(this,pemesananList);
        mUserpref = new UserPreference(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Menampilkan data..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

        getPemesananData();
    }

    private void getPemesananData(){
        Log.d("idUser:",""+mUserpref.getIdUser());
        String url = SharedVariable.ipServer+"/Paket/listPesananUser/"+mUserpref.getIdUser();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {
                    showPemesanan(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("getpemesanan:","eror "+error.getMessage().toString());
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showPemesanan(String response) throws JSONException {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        pemesananList.clear();
        adapter.notifyDataSetChanged();

        JSONArray jsonArray2 = new JSONArray(response);
        Log.d("ukuranArrayPemesanan",""+jsonArray2.length());

        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);
            Log.d("arrayPemesanan:",""+jojo.toString());

            String kode = jojo.getString("kode");
            String tanggal_berangkat = jojo.getString("tanggal_berangkat");
            String nama = jojo.getString("nama");
            String nama_rombongan = jojo.getString("nama_rombongan");
            String jk = jojo.getString("jk");
            String paket = jojo.getString("paket");
            String transportasi = jojo.getString("transportasi");
            String jumlah_peserta = jojo.getString("jumlah_peserta");
            String jumlah_transportasi = jojo.getString("jumlah_transportasi");
            String status = jojo.getString("status");
            String pembayaran = jojo.getString("pembayaran");
            String hp = jojo.getString("hp");
            String alamat = jojo.getString("alamat");
            String lokasi_penjemputan = jojo.getString("lokasi_penjemputan");
            String idPelanggan = jojo.getString("idPelanggan");
            String konfirmasi = jojo.getString("konfirmasi");

            PemesananPaket pemesananPaket = new PemesananPaket(
                   tanggal_berangkat,
                   nama,
                   nama_rombongan,
                   jk,
                   paket,
                   transportasi,
                   jumlah_peserta,
                   jumlah_transportasi,
                   status,
                   hp,
                   alamat,
                   lokasi_penjemputan,
                   idPelanggan
            );
            pemesananPaket.setKode(kode);
            pemesananPaket.setKonfirmasi(konfirmasi);
            pemesananPaket.setPembayaran(pembayaran);
            Log.d("lokasi_jemput:",""+lokasi_penjemputan);

            pemesananList.add(pemesananPaket);
            adapter.notifyDataSetChanged();
        }
        Log.d("isiPemesananList:",""+pemesananList.size());


    }
}
