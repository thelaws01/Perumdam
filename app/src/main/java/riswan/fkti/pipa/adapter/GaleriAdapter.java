package riswan.fkti.pipa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import riswan.fkti.pipa.R;
import riswan.fkti.pipa.databinding.ListAllGaleryBinding;
import riswan.fkti.pipa.databinding.ListPengaduanBinding;

public class GaleriAdapter extends RecyclerView.Adapter<GaleriAdapter.MyViewHolder> {

    private ArrayList<String> imagePath;
    private Activity mContext;
    OnImageClickListener listener;

    public interface OnImageClickListener{
        void onSelected(File file);
    }

    public GaleriAdapter(Activity context, ArrayList<String> imagePathArrayList, OnImageClickListener listener) {
        this.mContext = context;
        this.imagePath = imagePathArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListAllGaleryBinding binding = ListAllGaleryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        File imgFile = new File(imagePath.get(position));
        String strFileName = imgFile.getName();

        if (imgFile.exists()) {
            listener.onSelected(imgFile);
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(holder.binding.getImageView);
            holder.binding.getImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, strFileName, Toast.LENGTH_SHORT).show();
                    listener.onSelected(imgFile);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imagePath.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ListAllGaleryBinding binding;
        MyViewHolder(ListAllGaleryBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
