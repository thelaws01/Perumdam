package riswan.fkti.pipa.activity.offline;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import riswan.fkti.pipa.App;
import riswan.fkti.pipa.activity.offline.Koneksi;
import riswan.fkti.pipa.databinding.ActivityNoIntenerBinding;

public class OfflineClass extends AppCompatActivity implements Koneksi.ConnectivityReceiver {

    ActivityNoIntenerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityNoIntenerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
    }

    public void changeActivity(){
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        Koneksi koneksi = new Koneksi();
        registerReceiver(koneksi, filter);

        App.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkCanged(boolean isConnected) {
        if(isConnected){
            changeActivity();
        }
    }
}
