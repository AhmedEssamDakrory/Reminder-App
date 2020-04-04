package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button letMeSeeButton = findViewById(R.id.b1);
        letMeSeeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent openReminder = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(openReminder);
            }
        });

    }
}
