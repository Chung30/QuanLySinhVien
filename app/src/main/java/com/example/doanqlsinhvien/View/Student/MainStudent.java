package com.example.doanqlsinhvien.View.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.doanqlsinhvien.Data.SQLiteAdapter;

import java.util.ArrayList;

import com.example.doanqlsinhvien.Control.SubSystem.GridAdapterSystem;
import com.example.doanqlsinhvien.Model.subSystem;
import com.example.doanqlsinhvien.R;


public class MainStudent extends AppCompatActivity {
    private ArrayList<subSystem> lSubSystem = new ArrayList<>();
    private SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("idAccount", -1);

        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("select idStudent from Student_Account where idAccount = " + id);
        cursor.moveToFirst();
        sqlite.Close();
        UpdateData();
        EditGrid(lSubSystem, cursor.getInt(0));
    }

    private void EditGrid(ArrayList<subSystem> lSubSystem, int idStudent) {
        GridView grid = findViewById(R.id.gridViewStudent);
        GridAdapterSystem adapter = new GridAdapterSystem(this, R.layout.gird_item, lSubSystem);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 1: {
                    Intent intent = new Intent(MainStudent.this, CalenderStudent.class);
                    intent.putExtra("idStudent", idStudent);
                    startActivity(intent);
                    break;
                }

                case 2: {
                    Intent intent = new Intent(MainStudent.this, MarkStudent.class);
                    intent.putExtra("idStudent", idStudent);
                    startActivity(intent);
                    break;
                }

                case 4: {
                    Intent intent = new Intent(MainStudent.this, RegisterLearn.class);
                    intent.putExtra("idStudent", idStudent);
                    startActivity(intent);
                    break;
                }

                default:{
                    Toast.makeText(this, "Chưa cập nhật menu này.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdateData() {
        lSubSystem.add(new subSystem("TIN TỨC", R.drawable.news));
        lSubSystem.add(new subSystem("LỊCH HỌC", R.drawable.calendar));
        lSubSystem.add(new subSystem("BẢNG ĐIỂM", R.drawable.score));
        lSubSystem.add(new subSystem("LỊCH THI", R.drawable.calendar_exam));
        lSubSystem.add(new subSystem("ĐĂNG KÝ MÔN HỌC", R.drawable.join_class));
    }
}