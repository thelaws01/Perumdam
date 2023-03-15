package riswan.fkti.pipa.activity.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riswan.fkti.pipa.databinding.ActivityMainProfilPasswordBinding;
import riswan.fkti.pipa.session.AuthSession;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class EditPassword  extends AppCompatActivity {

    ActivityMainProfilPasswordBinding binding;
    String password_lama;
    AuthSession session;
    String name;
    ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityMainProfilPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetroClass.getApiService();

        session = new AuthSession(getApplicationContext());
        HashMap<String,String> kocok = session.getUserDetail();
        password_lama = kocok.get(AuthSession.KEY_PASSWORD);
        name = kocok.get(AuthSession.KEY_USERNAME);


        binding.backToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPassword.this, ProfilAcitivty.class));
                finish();
            }
        });

        binding.lineProfilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.passwordlama.getText().toString().equals(password_lama)){
                    if(binding.passwordbaru.getText().toString().trim().equals(binding.passwordulangi.getText().toString().trim())){
                        if(binding.passwordbaru.getText().toString().length()<8 && !isValidPassword(binding.passwordbaru.getText().toString())){
                            binding.passwordbaru.setError("Silahkan Gunukan Spesial Karakter");
                            binding.passwordbaru.requestFocus();
                        }else{
                            to_update(name, binding.passwordbaru.getText().toString().trim());
                        }
                    } else {
                        binding.passwordulangi.setError("Password Tidak Sesaui");
                        binding.passwordulangi.requestFocus();
                    }
                } else {
                    binding.passwordlama.setError("Password Tidak Sesuai");
                    binding.passwordlama.requestFocus();
                }
            }
        });
    }

    private void to_update(String name, String trim) {
        ProgressDialog dialog = ProgressDialog.show(this, null, "Loading");
        Call<ResponseBody> peler = apiService.posPassword(name, trim);
        peler.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    dialog.hide();
                    try {
                        JsonElement jsonElement = JsonParser.parseString(response.body().string());
//                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                        String ler = gson.toJson(jsonElement);
                        JSONObject object = new JSONObject(new Gson().toJson(jsonElement));
                        if(object.getString("status").equals("Sukses")){
                            Toast.makeText(EditPassword.this, "Berhasil Update Password", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditPassword.this, ProfilAcitivty.class));
                            finish();
                        } else {
                            dialog.hide();
                            Toast.makeText(EditPassword.this, "Gagal Update Password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.hide();
                    Toast.makeText(EditPassword.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.hide();
            }
        });
    }


    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
