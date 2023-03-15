package riswan.fkti.pipa.activity.aduan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riswan.fkti.pipa.R;
import riswan.fkti.pipa.activity.DashboardActivity;
import riswan.fkti.pipa.activity.kategori.IndexKategoriActivity;
import riswan.fkti.pipa.activity.profil.EditProfil;
import riswan.fkti.pipa.databinding.ActivityPostPengaduanBinding;
import riswan.fkti.pipa.session.AuthSession;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class PostPengaduanActivity extends AppCompatActivity {


    public static final String TAG = PostPengaduanActivity.class.getSimpleName();

    ActivityPostPengaduanBinding binding;
    String imgPath, id_kategori;
    AuthSession session;
    public String name;
    ApiService apiService;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostPengaduanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new AuthSession(getApplicationContext());

        init_session();

        initalis();

        binding.buatPengaduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alamat = binding.postAlamat.getText().toString().trim();
                String info = binding.postInfo.getText().toString().trim();
                uploadImageUsingRetrofit(bitmap, alamat, info);
            }
        });

        binding.backToImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostPengaduanActivity.this, AddPengaduan.class));
                finish();
            }
        });

        binding.getKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PostPengaduanActivity.this, IndexKategoriActivity.class), 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                binding.getKategori.setText(data.getStringExtra("label"));
                id_kategori = data.getStringExtra("id");
            }
        }
    }

    private void uploadImageUsingRetrofit(Bitmap bitmap, String alamat, String info) {
        ProgressDialog dialog =ProgressDialog.show(PostPengaduanActivity.this,null, "Loading");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        apiService = RetroClass.getApiService();

        Call<ResponseBody> peler = apiService.addPost(name, alamat, info, id_kategori, image);
        peler.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    dialog.hide();
//                    JsonElement jsonElement = null;
//                    try {
//                        jsonElement = JsonParser.parseString(response.body().string());
//                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                        String ler = gson.toJson(jsonElement);
//                        JSONObject jsonObject = new JSONObject(new Gson().toJson(jsonElement));
//                        JSONObject ddata = jsonObject.getJSONObject("message");
//                        Log.d(TAG, "respon" + ddata);
                        Toast.makeText(PostPengaduanActivity.this, "Berhasil Buat Pengaduan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PostPengaduanActivity.this, DashboardActivity.class));
                        finish();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    Log.d("Pengaduan", "Error Apa" + response.errorBody().toString());
                    dialog.hide();
                    Toast.makeText(PostPengaduanActivity.this, "Error Njing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.hide();
                Log.d(TAG, "error" + t.getMessage());
            }
        });
    }

    private void init_session() {
        HashMap<String, String> galer = session.getUserDetail();
        name = galer.get(AuthSession.KEY_USERNAME);
    }

    private void initalis() {
//        Toast.makeText(this, getIntent().getStringExtra("inifoto"), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, getIntent().getStringExtra("foto"), Toast.LENGTH_SHORT).show();
        imgPath = getIntent().getStringExtra("inifoto");
//
        File imgFile = new File(imgPath);
        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

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
//            binding.getIntentImage.setImageBitmap(resizedBitmap);
//            uploadImageUsingRetrofit(bitmap);
    }
}
