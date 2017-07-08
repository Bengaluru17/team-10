package com.example.chiku.reachinghands;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class InventoryList extends Fragment {
    RecyclerView recyclerView;
    InventoryAdaptor inventoryAdaptor;
    List<InventorySingle> inventoryList = new ArrayList<>();
    private int tabNum;
    private TextView tabName;

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
        tabName = (TextView)view.findViewById(R.id.tabName);

        tabNum = (int)getArguments().getInt("tab");
        if(tabNum == 1){
            tabName.setText("Boys");
        }
        else if(tabNum == 2){
            tabName.setText("Girls");
        }
        else {
            tabName.setText("Kitchen");
        }
        //new inventories
        inventoryList.add(new InventorySingle("1","clothes", 25));
        inventoryList.add(new InventorySingle("2","clothes", 25));
        inventoryList.add(new InventorySingle("3","vegetables", 35));
        inventoryList.add(new InventorySingle("4","bottles", 30));

        recyclerView = (RecyclerView)view.findViewById(R.id.inventory_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        inventoryAdaptor = new InventoryAdaptor(inventoryList);
        recyclerView.setAdapter(inventoryAdaptor);
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
}