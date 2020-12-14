package com.example.mbiel;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    File file;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                file = new File(data.toUri(0));
                System.out.println(file);

                new Thread(new Runnable() {
                    public void run(){
                        String a = null;
                        try {
                            a = runA("http://86.83.86.194:5000/test");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(a);
                    }
                }).start();
            }
        }
    }

    OkHttpClient client = new OkHttpClient();

    String runA(String url) throws IOException {
        RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("messi","messi.jpg", RequestBody.create(MEDIA_TYPE_JPG, file)).build();
        Request request = new Request.Builder()
                .url(url)
                .post(req)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}