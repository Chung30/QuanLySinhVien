package com.example.doanqlsinhvien.Control.MarkStudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doanqlsinhvien.R;


public class CustomViewMarkStudent extends LinearLayout {
    TextView txtName, txtScore1, txtScore2, txtFinalScore;
    public CustomViewMarkStudent(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.inflate(R.layout.item_student_mark, this, true);
        txtName = findViewById(R.id.txtStudentName);
        txtScore1 = findViewById(R.id.txtScore1);
        txtScore2 = findViewById(R.id.txtScore2);
        txtFinalScore = findViewById(R.id.txtFinalScore);
    }
}
