package riswan.fkti.pipa.activity.offline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import riswan.fkti.pipa.App;

public class Koneksi extends BroadcastReceiver {

    public static ConnectivityReceiver connectivityReceiver;

    public Koneksi(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        boolean isActive = info!=null && info.isConnectedOrConnecting();

        if(connectivityReceiver!=null){
            connectivityReceiver.onNetworkCanged(isActive);
        }
    }

    public static boolean isActive(){
        ConnectivityManager cm = (ConnectivityManager) App
                .getInstance()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activework = cm.getActiveNetworkInfo();

        return activework != null && activework.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiver{
        void onNetworkCanged(boolean isConnected);
    }
}
