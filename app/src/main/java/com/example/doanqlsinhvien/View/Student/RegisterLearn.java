package com.example.doanqlsinhvien.View.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doanqlsinhvien.Data.SQLiteAdapter;

import java.util.ArrayList;

import com.example.doanqlsinhvien.Control.RegisterSubject.GridAdapterRegisterSubject;
import com.example.doanqlsinhvien.Model.registerSubject;
import com.example.doanqlsinhvien.R;


public class RegisterLearn extends AppCompatActivity {
    Spinner spinnerRegisterLearn;
    Button btnRegisterSubject;
    ArrayList<registerSubject> lSubject = new ArrayList<>();
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_learn);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("idStudent", -1);

        btnRegisterSubject = findViewById(R.id.btnRegisterSubject);
        spinnerRegisterLearn = findViewById(R.id.spinnerRegisterLearn);
        String[] statusSubject = {"Chọn môn học", "C/C++", "JAVA", "PYTHON", "ANDROID", "PHOTOSHOP"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusSubject);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegisterLearn.setAdapter(adapter);
        spinnerRegisterLearn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn
                if(position == 0) lSubject.clear();
                else{
                    lSubject.clear();
                    sqlite.Open();
                    Cursor cursor = sqlite.SQLTable("SELECT * FROM Subject" +
                            " WHERE nameSubject = '" + statusSubject[position] + "'");
                    while (cursor.moveToNext()) {
                        int idSub = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String nameClass = cursor.getString(2);
                        String timeStart = cursor.getString(3);
                        String timeEnd = cursor.getString(4);
                        boolean rbCheck = false;
                        Cursor brCheck = sqlite.SQLTable("select count(*) from Learn where idSubject = " + idSub);
                        brCheck.moveToFirst();
                        if(brCheck.getInt(0) > 0) rbCheck = true;
                        lSubject.add(new registerSubject(idSub, name, nameClass, timeStart, timeEnd, rbCheck));
                        brCheck.close();
                    }
                    cursor.close();
                    sqlite.Close();
                }

                ShowSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        btnRegisterSubject.setOnClickListener(v -> {
            sqlite.Open();
            int brPosition = -1;
            for(int i=0;i<lSubject.size();i++){
                if(lSubject.get(i).isRbCheck() == true) {
                    brPosition = i;
                    break;
                }
            }

            if(brPosition != -1){
                int idSubject = lSubject.get(brPosition).getId();
                Cursor learn = sqlite.SQLTable("select count(*) from Learn a join Subject b on a.idSubject = b.idSubject" +
                        " where nameSubject = '" + lSubject.get(brPosition).getName() + "' and idStudent = " + id);
                learn.moveToFirst();
                //Khi chua co trong lsubject
                if(learn.getInt(0) == 0){
                    ContentValues values = new ContentValues();
                    values.put("idStudent", id);
                    values.put("idSubject", idSubject);
                    sqlite.Insert("Learn", values);
                    Toast.makeText(RegisterLearn.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                    spinnerRegisterLearn.setSelection(0);
                }
                //Khi co trong lsubject
                else{
                    Cursor subject = sqlite.SQLTable("select a.idSubject, nameClass from Learn a join Subject b on a.idSubject = b.idSubject" +
                            " where idStudent = " + id + " and nameSubject = '" + lSubject.get(brPosition).getName() + "'");
                    subject.moveToFirst();
                    //Neu co ma khac voi trong table Learn
                    if(!subject.getString(1).equals(lSubject.get(brPosition).getNameClass())){
                        sqlite.SQLRun("update Learn set idSubject = " + idSubject + " where idStudent = " + id + "" +
                                " and idSubject = " + subject.getInt(0));
                    }
                    Toast.makeText(RegisterLearn.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                    spinnerRegisterLearn.setSelection(0);
                    subject.close();
                }
            }
            else{
                Toast.makeText(RegisterLearn.this, "Bạn chưa đăng ký.", Toast.LENGTH_SHORT).show();
            }
        });
        sqlite.Close();
    }

    private void ShowSubject() {
        GridView grid = findViewById(R.id.gridViewRegisterLearn);
        GridAdapterRegisterSubject adapter = new GridAdapterRegisterSubject(this, R.layout.item_student_subject, lSubject);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view1, position, id) -> {
            if (adapter.getSelectedPosition() != -1) {
                lSubject.get(adapter.getSelectedPosition()).setRbCheck(false);
                adapter.setSelectedPosition(-1);
                adapter.notifyDataSetChanged();
            }
        });
    }
}