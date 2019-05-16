package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import Kelas.PaketTour;
import Kelas.PemesananPaket;
import myproject.travelpms.DetailPaketTour;
import myproject.travelpms.R;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterPemesanan extends RecyclerView.Adapter<AdapterPemesanan.MyViewHolder> {

    private Context mContext;

    private List<PemesananPaket> pemesananList;
    private SimpleDateFormat dateFormatter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNamaPaket,txtTime,txtNamaUser,txtJmlPeserta,txtJenisTransportasi,txtJmlTransportasi,txtAlamat
                ,txtKonfirmasi;
        public LinearLayout lyt_parent;

        public MyViewHolder(View view) {
            super(view);
            txtNamaPaket =  view.findViewById(R.id.txtNamaPaket);
            txtTime =  view.findViewById(R.id.txtTime);
            txtNamaUser =  view.findViewById(R.id.txtNamaUser);
            txtJmlPeserta =  view.findViewById(R.id.txtJmlPeserta);
            txtJenisTransportasi =  view.findViewById(R.id.txtJenisTransportasi);
            txtJmlTransportasi =  view.findViewById(R.id.txtJmlTransportasi);
            txtAlamat =  view.findViewById(R.id.txtAlamat);
            txtKonfirmasi =  view.findViewById(R.id.txtKonfirmasi);

            lyt_parent = view.findViewById(R.id.lyt_parent);
        }
    }


    public AdapterPemesanan(Context mContext, List<PemesananPaket> pemesananList) {
        this.mContext = mContext;
        this.pemesananList = pemesananList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mypaket, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (pemesananList.isEmpty()){
            Toast.makeText(mContext.getApplicationContext(),"Data Kosong !",Toast.LENGTH_LONG).show();
            Log.d("isiPemesananList : ",""+pemesananList.size());
        }else {
            final PemesananPaket pemesananPaket = pemesananList.get(position);
            dateFormatter   = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            String tanggal_berangkat = pemesananPaket.getTanggal_berangkat();
            String tanggal = tanggal_berangkat.substring(8,10);
            String bulan = tanggal_berangkat.substring(5,7);
            String tahun = tanggal_berangkat.substring(0,4);
            tanggal_berangkat = tanggal+"-"+bulan+"-"+tahun;


            holder.txtNamaPaket.setText(pemesananPaket.getPaket());
            holder.txtNamaUser.setText(pemesananPaket.getNama());
            holder.txtTime.setText(tanggal_berangkat);
            holder.txtKonfirmasi.setText(pemesananPaket.getKonfirmasi());
            holder.txtJenisTransportasi.setText("Jenis Transportasi : "+pemesananPaket.getTransportasi());
            holder.txtJmlPeserta.setText("Jumlah Peserta : "+pemesananPaket.getJumlah_peserta());
            holder.txtJmlTransportasi.setText("Jumlah Transportasi : "+pemesananPaket.getJumlah_transportasi());
            holder.txtAlamat.setText("Alamat : "+pemesananPaket.getAlamat());


        }


    }


    /**
     * Showing popup menu when tapping on 3 dots
     */

    /**
     * Click listener for popup menu items
     */


    @Override
    public int getItemCount() {
       // return namaDestinasi.length;
       return pemesananList.size();
    }
}
