package com.example.amit.http_post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CountDownTimer(5000, 1000) {


            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(util.isConnected(SplashScreen.this)) {
                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    String uname = sp.getString("username", "");
                    String pwd = sp.getString("password", "");
                    if (!uname.equals("") && !pwd.equals("")) {
                        finish();
                        Intent Login = new Intent(getApplicationContext(), DepartmentList.class);
                        startActivity(Login);
                    }
                     else {
                        finish();
                        Intent i = new Intent(SplashScreen.this, Login.class);
                        startActivity(i);
                    }
                }
                else{
                    Toast.makeText(SplashScreen.this,"No internet connection",Toast.LENGTH_SHORT).show();
                }


            }
        }.start();


    }
}

