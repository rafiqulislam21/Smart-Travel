package com.example.ribijoy.smarttravelguide;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather extends AsyncTask<String,Void,String> {
    String result;


    @Override
    protected String doInBackground(String...urls) {
        result = "";
        URL link;
        HttpURLConnection myConnection = null;

        try {
            link = new URL(urls[0]);
            myConnection = (HttpURLConnection)link.openConnection();
            InputStream in = myConnection.getInputStream();
            InputStreamReader myStreamReader = new InputStreamReader(in);
            int data = myStreamReader.read();
            while (data!=-1){
                char current = (char)data;
                result+=current;
                data = myStreamReader.read();
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject myObject = new JSONObject(result);
            JSONObject myObject2 = new JSONObject(result);
            //JSONObject myObject4 = new JSONObject(result);


            JSONObject main = new JSONObject(myObject.getString("main"));

            JSONObject wind = new JSONObject(myObject2.getString("wind"));

            //JSONObject weather = new JSONObject(myObject4.getString("weather"));

            String temperature = main.getString("temp");
            String humidity = main.getString("humidity");
            //String pressure = main.getString("pressure"); //experimental

            String windSpeed = wind.getString("speed");

            //String status = weather.getString("main");


            //activity_showPlace.txtPlaceWeatherStatus.setText(status);
            activity_showPlace_map.txtPlaceWeatherTemperature.setText(Double.toString(Math.round(Double.parseDouble(temperature)-273)));
            activity_showPlace_map.txtPlaceWeatherHumidity.setText(humidity);
            activity_showPlace_map.txtPlaceWeatherWindSpeed.setText(windSpeed);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
