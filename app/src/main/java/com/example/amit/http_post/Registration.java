package com.example.amit.http_post;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    JSONObject json ;

    EditText fname,lname,uname,pwd,addr,phn,email;
    Button login;
    String username,firstname,lastname,password,address,phnnum,emailid,Message;
    JSONArray arrmsg = null;

    private String link="http://220.225.80.177/drbookingapp/bookingapp.asmx/UserRegistration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        fname=(EditText)findViewById(R.id.firstname);
        lname=(EditText)findViewById(R.id.lastname);
        uname=(EditText)findViewById(R.id.username);
        pwd=(EditText)findViewById(R.id.password);
        addr=(EditText)findViewById(R.id.address);
        phn=(EditText)findViewById(R.id.phoneno);
        email=(EditText)findViewById(R.id.email);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstname=fname.getText().toString();
                lastname=lname.getText().toString();
                username=uname.getText().toString();
                 password=pwd.getText().toString();
                 address=addr.getText().toString();
                 phnnum=phn.getText().toString();
                emailid=email.getText().toString();
                ConnectivityManager connmangr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connmangr.getActiveNetworkInfo();
                if(networkInfo!=null && networkInfo.isConnected()) {

                    new SigninActivity().execute();
                    Intent login = new Intent(Registration.this, Login.class);
                    startActivity(login);
                 //   finish();

                }
                else {

                    Toast.makeText(Registration.this,"No data connection",Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    public class SigninActivity extends AsyncTask<String,Void,String> {

        private ProgressDialog dialog;

        protected void onPreExecute(){


            super.onPreExecute();
            dialog = new ProgressDialog(Registration.this);
            dialog.setMessage("wait......");

            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                JSONParser jParser = new JSONParser();
                HashMap<String, String> hashmap = new HashMap<String, String>();
                hashmap.put("fname", firstname);
                hashmap.put("lname", lastname);
                hashmap.put("username", username);
                hashmap.put("pwd", password);
                hashmap.put("address", address);
                hashmap.put("phoneno", phnnum);
                hashmap.put("email", emailid);

                // getting JSON string from URL
                json = jParser.insertJsonFromUrl(link, "POST", hashmap);
                Message = json.getString("Message");


            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result){
            dialog.dismiss();


            Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG).show();

        }
    }
}
