package com.example.mbiel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mbiel.volleyPost;

public class filterActivity extends AppCompatActivity {

    public String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        encodedImage = getIntent().getStringExtra("PHOTO");
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
    }

}