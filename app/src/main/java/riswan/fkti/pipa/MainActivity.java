package riswan.fkti.pipa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import riswan.fkti.pipa.activity.DashboardActivity;
import riswan.fkti.pipa.activity.profil.ProfilAcitivty;
import riswan.fkti.pipa.activity.profil.UpdateProfil;
import riswan.fkti.pipa.session.AuthSession;

public class MainActivity extends AppCompatActivity {

    AuthSession session;
    String status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new AuthSession(getApplicationContext());
        session.check_login();

        HashMap<String,String> peler = session.getUserDetail();
        status = peler.get(AuthSession.KEY_BARU);

        if(session.is_login()){
            Log.d("Main", "session" + status);
            switch (status){
                case "baru":
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    finish();
                    break;
                case "success":
                    startActivity(new Intent(MainActivity.this, UpdateProfil.class));
                    finish();
                    break;
            }
        }
    }
}