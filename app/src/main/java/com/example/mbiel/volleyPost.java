package com.example.mbiel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class volleyPost extends MainActivity {
    public void volleyPostRequest(String dat, String url){
        String postUrl = url;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getAppContext());

        JSONObject postData = new JSONObject();
        try {
            postData.put("data", dat);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                byte[] decodedString = new byte[0];
                try {
                    String resp = response.getString("data");

                    decodedString = Base64.decode(resp, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                            0,decodedString.length);
                    Context ct = MainActivity.getAppContext();

                    Intent intent = new Intent(ct, resultActivity.class);

                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    decodedByte.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                    intent.putExtra("PHOTO", bs.toByteArray());

                    //https://stackoverflow.com/questions/3918517/calling-startactivity-from-outside-of-an-activity-context
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    ct.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
