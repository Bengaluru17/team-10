package com.example.chiku.reachinghands;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestsForApproval extends AppCompatActivity {
    ListView lv;
    EditText ed1;
    ArrayAdapter<String> adapter;
    String address = "http://192.168.43.201/cfg/GetData.php";
    InputStream is = null;
    String line = null;
    String result = null;

    String[] data;
    String[] mycount;
    String[] threshold;
    String[] finaldata;
    String[] approv;
    int[] selectedindex;

    int tv,cv,datalength;
    int j;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_for_approval);
        lv = (ListView) findViewById(R.id.listView1);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getData();

        ed1 = (EditText) findViewById(R.id.editText2);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, finaldata);
        lv.setAdapter(adapter);

    }

    public void btn_request(View v){
        String item_name;
        item_name = ed1.getText().toString();
        //Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();
        int u;
        for(u=0;u<data.length;u++){
            if(data[u].equalsIgnoreCase(item_name)){

                Toast.makeText(getApplicationContext(),"Request Initiated",Toast.LENGTH_LONG).show();
                break;
            }
            else{
                continue;
            }
        }
        String method = "register";
        BackGroundTask backGroundTask = new BackGroundTask(this);
        backGroundTask.execute(method,item_name);
        finish();
        Intent i = new Intent(getApplicationContext(),AfterLogin.class);
        startActivity(i);

    }


    private void getData() {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            is = new BufferedInputStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //reading is content into string
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();
            result = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //parse json data
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            data = new String[ja.length()];
            mycount = new String[ja.length()];
            threshold = new String[ja.length()];
            finaldata = new String[ja.length()];
            selectedindex = new int[ja.length()];
            approv = new String[ja.length()];

            datalength = ja.length();
            // condata=new String[2*ja.length()];
                int k =0;
            for (int i = 0; i < ja.length(); i++) {
                jo=ja.getJSONObject(i);
                data[i]=jo.getString("item_name");
                //finaldata[i]=" ";
                mycount[i]=jo.getString("current_count");
                threshold[i]=jo.getString("threshold_count");
                approv[i]=jo.getString("approval");
                tv = Integer.parseInt(threshold[i]);
                cv = Integer.parseInt(mycount[i]);

                if(cv<=tv){
                    finaldata[k]="";
                    finaldata[k]=finaldata[k].concat(data[i]);
                    finaldata[k]=finaldata[k].concat(" ");
                    finaldata[k]=finaldata[k].concat(mycount[i]);

                    k++;

                }
                //data[i]=data[i].concat(" ");
                // data[i]=data[i].concat(threshold[i]);
            }
            for(j=k;j<ja.length();j++){
                finaldata[j]=finaldata[j].concat(" ");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
