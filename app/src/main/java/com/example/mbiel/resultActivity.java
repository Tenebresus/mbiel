package com.example.mbiel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class resultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.i("TEst", "OUTPUT");

        Bitmap decodedByte = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("PHOTO"), 0, getIntent().getByteArrayExtra("PHOTO").length);

        ImageView resultImage = (ImageView) findViewById(R.id.resultImage);
        resultImage.setImageBitmap(decodedByte);
    }
}