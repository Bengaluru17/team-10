package com.example.chiku.reachinghands;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Inventory extends AppCompatActivity {
    private ViewPager viewPager;
    private int[] tabNumbers = {1,2,3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        viewPager = (ViewPager)findViewById(R.id.view_pager_inventory_tab);
        FragmentManager fm = getSupportFragmentManager();
        /*Fragment frag = fm.findFragmentById(R.id.inventory_container);
        if(frag == null){
            frag = InventoryList.newInstance(1);
            fm.beginTransaction().add(R.id.inventory_container, frag).commit();
        }*/
        /*AsyncFetchInventory asyncFetchInventory = new AsyncFetchInventory();
        asyncFetchInventory.execute(new String[]{});*/
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                int tabNum = tabNumbers[position];
                return InventoryList.newInstance(tabNum);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }
}
