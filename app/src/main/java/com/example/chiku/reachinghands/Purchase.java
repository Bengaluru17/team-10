package com.example.chiku.reachinghands;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class Purchase extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> approvedItems = new ArrayList<>();
    private List<ApprovalNeeded> approvalNeededList = new ArrayList<>();
    private ApprovalNeededAdaptor approvalNeededAdaptor;
    private Button purchaseButton;
    EditText bil;
    int money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        purchaseButton = (Button)findViewById(R.id.purchase_button);
        bil= (EditText) findViewById(R.id.bill);
        //money=Integer.parseInt(bil.getText().toString());
        recyclerView = (RecyclerView)findViewById(R.id.approved_for_purchase_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AsyncFetchApprovalNeeded asyncFetchApprovalNeeded = new AsyncFetchApprovalNeeded();
        asyncFetchApprovalNeeded.execute(new String[]{});

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                money=Integer.parseInt(bil.getText().toString());
                Toast.makeText(Purchase.this, "size="+approvalNeededList.size(), Toast.LENGTH_SHORT).show();
                for(String ap : approvedItems){
                    Toast.makeText(Purchase.this, ap+"       rr", Toast.LENGTH_SHORT).show();
                    AsyncDeleteApproved asyncDeleteApproved = new AsyncDeleteApproved();
                    asyncDeleteApproved.execute(new String[]{ap});
                }
                AsyncFetchApprovalNeeded asyncFetchApprovalNeeded1 = new AsyncFetchApprovalNeeded();
                asyncFetchApprovalNeeded1.execute(new String[]{});

                AsyncBillFetchAndUpdate asyncBillFetchAndUpdate= new AsyncBillFetchAndUpdate();
                asyncBillFetchAndUpdate.execute(new String[]{});
            }
        });

    }

    private class ApprovalNeededHolder extends RecyclerView.ViewHolder{
        private CheckBox approvalNeededCheckBox;
        private TextView approvalNeededName;

        public ApprovalNeededHolder(View itemView) {
            super(itemView);
            approvalNeededCheckBox = (CheckBox) itemView.findViewById(R.id.approval_needed_check_box);
            approvalNeededName = (TextView)itemView.findViewById(R.id.approval_needed_name);
        }
        public void bindApprovalNeeded(final ApprovalNeeded approvalNeeded){
            //Toast.makeText(PendingApprovalSantosh.this, approvalNeeded.getName()+"   from bindholderViewHolder", Toast.LENGTH_SHORT).show();
            if(approvalNeeded.getCheck().equalsIgnoreCase("Yes")){
                //approvalNeededCheckBox.setChecked(true);
                approvalNeededCheckBox.setChecked(true);
                approvalNeededName.setText(approvalNeeded.getName());
                approvedItems.add(approvalNeeded.getName());
            }
            approvalNeededCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                int fla=0;
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b == true){
                        for(int i=0; i<approvedItems.size(); i++){
                            //Toast.makeText(PendingApprovalSantosh.this, approvedItems.get(i)+"  "+approvalNeeded.getName(), Toast.LENGTH_SHORT).show();
                            if(approvedItems.get(i).equals(approvalNeeded.getName())){
                                fla=1;  //already present, dont add again
                                break;
                            }
                        }
                        if(fla == 0) {
                            approvedItems.add(approvalNeeded.getName());
                        }
                        else {
                            fla = 0;
                        }
                    }
                    else{
                        approvedItems.remove(approvalNeeded.getName());
                    }
                }
            });
            //item_name.setText(inventorySingle.getName());
            //current_item_count.setText(inventorySingle.getCurrent_count()+"");
        }
    }

    private class ApprovalNeededAdaptor extends RecyclerView.Adapter<ApprovalNeededHolder>{
        private List<ApprovalNeeded> lst = new ArrayList<>();
        public ApprovalNeededAdaptor(List<ApprovalNeeded> appr){
            lst = appr;
        }

        @Override
        public ApprovalNeededHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(Purchase.this);
            View view = inflater.inflate(R.layout.approval_needed_holder, parent, false);
            return new ApprovalNeededHolder(view);
        }

        @Override
        public void onBindViewHolder(ApprovalNeededHolder holder, int position) {
            ApprovalNeeded approvalNeeded = lst.get(position);
            //Toast.makeText(PendingApprovalSantosh.this, approvalNeeded.getName()+"   from bindholder", Toast.LENGTH_SHORT).show();
            holder.bindApprovalNeeded(approvalNeeded);

        }

        @Override
        public int getItemCount() {
            return lst.size();
        }
    }
    private class AsyncFetchApprovalNeeded extends AsyncTask {

        ProgressDialog pdLoading = new ProgressDialog(Purchase.this);
        private char ch;
        private String aa="";

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            approvalNeededList = new ArrayList<>();
            //Toast.makeText(getActivity(), tabNum+"", Toast.LENGTH_SHORT).show();
            String name="";
            String check="";
            //aa="hiiiiHello";

            try{
                String link = "http://10.0.2.2/ReachingHands.php";
                String data="";
                try {
                    data = URLEncoder.encode("work", "UTF-8")+"="+URLEncoder.encode("retrieveApprovedNeeded","UTF-8");
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
                            name = name+ch;
                        }
                        else{
                            c = 2;
                            //Toast.makeText(getActivity(), invenName, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(c == 2){
                        if((ch=sb.charAt(i)) != '&'){
                            check = check+ch;
                        }
                        else{
                            c = 1;
                            ApprovalNeeded approvalNeeded = new ApprovalNeeded(name, check);
                            if(check.equals("Yes")){
                                approvalNeededList.add(approvalNeeded);
                            }
                            name="";
                            check="";
                        }
                    }

                }
                aa=sb;
                return sb;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }



        @Override
        protected void onPostExecute(Object result){
            approvalNeededAdaptor= new ApprovalNeededAdaptor(approvalNeededList);
            recyclerView.setAdapter(approvalNeededAdaptor);
            approvalNeededAdaptor.notifyDataSetChanged();
            //tabName.setText(aa);
            //Toast.makeText(PendingApprovalSantosh.this, aa, Toast.LENGTH_LONG).show();
            pdLoading.hide();
            //Toast.makeText(PendingApprovalSantosh.this, approvalNeededList.get(1).getName(), Toast.LENGTH_SHORT).show();
        }

    }
    private class AsyncBillFetchAndUpdate extends AsyncTask
    {
        String aa="";

        @Override
        protected Object doInBackground(Object[] objects) {
            String sb = "";
            try {
                String link = "http://10.0.2.2/ReachingHands.php";
                String data = "";
                try {
                    data = URLEncoder.encode("work", "UTF-8") + "=" + URLEncoder.encode("billing", "UTF-8")+"&"+URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(money+"", "UTF-8");
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

                String line = null;

                int c = 1;
                //String name="", address="", phone="", comments="";
                while((line = rd.readLine()) != null) {
                    //sb.append(line);
                    sb = sb+line;
                    //break;
                }
            }
            catch (Exception e)
            {

            }
            aa=sb;
            return sb;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(Purchase.this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }
    private class AsyncDeleteApproved extends AsyncTask {

        ProgressDialog pdLoading = new ProgressDialog(Purchase.this);
        private char ch;
        private String aa="";

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String nameItem = (String)params[0];
            //Toast.makeText(getActivity(), tabNum+"", Toast.LENGTH_SHORT).show();
            String name="";
            String check="";
            //aa="hiiiiHello";

            try{
                String link = "http://10.0.2.2/ReachingHands.php";
                String data="";
                try {
                    data = URLEncoder.encode("work", "UTF-8")+"="+URLEncoder.encode("deleteApproved","UTF-8")+"&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(nameItem,"UTF-8");
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

                aa=sb;
                return sb;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(Object result){
            //tabName.setText(aa);
            //Toast.makeText(PendingApprovalSantosh.this, aa, Toast.LENGTH_LONG).show();
            pdLoading.hide();
            //Toast.makeText(PendingApprovalSantosh.this, approvalNeededList.get(1).getName(), Toast.LENGTH_SHORT).show();
        }

    }
}
