package com.example.imagincup.back.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.HttpResponse;
import com.example.imagincup.AnswerActivity;

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
        //this.mContext = mContext;
    }

    //ProgressDialog progressDialog;
    public Context mContext;

    // @Override protected Long doInBackground(URL... urls) { // 전달된 URL 사용 작업 return total; }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground 에서 받아온 total 값 사용 장소

        //progressDialog = ProgressDialog.show(mContext, mContext.getString(R.string.message_progress), null, true, true);
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        String KEY = "e3fe5d90c4de453ea23e2af93e90cea5";
        String ENDPOINT = "https://text-emotion.cognitiveservices.azure.com/"; // 따로 빼주기
        JSONObject data = null;

        try {
            // 2. HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송
            URL url = new URL(ENDPOINT + "/text/analytics/v3.0/sentiment"); // 주소가 저장된 변수

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

            // Request Body에 데이터를 담기위한 OutputStream 객체 생성
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            Log.d("a", json.toString());
            outputStream.flush();
            //outputStream.close();

            // 응답을 읽rl
            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d("TAG", "POST response code - " + responseStatusCode);

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                // 정상적인 응답 데이터
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

                Log.d("a", data.getString("sentiment"));
                JSONObject parcent = data.getJSONObject("confidenceScores");
                Log.d("double", String.valueOf(parcent.getDouble(data.getString("sentiment"))));

                Log.d("TAG", "POST response - " + outResult.toString());

            } else {
                // 에러 발생
                inputStream = httpURLConnection.getErrorStream();
            }


        } catch (Exception e) {
            Log.d("TAG", "InsertData: Error ", e);
        }
        return data;
    }
}
