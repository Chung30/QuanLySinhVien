package com.example.doanqlsinhvien.View.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doanqlsinhvien.Data.SQLiteAdapter;

import java.util.ArrayList;

import com.example.doanqlsinhvien.Control.MarkStudent.GridAdapterMarkStudent;
import com.example.doanqlsinhvien.Model.result;
import com.example.doanqlsinhvien.R;


public class MarkTeacher extends AppCompatActivity {
    ArrayList<result> lResult = new ArrayList<>();
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    Spinner spinnerSubjectMark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_teacher);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("idTeacher", -1);

        spinnerSubjectMark = findViewById(R.id.spinnerSubjectMark);
        ArrayList statusSubject = new ArrayList<>();
        statusSubject.add("Chọn lớp học");
        sqlite.Open();
        Cursor subject = sqlite.SQLTable("select nameClass from Subject a join Teach b on a.idSubject = b.idSubject" +
                " where idTeacher = " + id);
        while (subject.moveToNext()){
            statusSubject.add(subject.getString(0));
        }
        subject.close();
        sqlite.Close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusSubject);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubjectMark.setAdapter(adapter);
        spinnerSubjectMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sqlite.Open();
                // Xử lý khi một mục được chọn
                if(position == 0) lResult.clear();
                else{
                    lResult.clear();
                    Cursor cursor = sqlite.SQLTable("SELECT a.idStudent, name, c.idSubject FROM Student a " +
                            "JOIN Learn b ON a.idStudent = b.idStudent " +
                            "JOIN Subject c ON c.idSubject = b.idSubject " +
                            "WHERE c.nameClass = '" + statusSubject.get(position) + "' " +
                            "GROUP BY name " +
                            "ORDER BY name DESC");


                    while(cursor.moveToNext()){
                        int idStudent = cursor.getInt(0);
                        String nameStudent = cursor.getString(1);
                        int idSubject = cursor.getInt(2);

                        Cursor idStudentInResult = sqlite.SQLTable("select idStudent from Result where idStudent = " + idStudent + " and idSubject = " + idSubject);
                        ArrayList<Integer> lIdStudentInResult = new ArrayList<>();
                        while (idStudentInResult.moveToNext()){
                            lIdStudentInResult.add(idStudentInResult.getInt(0));
                        }
                        idStudentInResult.close();
                        //neu da co kq trong Result
                        if(lIdStudentInResult.contains(idStudent)){
                            Cursor cResult = sqlite.SQLTable("select idResult, score1, score2, finalScore, rStatement" +
                                    " from Result where idStudent = " + idStudent + " and idSubject = " + idSubject);
                            cResult.moveToFirst();
                            lResult.add(new result(cResult.getInt(0), nameStudent, cResult.getFloat(1),
                                    cResult.getFloat(2), cResult.getFloat(3), cResult.getInt(4)));
                            cResult.close();
                        }
                        else {
                            ContentValues valuesResult = new ContentValues();
                            valuesResult.put("score1", 0);
                            valuesResult.put("score2", 0);
                            valuesResult.put("finalScore", 0);
                            valuesResult.put("rStatement", 0);
                            valuesResult.put("idStudent", idStudent);
                            valuesResult.put("idSubject", idSubject);

                            sqlite.Insert("Result", valuesResult);
                            sqlite.Open();
                            Cursor result = sqlite.SQLTable("select idResult from Result where idStudent = " + idStudent + " and idSubject = " + idSubject);
                            if(result.moveToFirst()){
                                lResult.add(new result(result.getInt(0), nameStudent, 0,0,0,0));
                            }
                            result.close();
                        }
                    }
                    cursor.close();
                }
                sqlite.Close();
                ShowSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }

    private void ShowSubject() {
        GridView grid = findViewById(R.id.gridViewSubjectMark);
        GridAdapterMarkStudent adapter = new GridAdapterMarkStudent(this, 0, lResult);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view1, position, id) -> {
            ShowItem(position);
        });
    }

    private void ShowItem(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_mark_student, null);
        dialogBuilder.setView(dialogView);
        //chuan bi cho popup
        final TextView txtName = dialogView.findViewById(R.id.txtName);
        final EditText txtScore1 = dialogView.findViewById(R.id.txtScore1);
        final EditText txtScore2 = dialogView.findViewById(R.id.txtScore2);
        final EditText txtFinalScore = dialogView.findViewById(R.id.txtFinalScore);

        dialogBuilder.setTitle("Kết quả học tập");
        txtName.setText(lResult.get(position).getNameStudent());
        txtScore1.setText(lResult.get(position).getScore1() + "");
        txtScore2.setText(lResult.get(position).getScore2() + "");
        txtFinalScore.setText(lResult.get(position).getFinalScore() + "");

        dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
            float score1 = Float.parseFloat(txtScore1.getText().toString());
            float score2 = Float.parseFloat(txtScore2.getText().toString());
            float finalScore = Float.parseFloat(txtFinalScore.getText().toString());

            lResult.get(position).setNameStudent(txtName.getText().toString());
            lResult.get(position).setScore1(score1);
            lResult.get(position).setScore2(score2);
            lResult.get(position).setFinalScore(finalScore);
            if(score1 >= 6 && score2 >= 6 && finalScore >= 6) lResult.get(position).setrStatement(1);
            else lResult.get(position).setrStatement(0);

            sqlite.Open();
            String updateQuery = "UPDATE Result SET score1 = " + score1 + ", score2 = " + score2 +
                    ", finalScore = " + finalScore + ", rStatement = " + lResult.get(position).getrStatement() +
                    " where idResult = " + lResult.get(position).getIdResult();
            sqlite.SQLRun(updateQuery);
            sqlite.Close();
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}