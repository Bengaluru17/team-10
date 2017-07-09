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

import static com.example.chiku.reachinghands.R.id.current_item_count;
import static com.example.chiku.reachinghands.R.id.item_name;

public class PendingApprovalSantosh extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ApprovalNeeded> approvalNeededList = new ArrayList<>();
    private List<String> approvedItems = new ArrayList<>();
    private Button approvedButton;
    private ApprovalNeededAdaptor approvalNeededAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_approval_santosh);
        approvedButton = (Button)findViewById(R.id.approved_button);

        recyclerView = (RecyclerView)findViewById(R.id.pending_approval_santosh_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //approvalNeededAdaptor = new ApprovalNeededAdaptor(approvalNeededList);
        //recyclerView.setAdapter(approvalNeededAdaptor);

        final AsyncFetchApprovalNeeded asyncFetchApprovalNeeded = new AsyncFetchApprovalNeeded();
        asyncFetchApprovalNeeded.execute(new String[]{});

        approvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(String appN : approvedItems){
                    //Toast.makeText(PendingApprovalSantosh.this, appN+"   "+approvedItems.size(), Toast.LENGTH_SHORT).show();
                    AsyncUpdateApprovedItems asyncUpdateApprovedItems = new AsyncUpdateApprovedItems();
                    asyncUpdateApprovedItems.execute(new String[]{appN});
                }
                AsyncFetchApprovalNeeded asyncFetchApprovalNeede = new AsyncFetchApprovalNeeded();
                asyncFetchApprovalNeede.execute(new String[]{});
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
            }
            else {
                approvalNeededCheckBox.setChecked(true);
                approvalNeededName.setText(approvalNeeded.getName());
                approvedItems.add(approvalNeeded.getName());
                //Toast.makeText(PendingApprovalSantosh.this, approvalNeededName.getText()+"   from bindholderViewHolder", Toast.LENGTH_SHORT).show();
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
            LayoutInflater inflater = LayoutInflater.from(PendingApprovalSantosh.this);
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

        ProgressDialog pdLoading = new ProgressDialog(PendingApprovalSantosh.this);
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

                            }
                            else {
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


    private class AsyncUpdateApprovedItems extends AsyncTask {

        ProgressDialog pdLoading = new ProgressDialog(PendingApprovalSantosh.this);
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
            String ap = (String)params[0];
            //Toast.makeText(getActivity(), tabNum+"", Toast.LENGTH_SHORT).show();
            String name="";
            String sb = "";
            //aa="hiiiiHello";

            try{
                String link = "http://10.0.2.2/ReachingHands.php";
                String data="";
                //String data = "status=registered";

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    try {
                        //aa=aa+"............"+ap;
                        data = URLEncoder.encode("work", "UTF-8")+"="+URLEncoder.encode("updateApprovedItems","UTF-8")+"&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(ap+"","UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    // Read Server Response

                    //String name="", address="", phone="", comments="";
                    while((line = rd.readLine()) != null) {
                        //sb.append(line);
                        sb = sb+line;
                        //break;
                    }

                aa=aa+sb;
                return sb;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(Object result){
            //Toast.makeText(PendingApprovalSantosh.this, "Items Approved", Toast.LENGTH_LONG).show();
            Toast.makeText(PendingApprovalSantosh.this, aa, Toast.LENGTH_LONG).show();
            pdLoading.hide();
        }

    }
}
