package riswan.fkti.pipa.activity.aduan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import riswan.fkti.pipa.R;
import riswan.fkti.pipa.adapter.GaleriAdapter;
import riswan.fkti.pipa.databinding.ActivityAddPengaduanBinding;

public class AddPengaduan extends AppCompatActivity implements GaleriAdapter.OnImageClickListener{

    ActivityAddPengaduanBinding binding;
    private ArrayList<String> imagePath;
    GaleriAdapter adapter;
    File url_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityAddPengaduanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imagePath = new ArrayList<>();
        adapter = new GaleriAdapter(this, imagePath, this);

        getImagePath();
        getImage();

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AddPengaduan.this, url_foto.toString(), Toast.LENGTH_SHORT).show();
                Intent aa = new Intent(AddPengaduan.this, PostPengaduanActivity.class);
                aa.putExtra("inifoto", url_foto.toString());
                startActivity(aa);
            }
        });
    }

    private void getImage() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        binding.getRecylerImage.setLayoutManager(manager);
        binding.getRecylerImage.setAdapter(adapter);
    }

    private void getImagePath() {
        // in this method we are adding all our image paths
        // in our arraylist which we have created.
        // on below line we are checking if the device is having an sd card or not.
        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (isSDPresent) {

            // if the sd card is present we are creating a new list in
            // which we are getting our images data with their ids.
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            // on below line we are creating a new
            // string to order our images by string.
            final String orderBy = MediaStore.Images.Media._ID;

            // this method will stores all the images
            // from the gallery in Cursor
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            // below line is to get total number of images
            int count = cursor.getCount();

            // on below line we are running a loop to add
            // the image file path in our array list.
            for (int i = 0; i < count; i++) {

                // on below line we are moving our cursor position
                cursor.moveToPosition(i);

                // on below line we are getting image file path
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                // after that we are getting the image file path
                // and adding that path in our array list.
                imagePath.add(cursor.getString(dataColumnIndex));
            }
            adapter.notifyDataSetChanged();
            // after adding the data to our
            // array list we are closing our cursor.
            cursor.close();
        }
    }

    @Override
    public void onSelected(File url) {
        url_foto = url;
        Picasso.get().load(url).into(binding.setImage);
    }
}
