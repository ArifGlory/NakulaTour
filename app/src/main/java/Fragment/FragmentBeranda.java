package Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.AdapterDestinasi;
import Kelas.PaketTour;
import Kelas.SharedVariable;
import Kelas.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.travelpms.BerandaActivity;
import myproject.travelpms.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBeranda extends Fragment {


    public FragmentBeranda() {
        // Required empty public constructor
    }

    TextView txtNotif;
    public static ProgressBar progressBar;

    private RecyclerView recyclerView;
    AdapterDestinasi adapterDestinasi;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    private List<PaketTour> paketTourList;
    String level,paketTour;
    UserPreference mUserpref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_container, container, false);


        mUserpref = new UserPreference(this.getActivity());

        paketTourList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        adapterDestinasi = new AdapterDestinasi(this.getActivity(),paketTourList);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterDestinasi);

        pDialogLoading = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Menampilkan data..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();


        getDataPaket();

        return view;
    }

    private void getDataPaket(){
        String url = SharedVariable.ipServer+"/Paket/listPaket/";

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
                Log.d("getlaporan:","eror "+error.getMessage().toString());
                Toast.makeText(getActivity(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showPaket(String response) throws JSONException {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        paketTourList.clear();
        adapterDestinasi.notifyDataSetChanged();

        JSONArray jsonArray2 = new JSONArray(response);
        Log.d("ukuranJarray",""+jsonArray2.length());


        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);
            Log.d("arrayPaket:",""+jojo.toString());

            int idPaket = Integer.parseInt(jojo.getString("no"));
            String namaPaket = jojo.getString("paket");
            String namaFoto = jojo.getString("foto");
            String downloadUrl = "https://nakulatour.com/public/assets/admin/gambar/paket/"+namaFoto;
            String keterangan = jojo.getString("keterangan");

            PaketTour paketTour = new PaketTour(idPaket,
                    namaPaket,
                    keterangan,
                    downloadUrl
            );

            paketTourList.add(paketTour);
            adapterDestinasi.notifyDataSetChanged();

        }

    }



}
