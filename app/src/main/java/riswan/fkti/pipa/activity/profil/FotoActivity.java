package riswan.fkti.pipa.activity.profil;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import riswan.fkti.pipa.databinding.ActivityMainFotoBinding;

public class FotoActivity extends AppCompatActivity {

    ActivityMainFotoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        binding = ActivityMainFotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.photoDraweeView.setPhotoUri(Uri.parse(getIntent().getStringExtra("image")));

    }
}
