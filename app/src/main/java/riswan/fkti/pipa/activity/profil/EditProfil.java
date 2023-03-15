package riswan.fkti.pipa.activity.profil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riswan.fkti.pipa.R;
import riswan.fkti.pipa.databinding.ActivityMainProfilEditBinding;
import riswan.fkti.pipa.session.AuthSession;
import riswan.fkti.pipa.utils.ApiService;
import riswan.fkti.pipa.utils.RetroClass;

public class EditProfil extends AppCompatActivity {

    ActivityMainProfilEditBinding binding;
    AuthSession session;
    private final int GALLERY = 1;
    public String kecamatan, name, image;
    ApiService apiService;

    public static final String TAG = EditProfil.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityMainProfilEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new AuthSession(getApplicationContext());

        init_session();

        binding.pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY);
            }
        });

        binding.lineProfilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> kocok = session.getUserDetail();
                String namee =  kocok.get(AuthSession.KEY_NAME);
                String nike = kocok.get(AuthSession.KEY_NIK);
                String alamate = kocok.get(AuthSession.KEY_ALAMAT);
                String phonee = kocok.get(AuthSession.KEY_PHONE);
                String camat = kocok.get(AuthSession.KEY_KEC);
                if(namee.equals(binding.updateName.getText().toString().trim())
                        && nike.equals(binding.updateNik.getText().toString().trim())
                        && alamate.equals(binding.updateAlamat.getText().toString().trim())
                        && phonee.equals(binding.updatePhone.getText().toString().trim())
//                        && camat.equals(kecamatan)
                ){
//                    Toast.makeText(EditProfil.this, "Simpan Tanpa Perubahan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfil.this, ProfilAcitivty.class));
                    finish();
                } else {
                    update_berlanjut();
                }
            }
        });

        binding.setKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kecamatan = binding.setKecamatan.getSelectedItem().toString();
                Toast.makeText(EditProfil.this, kecamatan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.backToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> kocok = session.getUserDetail();
                String namee =  kocok.get(AuthSession.KEY_NAME);
                String nike = kocok.get(AuthSession.KEY_NIK);
                String alamate = kocok.get(AuthSession.KEY_ALAMAT);
                String phonee = kocok.get(AuthSession.KEY_PHONE);
                if(namee.equals(binding.updateName.getText().toString().trim())
                        && nike.equals(binding.updateNik.getText().toString().trim())
                        && alamate.equals(binding.updateAlamat.getText().toString().trim())
                        && phonee.equals(binding.updatePhone.getText().toString().trim())
                ){
                    startActivity(new Intent(EditProfil.this, ProfilAcitivty.class));
                    finish();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            EditProfil.this);
                    alertDialogBuilder.setTitle("Keluar Tanpa Perubahan?");
                    alertDialogBuilder
                            .setMessage("Klik Ya untuk Buang!")
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    startActivity(new Intent(EditProfil.this, ProfilAcitivty.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }

    private void update_berlanjut() {
        apiService = RetroClass.getApiService();

        ProgressDialog dialog =ProgressDialog.show(EditProfil.this,null, "Loading");

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
                        session.updateProfile("baru", phone, alamat, nama, nik);
                        startActivity(new Intent(EditProfil.this, ProfilAcitivty.class));
                        finish();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    dialog.hide();
                    Toast.makeText(EditProfil.this, "Pasti Error Back End", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.hide();
                Toast.makeText(EditProfil.this, "Error Server", Toast.LENGTH_SHORT).show();
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
                    binding.setImage.setImageBitmap(resizedBitmap);
                    uploadImageUsingRetrofit(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfil.this, "Failed to select image!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadImageUsingRetrofit(Bitmap bitmap) {
        ProgressDialog dialog =ProgressDialog.show(EditProfil.this,null, "Loading");

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
                        Toast.makeText(EditProfil.this, "Berhasil Upload Foto", Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Apa errornya " + response.message().toString());
                    Toast.makeText(EditProfil.this, "Error Cuyy", Toast.LENGTH_SHORT).show();
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
        image = galer.get(AuthSession.KEY_FOTO);

        if(image == "" || image == null){
            binding.setImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo));
        } else {
            Picasso.get().load(galer.get(AuthSession.KEY_FOTO)).fit().rotate(90).placeholder(R.drawable.loading).into(binding.setImage);
        }
        binding.updateName.setText(galer.get(AuthSession.KEY_NAME));
        binding.updateNik.setText(galer.get(AuthSession.KEY_NIK));
        binding.updateAlamat.setText(galer.get(AuthSession.KEY_ALAMAT));
        binding.updatePhone.setText(galer.get(AuthSession.KEY_PHONE));
    }

    @Override
    public void onBackPressed() {
        HashMap<String, String> kocok = session.getUserDetail();
        String namee =  kocok.get(AuthSession.KEY_NAME);
        String nike = kocok.get(AuthSession.KEY_NIK);
        String alamate = kocok.get(AuthSession.KEY_ALAMAT);
        String phonee = kocok.get(AuthSession.KEY_PHONE);
        if(namee.equals(binding.updateName.getText().toString().trim())
                && nike.equals(binding.updateNik.getText().toString().trim())
                && alamate.equals(binding.updateAlamat.getText().toString().trim())
                && phonee.equals(binding.updatePhone.getText().toString().trim())
        ){
            startActivity(new Intent(EditProfil.this, ProfilAcitivty.class));
            finish();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    EditProfil.this);
            alertDialogBuilder.setTitle("Keluar Tanpa Perubahan?");
            alertDialogBuilder
                    .setMessage("Klik Ya untuk Buang!")
                    .setCancelable(false)
                    .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            startActivity(new Intent(EditProfil.this, ProfilAcitivty.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
