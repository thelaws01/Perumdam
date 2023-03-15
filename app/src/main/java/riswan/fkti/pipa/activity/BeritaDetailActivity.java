package riswan.fkti.pipa.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import riswan.fkti.pipa.databinding.ActivityMainBeritaDetailBinding;

public class BeritaDetailActivity extends AppCompatActivity {

    ActivityMainBeritaDetailBinding binding;
    String image, judul, isi, tanggal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBeritaDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init_intent();

        binding.backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init_intent(){
        image = getIntent().getStringExtra("image");
        judul = getIntent().getStringExtra("judul");
        isi = getIntent().getStringExtra("isi");
        tanggal = getIntent().getStringExtra("tanggal");

        ambil_isi();
    }

    public void ambil_isi()
    {
        Picasso.get().load(image).fit().into(binding.getImageBerita);
        binding.getJudul.setText(judul);
        binding.getIsi.setText(isi);
        binding.getTanggal.setText(tanggal);

    }


}
