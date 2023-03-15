package riswan.fkti.pipa.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import riswan.fkti.pipa.databinding.ListHistoryBinding;
import riswan.fkti.pipa.models.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Activity mContext;
    private List<History> historyList;

    public HistoryAdapter(Activity mContext){
        historyList = new ArrayList<>();
        this.mContext = mContext;
    }

    public void addHistroyList(List<History> historyList){
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListHistoryBinding binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final History history = historyList.get(position);
        holder.binding.getAlamat.setText(history.getLokasi());
        holder.binding.getKategori.setText(history.getBentuk());
        holder.binding.getWaktu.setText(history.getWaktu());
        holder.binding.cardKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, history.getBentuk(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ListHistoryBinding binding;
        MyViewHolder(ListHistoryBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
