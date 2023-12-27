package com.example.doanqlsinhvien.Control.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doanqlsinhvien.R;


public class CustomViewStudent extends LinearLayout {
    TextView txtName, txtDOB, txtPhone, txtEmail, statement;
    public CustomViewStudent(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.inflate(R.layout.item_student, this, true);
        txtName = findViewById(R.id.txtStudentName);
        txtDOB = findViewById(R.id.txtStudentDOB);
        txtPhone = findViewById(R.id.txtStudentPhone);
        txtEmail = findViewById(R.id.txtStudentEmail);
        statement = findViewById(R.id.txtStudentStatement);
    }
}
