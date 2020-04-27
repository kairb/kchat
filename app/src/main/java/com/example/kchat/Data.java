package com.example.kchat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class Data {
    public static JSONArray messages;

    public static String getMessageString(){
        String result = "";
        for(int i = 0; i < messages.length(); i++) {
            try {
                String name = messages.getJSONObject(i).getString("name");
                String message = messages.getJSONObject(i).getString("message");
                String time = messages.getJSONObject(i).getString("time");
                result = result + "Name: " + name + " " + time  +" message: " + message +"\n";
                Log.d("API", "Name: " + name + " " + time  +" message: " + message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
