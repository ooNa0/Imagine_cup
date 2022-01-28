package com.example.imagincup.back.task;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmotionAsyncTask extends AsyncTask<String, Void, JSONObject> {

    private JSONObject responseJson = null;

    public EmotionAsyncTask() {
        super();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        String KEY = "e3fe5d90c4de453ea23e2af93e90cea5";
        String ENDPOINT = "https://text-emotion.cognitiveservices.azure.com/";
        JSONObject data = null;

        try {
            URL url = new URL(ENDPOINT + "/text/analytics/v3.0/sentiment");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestProperty("Ocp-Apim-Subscription-Key", KEY);

            httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject json = new JSONObject();
            json.put("id", "1");
            json.put("text", params[0]);
            jsonArray.put(json);
            jsonObject.put("documents", jsonArray);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.flush();

            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder outResult = new StringBuilder();
                String inputLine = null;

                while ((inputLine = bufferedReader.readLine()) != null) {
                    outResult.append(inputLine);
                }

                bufferedReader.close();

                responseJson = new JSONObject(outResult.toString());
                JSONArray emotions = responseJson.getJSONArray("documents");
                data = emotions.getJSONObject(0);
            } else {
                // 에러 발생
                inputStream = httpURLConnection.getErrorStream();
            }
        } catch (Exception e) {
        }
        return data;
    }
}
