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

import java.util.List;

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNamaPaket;
        public LinearLayout lyt_parent;

        public MyViewHolder(View view) {
            super(view);
            namaDestinasi = (TextView) view.findViewById(R.id.namaDestinasi);
            backdrop = (ImageView) view.findViewById(R.id.backdrop);
            cv_main = (CardView) view.findViewById(R.id.cardlist_item);
            relaPaket = view.findViewById(R.id.relaPaket);
        }
    }


    public AdapterPemesanan(Context mContext, List<PemesananPaket> pemesananList) {
        this.mContext = mContext;
        this.pemesananList = pemesananList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_travel_destination, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (paketTourList.isEmpty()){
            Toast.makeText(mContext.getApplicationContext(),"Data Kosong !",Toast.LENGTH_LONG).show();
            Log.d("isiPaketList : ",""+paketTourList.size());
        }else {
            final PaketTour paketTour = paketTourList.get(position);

            holder.namaDestinasi.setText(paketTour.getNamaPaket());


            Glide.with(mContext)
                    .load(paketTour.getDownloadUrl())
                    .into(holder.backdrop);

            holder.relaPaket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,DetailPaketTour.class);
                    intent.putExtra("paketTour",paketTour);
                    mContext.startActivity(intent);
                }
            });

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
       return paketTourList.size();
    }
}
