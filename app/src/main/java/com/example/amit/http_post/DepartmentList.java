package com.example.amit.http_post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartmentList extends AppCompatActivity {


    TextView name;

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
       finishAffinity();

        /*Intent intent = new Intent(DepartmentList.this,DepartmentList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);*/
       // Toast.makeText(DepartmentList.this,"hello",Toast.LENGTH_SHORT).show();
      //moveTaskToBack(true);
        //finish();
        //System.exit(0);
      // finish();

    }

    ArrayList <DepartmentSetGet> arr=null;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        lv=(ListView)findViewById(R.id.lv);
        name=(TextView)findViewById(R.id.name);
        /*String fname=getIntent().getExtras().getString("firstname");
        String lname=getIntent().getExtras().getString("lastname");


        name.setText(fname+" "+lname);*/

        DepartmentTask departmentTask=new DepartmentTask();
        departmentTask.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.myaction){

            SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
            sp.edit().clear().commit();
            finish();

            Intent logout =new Intent(DepartmentList.this,Login.class);
            startActivity(logout);

        }
        return super.onOptionsItemSelected(item);
    }

    class DepartmentTask extends AsyncTask<String,Integer,Long>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            lv.setAdapter(new DepartmentAdapter());
        }

        @Override
        protected Long doInBackground(String... strings) {

            arr=new ArrayList<>();
            JSONParser parser=new JSONParser();
            try{
                JSONObject obj=parser.getJsonFromURL("http://220.225.80.177/drbookingapp/bookingapp.asmx/GetDepartment?");
                JSONArray departmentdetail=obj.getJSONArray("DepartmentDetails");
                for(int i=0;i<=departmentdetail.length();i++){

                    JSONObject detail=departmentdetail.getJSONObject(i);
                    int departmentid=detail.getInt("departmentid");
                    Log.i("deparment id",String.valueOf(departmentid));
                    String departmentname=detail.getString("departmentname");
                    DepartmentSetGet departmentSetGet=new DepartmentSetGet();
                    departmentSetGet.setDepartmentId(departmentid);
                    departmentSetGet.setDepartmentName(departmentname);
                    arr.add(departmentSetGet);


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public class DepartmentAdapter extends BaseAdapter{

    TextView departmentid,departmentname;
        DepartmentSetGet obj=new DepartmentSetGet();


        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            obj=arr.get(i);
            LayoutInflater layout=getLayoutInflater();
            View v=layout.inflate(R.layout.departmentdetail,viewGroup,false);
            departmentid=(TextView)v.findViewById(R.id.Did);
            departmentname=(TextView)v.findViewById(R.id.Dname);
            departmentid.setText(""+obj.getDepartmentId());
            departmentname.setText(obj.getDepartmentName());



            return v;
        }
    }
}
