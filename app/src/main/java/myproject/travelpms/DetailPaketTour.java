package myproject.travelpms;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import Kelas.PaketTour;
import Kelas.Rating;
import Kelas.SharedVariable;
import Kelas.Wisata;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailPaketTour extends AppCompatActivity implements RatingDialogListener {

    RecyclerView recyclerView;
    Intent i;
    TextView namaPakettxtDiskon;
    ImageView backdrop,imgRate;
    Button btnFasilitas,btnItinenary,btnUlasan;
    private SweetAlertDialog pDialogInfo,pDialogLoading;
    RelativeLayout relaRating;
    AppBarLayout appbar;
    PaketTour paketTour;
    ArrayList<Integer> arrayRating = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_paket_tour);


        i = getIntent();
        paketTour = (PaketTour) i.getSerializableExtra("paketTour");

        backdrop = findViewById(R.id.backdrop);
        btnFasilitas = findViewById(R.id.btnFasilitas);
        relaRating = findViewById(R.id.relaRating);
        imgRate = findViewById(R.id.imgRate);
        appbar = findViewById(R.id.appbar);
        btnItinenary = findViewById(R.id.btnItinenary);
        btnUlasan = findViewById(R.id.btnUlasan);



        recyclerView = findViewById(R.id.recycler_view);







    }

    private void showDialogRating(){
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(4)
                .setTitle("Berikan Penilaian mu")
                .setDescription("Silakan berikan penilaian mu tentang paket tour ini")
                .setCommentInputEnabled(true)
                .setDefaultComment("Keren banget !")
                .setStarColor(R.color.startblue)
                .setNoteDescriptionTextColor(R.color.kuningGelap)
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimaryDark)
                .setHint("Tulis komentar mu disini ...")
                .setHintTextColor(R.color.colorlight2)
                .setCommentTextColor(R.color.album_title)
                .setCommentBackgroundColor(R.color.photo_placeholder)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(DetailPaketTour.this)
               // .setTargetFragment(this, TAG) // only if listener is implemented by fragment
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {
      //  Toast.makeText(getApplicationContext(),"submit",Toast.LENGTH_SHORT).show();


    }
}
