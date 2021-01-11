package com.example.mbiel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        Button returnHomeButton = (Button) findViewById(R.id.return_main);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ct = MainActivity.getAppContext();

                Intent intent = new Intent(ct, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ct.startActivity(intent);
            }
        });

        Button saveImageButton = (Button) findViewById(R.id.save_image);
        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        decodedByte,
                        "image_with_filter",
                        "image with a filter added"
                );

                Context ct = MainActivity.getAppContext();

                Intent intent = new Intent(ct, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ct.startActivity(intent);
            }
        });
    }
}