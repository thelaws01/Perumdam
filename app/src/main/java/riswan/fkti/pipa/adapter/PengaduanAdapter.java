package riswan.fkti.pipa.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import riswan.fkti.pipa.R;
import riswan.fkti.pipa.databinding.ListPengaduanBinding;
import riswan.fkti.pipa.models.Pengaduan;

public class PengaduanAdapter extends RecyclerView.Adapter<PengaduanAdapter.MyViewHolder> {

    private List<Pengaduan> pengaduanList;
    private Activity mContext;

    public PengaduanAdapter(Activity mContext){
        pengaduanList = new ArrayList<>();
        this.mContext = mContext;
    }

    public void addPengaduanList(List<Pengaduan> pengaduanList){
        this.pengaduanList = pengaduanList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListPengaduanBinding binding = ListPengaduanBinding.inflate(
                LayoutInflater.from(parent.getContext())
        );
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Pengaduan pengaduan = pengaduanList.get(position);
        holder.binding.getMemberName.setText(pengaduan.getMember_nama());
        holder.binding.getUsername.setText(pengaduan.getMember_nama());
        holder.binding.getBentuk.setText(pengaduan.getBentuk()+"\n"+mContext.getResources().getString(R.string.lokasi) + pengaduan.getLokasi() + "\n");
        holder.binding.getTanggal.setText(pengaduan.getWaktu());
        Picasso.get().load(pengaduan.getFoto()).fit().into(holder.binding.getFoto);
    }

    @Override
    public int getItemCount() {
        return pengaduanList != null ? pengaduanList.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private ListPengaduanBinding binding;
        MyViewHolder(ListPengaduanBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
