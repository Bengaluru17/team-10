package com.example.chiku.reachinghands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
    }
    public void btn_approvalreq(View v){

        Intent i = new Intent(this,RequestsForApproval.class);
        startActivity(i);
    }
    public void btn_Inventory(View v){
        Intent i = new Intent(this,Inventory.class);
        startActivity(i);
    }
    public void btn_approvedreq(View v){
        Intent i = new Intent(this,ApprovedReq.class);
        startActivity(i);
    }
}
