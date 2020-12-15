package com.example.mbiel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

                Uri uri = data.getData();
                InputStream iStream = null;
                try {
                   iStream =   getContentResolver().openInputStream(uri);
                    byte[] inputData = getBytes(iStream);
                    createFile(inputData);

                } catch (IOException e) {
                    e.printStackTrace();
                }


//                System.out.println(data.getDataString());
//                if(file.exists()){
//                    System.out.println(file);
//
//                    new Thread(new Runnable() {
//                        public void run(){
//                            String a = null;
//                            try {
//                                a = runA("http://86.83.86.194:5000/test");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println(a);
//                        }
//                    }).start();
//                }else{
//                    System.out.println("cant find file");
//                }

            }
        }
    }

    OkHttpClient client = new OkHttpClient();

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

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
    private void createFile(byte[] fileData) {
                try {
                    //Create directory..
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Images");
            File dir = new File(root + File.separator);
            if (!dir.exists()) dir.mkdir();

            //Create file..
            File file = new File(root + File.separator + "image.jpg");
            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            out.write(fileData);
            out.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}