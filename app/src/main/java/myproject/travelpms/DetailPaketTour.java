package myproject.travelpms;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import Kelas.PaketTour;
import Kelas.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailPaketTour extends AppCompatActivity   {

    RecyclerView recyclerView;
    Intent i;
    ImageView backdrop,imgRate;
    private SweetAlertDialog pDialogInfo,pDialogLoading;
    AppBarLayout appbar;
    Button btnPesan;
    PaketTour paketTour;
    ArrayList<Integer> arrayRating = new ArrayList<>();
    TextView txtNamaPaket,txtKeterangan,txtHarga;
    UserPreference mUserpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_paket_tour);
        mUserpref = new UserPreference(this);

        i = getIntent();
        paketTour = (PaketTour) i.getSerializableExtra("paketTour");
        btnPesan = findViewById(R.id.btnPesan);
        backdrop = findViewById(R.id.backdrop);
        txtKeterangan = findViewById(R.id.txtKeterangan);
        txtNamaPaket = findViewById(R.id.txtNamaPaket);
        txtHarga = findViewById(R.id.txtHarga);

        txtNamaPaket.setText(paketTour.getNamaPaket());
        txtKeterangan.setText(paketTour.getKeteranganPaket());

        Glide.with(this)
                .load(paketTour.getDownloadUrl())
                .into(backdrop);
        Log.d("harga:",""+paketTour.getHarga());

        if (paketTour.getHarga().length() != 0 && paketTour.getHarga() != null ){
            int harga  = Integer.parseInt(paketTour.getHarga());

            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            txtHarga.setText(""+formatRupiah.format((double) harga));
            txtHarga.setVisibility(View.VISIBLE);
        }

        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUserpref.getIsLoggedIn().equals("no")){
                    new SweetAlertDialog(DetailPaketTour.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sorry")
                            .setContentText("Fitur ini hanya untuk pengguna, silakan login terlebih dahulu.")
                            .show();
                }else {
                    i = new Intent(getApplicationContext(),PesanPaketActivity.class);
                    i.putExtra("paketTour",paketTour);
                    startActivity(i);
                }

            }
        });


    }


}
