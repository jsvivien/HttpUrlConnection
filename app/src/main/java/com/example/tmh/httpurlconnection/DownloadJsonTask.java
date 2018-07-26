package com.example.tmh.httpurlconnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadJsonTask extends AsyncTask<String, Void, String> {
    private TextView mTextJson;

    public DownloadJsonTask(TextView mTextJson) {
        this.mTextJson = mTextJson;
    }

    @Override
    protected String doInBackground(String... strings) {
        String textUrl = strings[0];

        InputStream in = null;
        BufferedReader br = null;
        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));

                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(br);
        }
        return null;
    }

    // Khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi.
    // Download thành công, update kết quả lên giao diện.
    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            this.mTextJson.setText(result);
        } else {
//            Log.e("MyMessage", "Failed to fetch data!");
        }
    }
}
