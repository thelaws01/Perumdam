package riswan.fkti.pipa.activity.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import riswan.fkti.pipa.R;
import riswan.fkti.pipa.activity.DashboardActivity;
import riswan.fkti.pipa.activity.view.HistoryViewModel;
import riswan.fkti.pipa.adapter.HistoryAdapter;
import riswan.fkti.pipa.databinding.ActivityMainProfilBinding;
import riswan.fkti.pipa.models.History;
import riswan.fkti.pipa.session.AuthSession;

public class ProfilAcitivty extends AppCompatActivity {

    ActivityMainProfilBinding binding;
    AuthSession session;
    boolean open;
    String image;
    HistoryAdapter adapter;
    HistoryViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new AuthSession(getApplicationContext());

        init_session();

        init_view();
        init_model();

        binding.backToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilAcitivty.this, DashboardActivity.class));
                finish();
            }
        });

        binding.lineKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout();
                finishAffinity();
            }
        });

        binding.updateProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilAcitivty.this, EditProfil.class));
                finish();
            }
        });

        binding.updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilAcitivty.this, EditPassword.class));
                finish();
            }
        });

        binding.lerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.standardBottomSheet.setVisibility(View.INVISIBLE);
                TranslateAnimation animation = new TranslateAnimation(0, binding.standardBottomSheet.getWidth(),0,0);
                animation.setDuration(500);
                binding.standardBottomSheet.setAnimation(animation);
            }
        });

        binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!open){
                    binding.standardBottomSheet.setVisibility(View.VISIBLE);
                    TranslateAnimation animation = new TranslateAnimation(binding.standardBottomSheet.getWidth(), 0,0,0);
                    animation.setDuration(500);
                    binding.standardBottomSheet.setAnimation(animation);
                } else {
                    binding.standardBottomSheet.setVisibility(View.INVISIBLE);
                    TranslateAnimation animation = new TranslateAnimation(0, binding.standardBottomSheet.getWidth(),0,0);
                    animation.setDuration(500);
                    binding.standardBottomSheet.setAnimation(animation);
                }
                open = !open;
            }
        });

//        binding.getimageProf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(image == "" || image == null){
//                    startActivity(new Intent(ProfilAcitivty.this, EditProfil.class));
//                    finish();
//                } else{
//                    Intent aa = new Intent(ProfilAcitivty.this, FotoActivity.class);
//                    aa.putExtra("image", image);
//                    startActivity(aa);
//                }
//            }
//        });
    }

    private void init_model() {
        ProgressDialog dialog = ProgressDialog.show(this, null, "Loading");
        model = new HistoryViewModel();
        model.ambilHistory().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> kategoriList) {
                adapter.addHistroyList(kategoriList);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.hide();
        dialog.dismiss();
    }

    private void init_view() {
        adapter = new HistoryAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvHistory.setLayoutManager(linearLayoutManager);
        binding.rvHistory.setAdapter(adapter);
        binding.rvHistory.setHasFixedSize(true);
    }

    private void init_session() {
        HashMap<String, String> peler = session.getUserDetail();
        image = peler.get(AuthSession.KEY_FOTO);
        if(image == "" || image == null){
            binding.getimageProf.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo));
        } else{
            Picasso.get().load(peler.get(AuthSession.KEY_FOTO)).fit().rotate(90).placeholder(R.drawable.loading).into(binding.getimageProf);
        }
        binding.getNameFull.setText(peler.get(AuthSession.KEY_NAME));
        binding.getPhoneNew.setText(peler.get(AuthSession.KEY_PHONE));
        binding.getAlamat.setText(peler.get(AuthSession.KEY_ALAMAT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        init_session();
        init_view();
        init_model();
    }
}
