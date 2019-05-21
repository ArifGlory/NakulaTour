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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.AdapterDestinasi;
import Adapter.AdapterPemesanan;
import Kelas.PaketTour;
import Kelas.PemesananPaket;
import Kelas.SharedVariable;
import Kelas.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListPromoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    AdapterDestinasi adapter;
    UserPreference mUserpref;
    private List<PaketTour> paketTourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_promo);

        recyclerView    = findViewById(R.id.recycler_view);
        paketTourList   = new ArrayList<>();
        adapter         = new AdapterDestinasi(this,paketTourList);
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

        getDataPromo();
    }

    private void getDataPromo(){
        String url = SharedVariable.ipServer+"/Paket/listPromo/";

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
                pDialogLoading.dismiss();
                Log.d("getlaporan:","eror "+error.getMessage().toString());
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

        paketTourList.clear();
        adapter.notifyDataSetChanged();

        JSONArray jsonArray2 = new JSONArray(response);
        Log.d("ukuranJarray",""+jsonArray2.length());


        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);
            Log.d("arrayPaket:",""+jojo.toString());

            int idPaket = Integer.parseInt(jojo.getString("no"));
            String namaPaket = jojo.getString("judul");
            String namaFoto = jojo.getString("gambar");
            String downloadUrl = "https://nakulatour.com/public/assets/images/Promo/"+namaFoto;
            String keterangan = jojo.getString("keterangan");
            String harga = jojo.getString("harga");

            PaketTour paketTour = new PaketTour(idPaket,
                    namaPaket,
                    keterangan,
                    downloadUrl
            );
            paketTour.setHarga(harga);

            paketTourList.add(paketTour);
            adapter.notifyDataSetChanged();

        }

    }
}
