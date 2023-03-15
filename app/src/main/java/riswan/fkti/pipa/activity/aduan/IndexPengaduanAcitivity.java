package riswan.fkti.pipa.activity.aduan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import riswan.fkti.pipa.activity.DashboardActivity;
import riswan.fkti.pipa.activity.view.PengduanViewModel;
import riswan.fkti.pipa.adapter.PengaduanAdapter;
import riswan.fkti.pipa.databinding.ActivityMainPengaduanIndexBinding;
import riswan.fkti.pipa.models.Pengaduan;

public class IndexPengaduanAcitivity extends AppCompatActivity {

    ActivityMainPengaduanIndexBinding binding;
    PengaduanAdapter adapter;
    PengduanViewModel viewModel;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityMainPengaduanIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init_ui();

        initViewModel();

        binding.backToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexPengaduanAcitivity.this, DashboardActivity.class));
                finish();
            }
        });
    }

    private void initViewModel() {

        viewModel = new PengduanViewModel();
        viewModel.getPengaduanAll().observe(this, new Observer<List<Pengaduan>>() {
            @Override
            public void onChanged(List<Pengaduan> pengaduanList) {
                adapter.addPengaduanList(pengaduanList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void init_ui() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        adapter = new PengaduanAdapter(this);

        binding.rvPengaduan.setHasFixedSize(true);
        binding.rvPengaduan.setLayoutManager(linearLayoutManager);
        binding.rvPengaduan.setAdapter(adapter);
    }
}
