package com.example.sendobjects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sendobjects.models.Constants;
import com.example.sendobjects.models.Student;
import com.google.gson.Gson;

public class ObjectViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_viewer);
        setTitle("Student Details");
        getObject();
    }

    /**
     * This function will take the object from sender activity and will set the data in object viewer
     * activity.
     */
    private void getObject() {

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra(Constants.OBJECT);
        Student obj = gson.fromJson(strObj, Student.class);
        TextView name = findViewById(R.id.showNameTV);
        name.setText(obj.name);
        TextView gender = findViewById(R.id.showGenderTV);
        gender.setText(obj.gender);
        TextView rollNo = findViewById(R.id.showRollTV);
        rollNo.setText(obj.rollNo);
        TextView phnNo = findViewById(R.id.showPhnTV);
        phnNo.setText(obj.phnNo);
    }


    /**
     * This function will change the activity to Sender Activity.
     * @param view
     *//*
    public void goBack(View view) {

        Intent intent = new Intent(this, ObjectSenderActivity.class);
        startActivity(intent);
    }*/
}