package com.example.reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity implements DialogActivity.DialogListener {
    private RemindersDbAdapter remindersDbAdapter;
    private RemindersSimpleCursorAdapter remindersSimpleCursorAdapter;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        remindersDbAdapter = new RemindersDbAdapter(this);
        remindersDbAdapter.open();
        remindersSimpleCursorAdapter = new RemindersSimpleCursorAdapter(this, R.layout.reminders_row,
                cursor, new String[]{RemindersDbAdapter.COL_CONTENT,RemindersDbAdapter.COL_IMPORTANT},
                new int[]{R.id.iname, R.id.sideLine}, 0);
        listView = findViewById(R.id.list);
        listView.setAdapter(remindersSimpleCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String[] items = new String[]{"Edit Reminder", "Delete Reminder"};
                new AlertDialog.Builder(ReminderActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    openDialog("Edit", position);
                                }
                                else{
                                    Cursor c = ((RemindersSimpleCursorAdapter) listView.getAdapter()).getCursor();
                                    c.moveToPosition(position);
                                    remindersDbAdapter.deleteReminderById(c.getInt(0));
                                    updateList();
                                }
                            }
                        })
                        .show();
            }
        });
        updateList();
    }

    public void openDialog(String type, int pos){
        String data = "";
        boolean imp = false;
        if(pos != -1){
            Cursor c = ((RemindersSimpleCursorAdapter) listView.getAdapter()).getCursor();
            c.moveToPosition(pos);
            data = c.getString(1);
            imp = c.getInt(2) == 1;
        }
        DialogActivity dialog = new DialogActivity(type, pos, imp, data);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void editReminder(String data, boolean imp, int pos){
        Cursor c = ((RemindersSimpleCursorAdapter) listView.getAdapter()).getCursor();
        c.moveToPosition(pos);
        remindersDbAdapter.updateReminder(new Reminder(c.getInt(0), data, imp?1:0));
        this.updateList();
    }

    @Override
    public void addReminder(String data, boolean imp){
        remindersDbAdapter.createReminder(data, imp);
        this.updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_reminder:
                openDialog("Add", -1);
                break;
            case R.id.exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateList(){
        cursor = remindersDbAdapter.fetchAllReminders();
        remindersSimpleCursorAdapter.changeCursor(cursor);
    }


}
