package com.example.kchat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class Data {
    public static JSONArray messages;

    public void stuff(){
        for(int i = 0; i < messages.length(); i++) {
            try {
                String name = messages.getJSONObject(i).getString("name");
                String message = messages.getJSONObject(i).getString("message");
                Log.d("API", "Name: " + name + "message: " + message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
