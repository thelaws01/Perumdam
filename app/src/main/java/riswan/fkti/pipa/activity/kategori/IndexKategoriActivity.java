package riswan.fkti.pipa.activity.kategori;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import riswan.fkti.pipa.activity.view.KategoriViewModel;
import riswan.fkti.pipa.adapter.KategoriAdapter;
import riswan.fkti.pipa.databinding.AcitivytMainKategoriIndexBinding;
import riswan.fkti.pipa.models.Kategori;

/**
 * Android MVVM Use Retrofit , Binding => fprabaskara@gmail.com
 */

public class IndexKategoriActivity extends AppCompatActivity {

    KategoriAdapter adapter;
    AcitivytMainKategoriIndexBinding binding;
    KategoriViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = AcitivytMainKategoriIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init_ui();
        init_model();
    }

    private void init_model() {
        model = new KategoriViewModel();
        model.ambilKategori().observe(this, new Observer<List<Kategori>>() {
            @Override
            public void onChanged(List<Kategori> kategoriList) {
                adapter.addKategoriList(kategoriList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init_ui() {
        adapter = new KategoriAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvKategori.setLayoutManager(linearLayoutManager);
        binding.rvKategori.setHasFixedSize(true);
        binding.rvKategori.setAdapter(adapter);
    }
}
