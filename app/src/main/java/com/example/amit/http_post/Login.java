package com.example.amit.http_post;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    String uname, pswd, Message, success, firstname, lastname;
    int userid;
    JSONObject myjson;

    EditText username, password;
    Button login, Register;


    private String loginUrl = "http://220.225.80.177/drbookingapp/bookingapp.asmx/UserLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.un);
        password = (EditText) findViewById(R.id.pswd);
        login = (Button) findViewById(R.id.loginb);
        Register = (Button) findViewById(R.id.Reg);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = username.getText().toString();
                pswd = password.getText().toString();
                if (username.getText().length() <= 0 || password.getText().length() <= 0) {
                    Toast.makeText(Login.this, "wrong login credential", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectivityManager connmangr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connmangr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        Mylogin mylogin = new Mylogin();
                        mylogin.execute();
                    } else {
                        Toast.makeText(Login.this, "No data connection", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(Login.this, Registration.class);
                startActivity(reg);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void savepref(String key, String value) {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    class Mylogin extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... strings) {


            JSONParser jParser = new JSONParser();
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("username", uname);
            hashmap.put("pwd", pswd);
            // getting JSON string from URL
            myjson = jParser.insertJsonFromUrl(loginUrl, "POST", hashmap);
            try {
                JSONObject obj = myjson.getJSONObject("UserDetails");
                firstname = obj.getString("fname");
                lastname = obj.getString("lname");
                userid = obj.getInt("userid");
                Message = myjson.getString("Message");
                success = myjson.getString("sucess");

/*if(success.equalsIgnoreCase("1")){
    AppData.suserid=obj.getInt("userid");
}*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Message == "null") {
                Toast.makeText(getBaseContext(), " " + userid, Toast.LENGTH_LONG).show();
                savepref("username", uname);
                savepref("password", pswd);
                finish();
                Intent departmentdetail = new Intent(Login.this, DepartmentList.class);
                departmentdetail.putExtra("firstname", firstname);
                departmentdetail.putExtra("lastname", lastname);
                // Closing all the Activities
                // departmentdetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                // departmentdetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(departmentdetail);
            } else {
                Toast.makeText(Login.this, "wrong credentials", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
