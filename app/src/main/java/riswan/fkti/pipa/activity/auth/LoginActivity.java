package riswan.fkti.pipa.activity.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import riswan.fkti.pipa.R;
import riswan.fkti.pipa.activity.BaseActivity;
import riswan.fkti.pipa.activity.DashboardActivity;
import riswan.fkti.pipa.databinding.ActivityAuthLoginBinding;
import riswan.fkti.pipa.models.auth.Login;
import riswan.fkti.pipa.session.AuthSession;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class LoginActivity extends BaseActivity {

    private Context mContext = LoginActivity.this;
    private ActivityAuthLoginBinding binding;
    private static final String TAG = LoginActivity.class.getSimpleName();
    AuthSession session;
    ApiService apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityAuthLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new AuthSession(getApplicationContext());

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d(TAG, token);
                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        binding.masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.username.getText().toString().trim().isEmpty() && binding.password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Mohon Lengkapi Inputan", Toast.LENGTH_SHORT).show();
                } else {
                    masuk(binding.username.getText().toString().trim(), binding.password.getText().toString().trim());
                }
            }
        });

        binding.daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void masuk(String username, String password) {

        ProgressDialog dialog = ProgressDialog.show(this, null, "Loading");

        apiService = RetroClass.getApiService();
        Login login = new Login(username, password);
        Call<ResponseBody> peler = apiService.toLogin(login);
        peler.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    dialog.hide();
                    JsonElement jsonElement = null;
                    try {
                        jsonElement = JsonParser.parseString(response.body().string());
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String ler = gson.toJson(jsonElement);
                        Log.d(TAG, "respon" + ler);

                        JSONObject jsonObject = new JSONObject(new Gson().toJson(jsonElement));
                        if(jsonObject.getString("status").equals("Gagal")){
                            Toast.makeText(LoginActivity.this, "username dan password salah", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject ddata = jsonObject.getJSONObject("data");
                            if (ddata.has("foto")){
                                session.createLogin(
                                        ddata.getString("id"),
                                        ddata.getString("akses_id"),
                                        ddata.getString("nama"),
                                        ddata.getString("nik"),
                                        ddata.getString("phone"),
                                        ddata.getString("foto"),
                                        ddata.getString("ttl"),
                                        ddata.getString("alamat"),
                                        ddata.getString("username"),
                                        ddata.getString("plain")
                                );
                            } else{
                                session.createLogin(
                                        ddata.getString("id"),
                                        ddata.getString("akses_id"),
                                        ddata.getString("nama"),
                                        ddata.getString("nik"),
                                        ddata.getString("phone"),
                                        "",
                                        ddata.getString("ttl"),
                                        ddata.getString("alamat"),
                                        ddata.getString("username"),
                                        ddata.getString("plain")
                                );
                            }
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.hide();
                    Toast.makeText(LoginActivity.this, "Periksa Kembali Jaringan Internet Anda", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.hide();
                Toast.makeText(LoginActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
