package riswan.fkti.pipa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import riswan.fkti.pipa.databinding.ListKategoriBinding;
import riswan.fkti.pipa.models.BeritaDepan;
import riswan.fkti.pipa.models.Kategori;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {

    private List<Kategori> kategoris;
    private Activity mContext;

    public KategoriAdapter(Activity mContext){
        kategoris = new ArrayList<>();
        this.mContext = mContext;

    }

    public void addKategoriList(List<Kategori> kategoriList){
        this.kategoris = kategoriList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListKategoriBinding binding = ListKategoriBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Kategori kategori = kategoris.get(position);
        holder.binding.getLabel.setText(kategori.getLabel());
        holder.binding.cardKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent();
                aa.putExtra("id", kategori.getId().toString());
                aa.putExtra("label", kategori.getLabel());
                mContext.setResult(Activity.RESULT_OK, aa);
                mContext.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoris.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ListKategoriBinding binding;
        MyViewHolder(ListKategoriBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
