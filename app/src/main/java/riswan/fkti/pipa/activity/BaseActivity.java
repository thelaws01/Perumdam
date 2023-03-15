package riswan.fkti.pipa.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import riswan.fkti.pipa.App;
import riswan.fkti.pipa.activity.offline.Koneksi;
import riswan.fkti.pipa.activity.offline.OfflineClass;

public class BaseActivity extends AppCompatActivity implements Koneksi.ConnectivityReceiver{

    private long backPressed = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInternet();
    }

    private void checkInternet() {
        boolean isConnect = Koneksi.isActive();
        if(!isConnect){
            changeAcitivy();
        }
    }

    private void changeAcitivy() {
            Intent aa = new Intent(this, OfflineClass.class);
            startActivity(aa);
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
    public void onBackPressed(){
        if(backPressed + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(this, "Tekan Sekali Lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

    @Override
    public void onNetworkCanged(boolean isConnected) {
        if (!isConnected){
            changeAcitivy();
        }
    }
}
