package com.example.kchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity{
    private RequestQueue queue;
    private int timeSinceLast = 0;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.queue = Volley.newRequestQueue(this);
        scheduleGetMessages();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void send(View view) {
        EditText comp = findViewById(R.id.messageEntry);
        String message = comp.getText().toString();
        if(!message.equals("")){
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
            receive();
        }
    }

    public void receive() {
        JsonArrayRequest stringRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL + "/messages",null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                Data.messages = response;
                                TextView text = findViewById(R.id.messages);
                                text.setText(Data.getMessageString());
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("API", error.toString());
                    }
                });
        queue.add(stringRequest);
    }

    public void scheduleGetMessages() {
        handler.postDelayed(new Runnable() {
            public void run() {
                receive();
                if(Data.messages != null){
                    Log.d("API", Data.messages.toString());
                }
                handler.postDelayed(this, Constants.GET_REQUEST_DELAY);
            }
        }, Constants.GET_REQUEST_DELAY);
    }
}
