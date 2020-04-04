package com.example.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogActivity extends AppCompatDialogFragment {

    private EditText editText;
    private Button cancelButton, commitButton;
    private CheckBox impCheckBox;
    private String type, reminderName;
    private boolean imp;
    private int pos;
    DialogListener dialogListener;

    public interface DialogListener{
        public void editReminder(String data, boolean imp, int pos);
        public void addReminder(String data, boolean imp);
    }

    DialogActivity(String type, int pos, boolean imp, String data){
        this.type = type;
        this.reminderName = data;
        this.pos = pos;
        this.imp = imp;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog, null);
        builder.setView(view);
        builder.setTitle(type+" Reminder");
        editText = view.findViewById(R.id.editText);
        cancelButton = view.findViewById(R.id.cancel);
        commitButton = view.findViewById(R.id.commit);
        impCheckBox = view.findViewById(R.id.checkBox);
        editText.setText(this.reminderName);
        impCheckBox.setActivated(this.imp);
        commitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                reminderName = editText.getText().toString();
                imp = impCheckBox.isChecked();
                makeChange();
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dismiss();
            }
        });;
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement DialogListener");
        }

    }

    public void makeChange(){
        if(type.equals("Edit")){
            dialogListener.editReminder(reminderName, imp, pos);
        }
        else{
           dialogListener.addReminder(reminderName, imp);
        }
    }
}
