package com.example.doanqlsinhvien.View.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.doanqlsinhvien.Control.SubSystem.GridAdapterSystem;
import com.example.doanqlsinhvien.Model.subSystem;
import com.example.doanqlsinhvien.R;


public class MainAdmin extends AppCompatActivity {

    private final ArrayList<subSystem> lSubSystem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        UpdateSubSystem();
        EditGrid(lSubSystem);
    }

    private void EditGrid(ArrayList<subSystem> lSubSystem) {
        GridView grid = findViewById(R.id.gridViewStudent);
        GridAdapterSystem adapter = new GridAdapterSystem(this, R.layout.gird_item, lSubSystem);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 1: {
                    Intent intent = new Intent(MainAdmin.this, ViewPageAccount.class);
                    startActivity(intent);
                    break;
                }

                case 4: {
                    Intent intent = new Intent(MainAdmin.this, ViewSubject.class);
                    startActivity(intent);
                    break;
                }

                default:{
                    Toast.makeText(MainAdmin.this, "Chưa cập nhật menu này.", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void UpdateSubSystem() {
        lSubSystem.add(new subSystem("TIN TỨC", R.drawable.news));
        lSubSystem.add(new subSystem("TÀI KHOẢN", R.drawable.calendar));
        lSubSystem.add(new subSystem("HỌC VIÊN", R.drawable.student));
        lSubSystem.add(new subSystem("MÔN HỌC", R.drawable.calendar_exam));
        lSubSystem.add(new subSystem("LỚP HỌC", R.drawable.classimg));
    }
}