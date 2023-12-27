package com.example.doanqlsinhvien.View.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import com.example.doanqlsinhvien.Control.Subject.GridAdapterSubject;
import com.example.doanqlsinhvien.Data.SQLiteAdapter;
import com.example.doanqlsinhvien.Model.subject;
import com.example.doanqlsinhvien.R;


public class CalenderTeacher extends AppCompatActivity {
    ArrayList<subject> lSubject = new ArrayList<>();
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_teacher);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("idTeacher", -1);

        UpdateData(id);
        ShowData();
    }

    private void ShowData() {
        GridView grid = findViewById(R.id.gridViewCalanderTeacher);
        GridAdapterSubject adapter = new GridAdapterSubject(this, R.layout.item_admin_subject, lSubject);
        grid.setAdapter(adapter);
    }

    private void UpdateData(int id) {
        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("select a.idSubject, a.nameSubject, nameClass, timeStart, timeEnd from Subject a " +
                "join Teach b on a.idSubject = b.idSubject where idTeacher = " + id);
        while(cursor.moveToNext()){
            int idSub = cursor.getInt(0);
            String name = cursor.getString(1);
            String nameClass = cursor.getString(2);
            String timeStart = cursor.getString(3);
            String timeEnd = cursor.getString(4);

            lSubject.add(new subject(idSub, name, nameClass, timeStart, timeEnd));
        }
        sqlite.Close();
    }
}