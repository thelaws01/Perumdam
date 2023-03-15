package riswan.fkti.pipa.activity.berita;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import riswan.fkti.pipa.adapter.BeritaAdapter;
import riswan.fkti.pipa.activity.view.BeritaViewModel;
import riswan.fkti.pipa.databinding.ActivityMainBeritaBinding;
import riswan.fkti.pipa.models.BeritaDepan;

public class IndexActivity extends AppCompatActivity {

    ActivityMainBeritaBinding binding;
    BeritaAdapter adapter;
    BeritaViewModel viewModel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBeritaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Content Loader");
        progressDialog.setProgress(10);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading...");
        new MyTask().execute();


        binding.backToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, DashboardActivity.class));
                finish();
            }
        });
    }

    private void init_modal() {
        viewModel = new BeritaViewModel();
        viewModel.ambilBerita().observe(this, new Observer<List<BeritaDepan>>() {
            @Override
            public void onChanged(List<BeritaDepan> beritaDepans) {
                adapter.adddBeritaList(beritaDepans);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void init_ui() {

        adapter = new BeritaAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvBerita.setLayoutManager(linearLayoutManager);
        binding.rvBerita.setHasFixedSize(true);
        binding.rvBerita.setAdapter(adapter);

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {
            progressDialog.show();
            init_ui();
            init_modal();

        }
        public Void doInBackground(Void... unused) {
            return null;
        }
        public void onPostExecute(Void result) {
            progressDialog.hide();
        }
    }
}
