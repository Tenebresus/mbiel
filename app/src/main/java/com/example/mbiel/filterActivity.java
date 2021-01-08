package com.example.mbiel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mbiel.volleyPost;

import java.io.ByteArrayOutputStream;

public class filterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Bitmap selectedImage = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("PHOTO"), 0, getIntent().getByteArrayExtra("PHOTO").length);

        String encodedImage = encodeImage(selectedImage);
        volleyPost volley = new volleyPost();

        Button cirkelButton = (Button) findViewById(R.id.Cirkels);
        cirkelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volley.volleyPostRequest(encodedImage, "http://86.83.86.194:5000/cirkel");
            }
        });

        Button driehoekButton = (Button) findViewById(R.id.Driehoek);
        driehoekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volley.volleyPostRequest(encodedImage, "http://86.83.86.194:5000/driehoek");
            }
        });

        Button mirrorButton = (Button) findViewById(R.id.spiegelen);
        mirrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volley.volleyPostRequest(encodedImage, "http://82.73.193.149:5000/mirror");
            }
        });

        Button zwartWitButton = (Button) findViewById(R.id.zwartwit);
        zwartWitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volley.volleyPostRequest(encodedImage, "http://82.73.193.149:5000/zwart_wit");
            }
        });
    }
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

}