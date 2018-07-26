package com.example.tmh.httpurlconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextJson;
    private Button mButtonJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextJson = (TextView) findViewById(R.id.text_json);
        mButtonJson = (Button) findViewById(R.id.button_json);
    }

    private boolean checkInternetConnection() {
        // Lấy ra bộ quản lý kết nối.
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Thông tin mạng đang kích hoạt.
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

    // Khi người dùng click vào nút "Download Json".
    public void downloadAndShowJson(View view) {
        boolean networkOK = this.checkInternetConnection();
        if (!networkOK) {
            return;
        }
        String jsonUrl = "https://api.github.com/users/google/repos";

        // Tạo một đối tượng làm nhiệm vụ download nội dung json từ URL.
        DownloadJsonTask task = new DownloadJsonTask(this.mTextJson);


        // Thực thi nhiệm vụ (Truyền vào URL).
        task.execute(jsonUrl);
    }
}
