package com.example.reminder;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class RemindersSimpleCursorAdapter extends SimpleCursorAdapter {

    public RemindersSimpleCursorAdapter(Context context, int layout, Cursor c, String[]
            from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        View sideView = view.findViewById(R.id.sideLine);
        TextView textView = view.findViewById(R.id.iname);
        Reminder reminder = new Reminder(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        textView.setText(reminder.getContent());
        if(reminder.getImportant() == 1){
            sideView.setBackgroundColor(context.getResources().getColor(R.color.importantItemColor));
        } else{
            sideView.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }

}
