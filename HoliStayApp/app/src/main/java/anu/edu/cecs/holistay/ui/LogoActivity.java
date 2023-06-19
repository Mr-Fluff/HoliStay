package anu.edu.cecs.holistay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import anu.edu.cecs.holistay.R;

/**
 * Activity to display an application logo when the application launches
 *
 * @Author: Srinivasa Sai Damarla (u7370240)
 */
public class LogoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        },1500);
    }
}