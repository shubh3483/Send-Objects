package com.example.sendobjects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendobjects.databinding.ActivityObjectSenderBinding;
import com.example.sendobjects.models.Constants;
import com.example.sendobjects.models.Student;
import com.google.gson.Gson;

public class ObjectSenderActivity extends AppCompatActivity {

    ActivityObjectSenderBinding b;
    String name = "";
    String gender = "";
    String rollNo = "";
    String phnNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityObjectSenderBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setTitle("Enter Student Details");
        setupHideErrorForEditText();
        if(savedInstanceState == null){
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            name = preferences.getString(Constants.NAME,"");
            gender = preferences.getString(Constants.GENDER,"");
            rollNo = preferences.getString(Constants.ROLL_NO,"");
            phnNo = preferences.getString(Constants.PHONE_NO,"");
            b.nameInput.getEditText().setText(name);
            b.rollNoInput.getEditText().setText(rollNo);
            b.phnNoInput.getEditText().setText(phnNo);
            if(gender.equals("Male")){
                b.maleRadioBtn.setChecked(true);
            }
            else b.femaleRadioBtn.setChecked(true);
        }
    }

    TextWatcher textWatcherForRollNo;
    TextWatcher textWatcherForPhnNo;
    TextWatcher textWatcherForName;

    /**
     * This function is for hiding errors when the text is changed.
     */
    private void setupHideErrorForEditText() {

        textWatcherForRollNo = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideErrorForRollET();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        textWatcherForPhnNo = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideErrorForPhnET();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        textWatcherForName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideErrorForNameET();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        b.rollNoInput.getEditText().addTextChangedListener(textWatcherForRollNo);
        b.phnNoInput.getEditText().addTextChangedListener(textWatcherForPhnNo);
        b.nameInput.getEditText().addTextChangedListener(textWatcherForName);
    }


    /**
     * This is the function which checks for the correct input type in all the input fields and
     * sends object to another activity.
     * @param view
     */
    public void saveData(View view) {

        /**
         * This is for Name input.
         */
        name = b.nameInput.getEditText().getText().toString();
        if(name.isEmpty()){
            b.nameInput.setError("Enter your name");
            return;
        }
        b.nameInput.setError(null);


        /**
         * This is for radio buttons basically for selection of gender type.
         */
        gender = null;
        int type = b.radioGrp.getCheckedRadioButtonId();
        if(type == R.id.maleRadioBtn){
            gender = new String("Male");
        }
        else if(type == R.id.femaleRadioBtn){
            gender = new String("Female");
        }
        else{
            Toast.makeText(this, "Choose gender", Toast.LENGTH_SHORT).show();
            return;
        }


        /**
         * This is for Roll No input.
         */
        rollNo = b.rollNoInput.getEditText().getText().toString().trim();
        if(!rollNo.matches("^\\d{2}[a-zA-Z]*\\d{3}")){
            b.rollNoInput.setError("Incorrect Roll No");
            return;
        }
        b.rollNoInput.setError(null);


        /**
         * This is for Phone No input.
         */
        phnNo = b.phnNoInput.getEditText().getText().toString().trim();
        if(!phnNo.matches("^\\d{10}$")){
            b.phnNoInput.setError("Enter 10 digit number");
            return;
        }
        b.phnNoInput.setError(null);


        Student student = new Student(name,gender,rollNo,phnNo);
        Gson gson = new Gson();
        Intent intent = new Intent(this, ObjectViewerActivity.class);
        intent.putExtra(Constants.OBJECT,gson.toJson(student));
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        preferences.edit()
                .putString(Constants.NAME, name)
                .putString(Constants.ROLL_NO, rollNo)
                .putString(Constants.PHONE_NO, phnNo)
                .putString(Constants.GENDER, gender)
                .apply();

    }

    /**
     * These functions are for hiding error from the Phone Edit Text and Roll No Edit Text.
     */
    private void hideErrorForPhnET() {
        b.phnNoInput.setError(null);
    }

    private void hideErrorForRollET() {
        b.rollNoInput.setError(null);
    }

    private void hideErrorForNameET() {
        b.nameInput.setError(null);
    }
}