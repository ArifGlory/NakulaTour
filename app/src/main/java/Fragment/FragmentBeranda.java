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

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterDestinasi;
import Kelas.PaketTour;
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

       /* pDialogLoading = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Menampilkan data..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();*/

        PaketTour paketTour1 = new PaketTour(
                "Paket Bali",
                "Bus Pariwisata AC, LCD + VCD, Reclining seat, toilet & non toilet\n" +
                        "Hotel Denpasar AC 1 malam : 1 kamar 4 orang,\n" +
                        "Makan 7x prasmanan,\n" +
                        "Persediaan P3K perjalanan,\n" +
                        "Free retribusi, parkir, fee sopir,\n" +
                        "Free banner wisata,\n" +
                        "Free dokumentasi shooting,\n" +
                        "Didampingi Tour Leader di setiap bus,\n" +
                        "Snack + Air mineral,\n" +
                        "Dipandu Guide asli Bali bersertifikat HPI,\n" +
                        "Free ticket masuk 9 objek wisata ( atau sesuai konfirmasi terlebih dahulu ),\n" +
                        "Free biaya penyeberangan Ketapang - Gilimanuk PP,\n" +
                        "Free biaya angkutan komotra akses pantai."
                ,
                "https://firebasestorage.googleapis.com/v0/b/sipat-a0ea2.appspot.com/o/images%2Fcs0zzHe7NObAMhIjOx02ZruIX4k1%2Fcs0zzHe7NObAMhIjOx02ZruIX4k1_20190129_092409?alt=media&token=02e01725-1d52-4d7d-997a-40ad14c3e80a"
        );

        PaketTour paketTour2 = new PaketTour(
                "Paket Jogja",
                "Bus Pariwisata AC, LCD + VCD, Reclining seat, toilet & non toilet,\n" +
                        "Makan 6x prasmanan / rice box,\n" +
                        "Persediaan P3K perjalanan,\n" +
                        "Free retribusi, parkir, fee sopir,\n" +
                        "Free banner wisata,\n" +
                        "Free dokumentasi shooting,\n" +
                        "Didampingi Tour Leader di setiap bus,\n" +
                        "Snack + Air mineral,\n" +
                        "Free ticket masuk 6 objek wisata ( atau sesuai konfirmasi terlebih dahulu ),\n" +
                        "Reservasi Hotel AC 1 malam : 1 kamar 4 orang (sekitar malioboro).\n" +
                        ""
                ,
                "https://firebasestorage.googleapis.com/v0/b/sipat-a0ea2.appspot.com/o/nakula%2Fjogja.jpeg?alt=media&token=3ca34cc9-c128-4422-b087-eff49d606eb5"
        );

        paketTourList.add(paketTour1);
        paketTourList.add(paketTour2);
        adapterDestinasi.notifyDataSetChanged();




        return view;
    }




}
