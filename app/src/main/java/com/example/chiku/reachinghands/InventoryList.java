package com.example.chiku.reachinghands;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class InventoryList extends Fragment {
    RecyclerView recyclerView;
    InventoryAdaptor inventoryAdaptor;
    List<InventorySingle> inventoryList = new ArrayList<>();
    private int tabNum;
    private EditText tabName;
    private String aa="";

    public static InventoryList newInstance(int tabNum){
        Bundle args = new Bundle();
        args.putInt("tab", tabNum);
        InventoryList fragment = new InventoryList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        tabName = (EditText) view.findViewById(R.id.tabName);

        tabNum = (int)getArguments().getInt("tab");
        if(tabNum == 1){
            tabName.setText("BOYS DORM");
        }
        else if(tabNum == 2){
            tabName.setText("GIRLS DORM");
        }
        else {
            tabName.setText("KITCHEN");
        }
        //new inventories
        /*inventoryList.add(new InventorySingle("clothes", 25));
        inventoryList.add(new InventorySingle("clothes", 25));
        inventoryList.add(new InventorySingle("vegetables", 35));
        inventoryList.add(new InventorySingle("bottles", 30));*/

        recyclerView = (RecyclerView)view.findViewById(R.id.inventory_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //fething the data
        AsyncFetchInventory asyncFetchInventory = new AsyncFetchInventory();
        asyncFetchInventory.execute(new String[]{});


        /*inventoryAdaptor = new InventoryAdaptor(inventoryList);
        recyclerView.setAdapter(inventoryAdaptor);*/
        //inventoryAdaptor.notifyDataSetChanged();
        return view;
    }


    private class InventoryHolder extends RecyclerView.ViewHolder{
        private TextView item_name;
        private TextView current_item_count;

        public InventoryHolder(View itemView) {
            super(itemView);
            item_name = (TextView)itemView.findViewById(R.id.item_name);
            current_item_count = (TextView)itemView.findViewById(R.id.current_item_count);
        }
        public void bindInventory(InventorySingle inventorySingle){
            item_name.setText(inventorySingle.getName());
            current_item_count.setText(inventorySingle.getCurrent_count()+"");
        }
    }

    private class InventoryAdaptor extends RecyclerView.Adapter<InventoryHolder>{
        private List<InventorySingle> lst = new ArrayList<>();
        InventoryAdaptor(List<InventorySingle> inven){
            lst = inven;
        }

        @Override
        public InventoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.activity_inventory_holder, parent, false);
            return new InventoryHolder(view);
        }

        @Override
        public void onBindViewHolder(InventoryHolder holder, int position) {
            InventorySingle inventorySingle = lst.get(position);
            //Toast.makeText(getActivity(), inventorySingle.getName(), Toast.LENGTH_SHORT).show();
            holder.bindInventory(inventorySingle);
        }

        @Override
        public int getItemCount() {
            return lst.size();
        }
    }

    private class AsyncFetchInventory extends AsyncTask {

        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        private char ch;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //Toast.makeText(getActivity(), tabNum+"", Toast.LENGTH_SHORT).show();
            String invenName="";
            char currentCount='a';
            //aa="hiiiiHello";

            try{
                String link = "http://10.0.2.2/ReachingHands.php";
                String data="";
                try {
                    data = URLEncoder.encode("tabNum", "UTF-8")+"="+URLEncoder.encode(tabNum+"","UTF-8")+"&"+URLEncoder.encode("work", "UTF-8")+"="+URLEncoder.encode("retrieve","UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //String data = "status=registered";

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String sb = "";
                String line = null;
                // Read Server Response

                int c = 1;
                //String name="", address="", phone="", comments="";
                while((line = rd.readLine()) != null) {
                    //sb.append(line);
                    sb = sb+line;
                    //break;
                }
                //aa=sb+"    "+sb.length();
                for(int i=0; i<sb.length(); i++){
                    if(c == 1){
                        if((ch=sb.charAt(i)) != '&'){
                            invenName = invenName+ch;
                        }
                        else{
                            c = 2;
                            //Toast.makeText(getActivity(), invenName, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(c == 2){
                        if((ch=sb.charAt(i)) != '&'){
                            currentCount = ch;
                        }
                        else{
                            c = 1;
                            InventorySingle inventorySingle = new InventorySingle(invenName, (int)currentCount);
                            inventoryList.add(inventorySingle);
                            invenName="";
                            currentCount='a';
                        }
                    }

                }
                return sb;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(Object result){
            inventoryAdaptor = new InventoryAdaptor(inventoryList);
            recyclerView.setAdapter(inventoryAdaptor);
            inventoryAdaptor.notifyDataSetChanged();
            //tabName.setText(aa);
            pdLoading.hide();
        }

    }
}
