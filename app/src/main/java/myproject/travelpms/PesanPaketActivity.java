package myproject.travelpms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Kelas.PaketTour;
import Kelas.SharedVariable;
import Kelas.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PesanPaketActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    Intent intent;
    PaketTour paketTour;
    UserPreference mUserpref;
    private SweetAlertDialog pDialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_paket);
        mUserpref = new UserPreference(this);

        intent = getIntent();
        paketTour = (PaketTour) intent.getSerializableExtra("paketTour");

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle(paketTour.getNamaPaket());

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();


        getDataUser();

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
