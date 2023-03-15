package riswan.fkti.pipa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import riswan.fkti.pipa.activity.BeritaDetailActivity;
import riswan.fkti.pipa.databinding.ListBeritaDepanBinding;
import riswan.fkti.pipa.models.BeritaDepan;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeriteViewHolder> {

    private List<BeritaDepan> beritas;
    private Activity mContext;

    public BeritaAdapter(Activity mContext){
        beritas = new ArrayList<>();
        this.mContext = mContext;
    }

    public void adddBeritaList(List<BeritaDepan> beritaList){
        this.beritas = beritaList;
    }

    @NonNull
    @Override
    public BeriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListBeritaDepanBinding binding1 = ListBeritaDepanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BeriteViewHolder(binding1);
    }

    @Override
    public void onBindViewHolder(@NonNull BeriteViewHolder holder, int position) {
        final BeritaDepan berita = beritas.get(position);
        holder.binding.getJudulBerita.setText(berita.getJudul());
        holder.binding.getTanggalBerita.setText(berita.getTanggal());
        Picasso.get().load(berita.getFoto()).fit().into(holder.binding.getFotoBerita);
        holder.binding.cardBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(mContext, BeritaDetailActivity.class);
                aa.putExtra("image", berita.getFoto());
                aa.putExtra("judul", berita.getJudul());
                aa.putExtra("isi", berita.getKontent());
                aa.putExtra("tanggal", berita.getTanggal());
                mContext.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beritas != null ? beritas.size() : 0;
    }

    static class BeriteViewHolder extends RecyclerView.ViewHolder{
        private ListBeritaDepanBinding binding;
        BeriteViewHolder(ListBeritaDepanBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
