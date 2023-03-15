package riswan.fkti.pipa.activity.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riswan.fkti.pipa.activity.BaseActivity;
import riswan.fkti.pipa.activity.DashboardActivity;
import riswan.fkti.pipa.activity.auth.RegisterActivity;
import riswan.fkti.pipa.databinding.AcitivyMainProfilUpdateBinding;
import riswan.fkti.pipa.models.auth.Register;
import riswan.fkti.pipa.session.AuthSession;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class UpdateProfil  extends BaseActivity {

    AcitivyMainProfilUpdateBinding binding;
    private final int GALLERY = 1;
    public String kecamatan, name;
    ApiService apiService;
    AuthSession session;

    public static final String TAG = UpdateProfil.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = AcitivyMainProfilUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new AuthSession(getApplicationContext());

        HashMap<String, String> galer = session.getUserDetail();
        name = galer.get(AuthSession.KEY_USERNAME);

        binding.takeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY);
            }
        });

        binding.setKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kecamatan = binding.setKecamatan.getSelectedItem().toString();
                Toast.makeText(UpdateProfil.this, kecamatan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.lineProfilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetroClass.getApiService();

                ProgressDialog dialog =ProgressDialog.show(UpdateProfil.this,null, "Loading");

                String nama = binding.updateName.getText().toString().trim();
                String nik = binding.updateNik.getText().toString().trim();
                String alamat = binding.updateAlamat.getText().toString().trim();
                String phone = binding.updatePhone.getText().toString().trim();


                Call<ResponseBody> gosol = apiService.getProf(name, nama, nik, alamat, phone, kecamatan);
                gosol.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            dialog.hide();
                            JsonElement jsonElement = null;
                            try {
                                jsonElement = JsonParser.parseString(response.body().string());
                                JSONObject jsonObject = new JSONObject(new Gson().toJson(jsonElement));
                                JSONObject ddata = jsonObject.getJSONObject("data");
                                Log.d(TAG, "respon" + ddata);
                                String phone = ddata.getString("phone");
                                String alamat = ddata.getString("alamat");
                                String nama = ddata.getString("nama");
                                String nik = ddata.getString("nik");
                                session.updateProfile("baru", phone, alamat, nama,nik);
                                startActivity(new Intent(UpdateProfil.this, ProfilAcitivty.class));
                                finish();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            dialog.hide();
                            Toast.makeText(UpdateProfil.this, "Pasti Error Back End", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dialog.hide();
                        Toast.makeText(UpdateProfil.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    int width = bitmap.getWidth();

                    int height = bitmap.getHeight();


                    int newWidth = 200;

                    int newHeight  = 200;

                    // calculate the scale - in this case = 0.4f

                    float scaleWidth = ((float) newWidth) / width;

                    float scaleHeight = ((float) newHeight) / height;

                    Matrix matrix = new Matrix();

                    matrix.postScale(scaleWidth, scaleHeight);
                    matrix.postRotate(90);

                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,width, height, matrix, true);
                    binding.displayCamera.setImageBitmap(resizedBitmap);
                    uploadImageUsingRetrofit(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateProfil.this, "Failed to select image!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadImageUsingRetrofit(Bitmap bitmap) {
        ProgressDialog dialog =ProgressDialog.show(UpdateProfil.this,null, "Loading");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        apiService = RetroClass.getApiService();

        Call<ResponseBody> peler = apiService.getImagePro(name, image);
        peler.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    dialog.hide();
                    JsonElement jsonElement = null;
                    try {
                        jsonElement = JsonParser.parseString(response.body().string());
//                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                        String ler = gson.toJson(jsonElement);
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(jsonElement));
                        JSONObject ddata = jsonObject.getJSONObject("data");
                        Log.d(TAG, "respon" + ddata);
                        String url = ddata.getString("foto");
                        session.updateFoto(name, url);
                        Toast.makeText(UpdateProfil.this, "Berhasil Upload Foto", Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(UpdateProfil.this, "Error Njing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.hide();
                Log.d(TAG, "error" + t.getMessage());
            }
        });
    }
}
