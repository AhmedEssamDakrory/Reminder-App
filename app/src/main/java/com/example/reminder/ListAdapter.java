package com.example.reminder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class CustomAdapter implements ListAdapter {
     ArrayList<ReminderInfo> list;
     Context context;

     public CustomAdapter(Context context, ArrayList<ReminderInfo> remindersList){
        this.context = context;
        this.list = remindersList;
     }

     @Override
     public boolean areAllItemsEnabled() {
         return false;
     }
     @Override
     public boolean isEnabled(int position) {
         return true;
     }
     @Override
     public void registerDataSetObserver(DataSetObserver observer) {
     }
     @Override
     public void unregisterDataSetObserver(DataSetObserver observer) {
     }
     @Override
     public int getCount() {
         return list.size();
     }
     @Override
     public Object getItem(int position) {
         return position;
     }
     @Override
     public long getItemId(int position) {
         return position;
     }
     @Override
     public boolean hasStableIds() {
         return false;
     }
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         ReminderInfo reminderData = list.get(position);
         String currentActivity = this.context.getClass().getName();
         if(convertView==null) {
             LayoutInflater layoutInflater = LayoutInflater.from(context);
             convertView=layoutInflater.inflate(R.layout.reminders_row, null);

             TextView itemName =convertView.findViewById(R.id.iname);
             itemName.setText(reminderData.name);
             View sideLine = convertView.findViewById(R.id.sideLine);
             if(reminderData.important == true){
                 sideLine.setVisibility(View.VISIBLE);
             }
             else{
                 sideLine.setVisibility(View.INVISIBLE);
             }

         }
         return convertView;
     }

     public void update(int pos , String data, boolean imp){
         list.set(pos, new ReminderInfo(data, imp));
     }

     @Override
     public int getItemViewType(int position) {
         return position;
     }
     @Override
     public int getViewTypeCount() {
         return list.size();
     }
     @Override
     public boolean isEmpty() {
         return false;
     }


}
