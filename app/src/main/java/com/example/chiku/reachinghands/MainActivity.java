package com.example.chiku.reachinghands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usr,pwd;
    CheckBox adminchck,userchck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr= (EditText) findViewById(R.id.UserName);
        pwd= (EditText) findViewById(R.id.Password);
        adminchck= (CheckBox) findViewById(R.id.checkBoxAdmin);
        userchck= (CheckBox) findViewById(R.id.checkBoxUser);
    }
    public void Login(View view)
    {
        String user=usr.getText().toString();
        String pass=pwd.getText().toString();
        if(adminchck.isChecked())
        {
            if(user.equals("Santosh")&&pass.equals("Reaching"))
            {
                Intent i=new Intent(getApplicationContext(),SantoshHome.class);
                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
        }
        if(userchck.isChecked())
        {
            if(user.equals("Staff")&&pass.equals("Hands"))
            {
                Intent i = new Intent(getApplicationContext(),AfterLogin.class);
                startActivity(i);
            }
            else
            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
        }
    }
}
