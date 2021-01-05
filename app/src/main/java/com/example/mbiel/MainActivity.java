package com.example.mbiel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity{

    private static Context context;
    ImageView imageView;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        imageView = (ImageView)findViewById(R.id.imageView);

        // Als je op de button klikt opent hij de camera
        final Button takePictureButton = findViewById(R.id.takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCamera();
            }
        });

        // Als je op de button klikt pakt hij een image van je gallerij
        final Button uploadPictureButton = findViewById(R.id.uploadPictureButton);
        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    private void openCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            Bitmap photo = (Bitmap)data.getExtras().get("data");
            String encodedImage = encodeImage(photo);

            Intent intent = new Intent(getBaseContext(), filterActivity.class);
            intent.putExtra("PHOTO", encodedImage);
            startActivity(intent);
        }
        if(requestCode == TAKE_PICTURE && resultCode == RESULT_OK){

            Bitmap photo = (Bitmap)data.getExtras().get("data");
            String encodedImage = encodeImage(photo);

            Intent intent = new Intent(getBaseContext(), filterActivity.class);
            intent.putExtra("PHOTO", encodedImage);
            startActivity(intent);
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}