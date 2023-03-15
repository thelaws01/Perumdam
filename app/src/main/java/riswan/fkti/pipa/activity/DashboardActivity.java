package riswan.fkti.pipa.activity;

    /*
    *
    * Author Fietra Prabaskara <fprabaskara@gmail.com>
    *
    * */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import java.util.ArrayList;
import java.util.List;

import riswan.fkti.pipa.activity.aduan.AddPengaduan;
import riswan.fkti.pipa.activity.aduan.IndexPengaduanAcitivity;
import riswan.fkti.pipa.adapter.BeritaAdapter;
import riswan.fkti.pipa.activity.berita.IndexActivity;
import riswan.fkti.pipa.activity.profil.ProfilAcitivty;
import riswan.fkti.pipa.activity.view.DashboardViewModel;
import riswan.fkti.pipa.adapter.SliderAdapter;
import riswan.fkti.pipa.databinding.ActivityMainDashboardBinding;
import riswan.fkti.pipa.models.Berita;
import riswan.fkti.pipa.models.BeritaDepan;
import riswan.fkti.pipa.models.Slider;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class DashboardActivity extends BaseActivity {

    private ActivityMainDashboardBinding binding;
    ProgressDialog progressDialog;

    public  static final String TAG = DashboardActivity.class.getSimpleName();
    ApiService apiService;
    BeritaAdapter adapter;
    ProgressDialog dialog;
    List<SlideModel> imageList = new ArrayList<SlideModel>();
    DashboardViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiService = RetroClass.getApiService();


        new MyTaskDashboard().execute();

        initial();
//        tesLagi();
        tes_slider();
        init_model();

        binding.include.lineSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ProfilAcitivty.class));
            }
        });

        binding.include.lineBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, IndexActivity.class));
            }
        });

        binding.include.linePengaduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, IndexPengaduanAcitivity.class));
            }
        });

        binding.include.lineAddAduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddPengaduan.class));
            }
        });


//        dashboardViewModel.getBeritaDepan().observe(this, new androidx.lifecycle.Observer<List<Pengaduan>>() {
//            @Override
//            public void onChanged(List<Pengaduan> pengaduans) {
//                Log.d(TAG, "peler" + pengaduans.size());
//                adapter.addPengaduanList(pengaduans);
//                adapter.notifyDataSetChanged();
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyTaskDashboard().execute();
    }

    private void init_model() {
        viewModel = new DashboardViewModel();
        viewModel.getBeritaDepan().observe(this, new androidx.lifecycle.Observer<List<BeritaDepan>>() {
            @Override
            public void onChanged(List<BeritaDepan> beritaDepans) {
                adapter.adddBeritaList(beritaDepans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initial() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvHolidayList.setLayoutManager(linearLayoutManager);
        binding.rvHolidayList.setHasFixedSize(true);

        adapter = new BeritaAdapter(this);
        binding.rvHolidayList.setAdapter(adapter);
    }

//    private void tesLagi() {
//        Observable<List<Berita>> berita = apiService.getBeritaList();
//        berita.
//                observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<List<Berita>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull List<Berita> beritas) {
//                        for(int i = 0; i < beritas.size(); i++){
//                            imageList.add(new SlideModel(beritas.get(i).getPhoto(), "", ScaleTypes.FIT));
//                        }
//                        binding.imageSlider.setImageList(imageList);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d(TAG, "error" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "kelar" + "kelar");
//                    }
//                });
//    }

    public void tes_slider(){

//        binding.imageSlider.setAdapter(sliderAdapter);
//        binding.imageSlider.setInfinite(true);
//
        imageList.add(new SlideModel("https://perumdamtirtakencana.id/banner/slide1.jpg","", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://perumdamtirtakencana.id/banner/slide3.jpg", "", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://perumdamtirtakencana.id/banner/slide2.jpg", "", ScaleTypes.FIT));
        binding.imageSlider.setImageList(imageList);
    }

    private class MyTaskDashboard extends AsyncTask<String, String, String> {
        public void onPreExecute() {
            progressDialog = new ProgressDialog(DashboardActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            initial();
            tes_slider();
            init_model();
        }
        public String doInBackground(String... unused) {
            return null;
        }
        public void onPostExecute(String result) {
            progressDialog.dismiss();
        }
    }
}
