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
    public void BtnInventory(View view)
    {
            startActivity(new Intent(getApplicationContext(),Inventory.class));
    }
    public void BtnApprovedReq(View view)
    {
        startActivity(new Intent(getApplicationContext(),ApprovedReq.class));
    }
    public void BtnReqForApproval(View view)
    {
        startActivity(new Intent(getApplicationContext(),RequestsForApproval.class));
    }
}
