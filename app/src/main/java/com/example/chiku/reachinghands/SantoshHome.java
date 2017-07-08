package com.example.chiku.reachinghands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SantoshHome extends AppCompatActivity {

    TextView bal,spent,left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santosh_home);
        bal= (TextView) findViewById(R.id.textViewbal);
        spent= (TextView) findViewById(R.id.textViewSpent);
        left= (TextView) findViewById(R.id.textViewleft);
    }
    public void approvals_page(View view)
    {
        startActivity(new Intent(getApplicationContext(),PendingApprovals.class));
    }
}
