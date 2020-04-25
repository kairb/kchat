package com.example.kchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity{
    private RequestQueue queue;
    private int timeSinceLast = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.queue = Volley.newRequestQueue(this);
    }

    public void send(View view) {
        EditText comp = findViewById(R.id.messageEntry);
        String message = comp.getText().toString();
        comp.setText("");
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, Constants.BASE_URL + "/messages?name=kai" + "&message=" + message ,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.d("API", response);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("API", error.toString());
                    }
                });
        queue.add(stringRequest);
    }

    public void receive() {
        JsonArrayRequest stringRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL + "/messages",null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                Data.messages = response;
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("API", error.toString());
                    }
                });
        queue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timeSinceLast - System.currentTimeMillis() > Constants.GET_REQUEST_DELAY){
            timeSinceLast = 0;
            receive();
            Log.d("API",Data.messages.toString());
        }
    }
}
