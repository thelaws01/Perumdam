package riswan.fkti.pipa.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import riswan.fkti.pipa.databinding.ListSliderBinding;
import riswan.fkti.pipa.models.Slider;

public class SliderAdapter  extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    private List<Slider> sliderList;
    private Activity mContext;

    public SliderAdapter(List<Slider> sliderList, Activity mContext){
        this.sliderList = sliderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSliderBinding binding1 = ListSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SliderAdapter.MyViewHolder(binding1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final riswan.fkti.pipa.models.Slider slider = sliderList.get(position);
        Picasso.get().load(slider.getId()).fit().into(holder.binding.imageSlider);
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ListSliderBinding binding;
        public MyViewHolder(@NonNull ListSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
