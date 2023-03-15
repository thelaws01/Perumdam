package riswan.fkti.pipa.activity.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riswan.fkti.pipa.activity.profil.ProfilAcitivty;
import riswan.fkti.pipa.activity.profil.UpdateProfil;
import riswan.fkti.pipa.databinding.ActivityAuthRegisterBinding;
import riswan.fkti.pipa.models.auth.Register;
import riswan.fkti.pipa.session.AuthSession;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class RegisterActivity extends AppCompatActivity {

    private ActivityAuthRegisterBinding binding;
    ApiService apiService;
    ProgressDialog progressDialog;
    AuthSession authSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityAuthRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authSession = new AuthSession(getApplicationContext());
        apiService = RetroClass.getApiService();
        binding.btnDaftar.setVisibility(View.GONE);

        binding.backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.dttrPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.dttrPasswordUlang.getText().toString().trim().equals(s.toString())){
                    binding.btnDaftar.setVisibility(View.VISIBLE);
                } else {
                    binding.btnDaftar.setVisibility(View.GONE);
                    binding.dttrPassword.setError("Password Tidak Sama");
                    binding.dttrPassword.requestFocus();
                }
            }
        });

        binding.dttrPasswordUlang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Toast.makeText(RegisterActivity.this, binding.dttrPassword.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                if(binding.dttrPassword.getText().toString().trim().equals(s.toString())){
                    binding.btnDaftar.setVisibility(View.VISIBLE);
                } else {
                    binding.btnDaftar.setVisibility(View.GONE);
                    binding.dttrPasswordUlang.setError("Password Tidak Sama");
                    binding.dttrPasswordUlang.requestFocus();
                }
            }
        });

        binding.keLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.dttrUsername.getText().toString().isEmpty()){
                    binding.dttrUsername.setError("Harap isi");
                    binding.dttrUsername.requestFocus();
                }
                else if(binding.dttrPasswordUlang.getText().toString().trim().isEmpty()){
                    binding.dttrPasswordUlang.setError("Harap isi");
                    binding.dttrPasswordUlang.requestFocus();
                } else if(binding.dttrPassword.getText().toString().trim().isEmpty()){
                    binding.dttrPassword.setError("Harap Isi");
                    binding.dttrPassword.requestFocus();
                } else {
                    toDaftar(binding.dttrUsername.getText().toString().trim(), binding.dttrPassword.getText().toString().trim());
                }
            }
        });

    }

    private void toDaftar(String name, String password) {
        progressDialog = ProgressDialog.show(RegisterActivity.this,null, "Loading");
        Register register = new Register(name, password);
        Call<Register> peler = apiService.daftarBaru(register);
        peler.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    if(jsonObject.getString("status").equals("Registered")){
                        progressDialog.hide();
                        Toast.makeText(RegisterActivity.this, "username telah digunakan", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.hide();
                        authSession.regisBaru(jsonObject.getString("status"), name);
                        Toast.makeText(RegisterActivity.this, "Berhasil Daftar", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, UpdateProfil.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Terjadi Error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
