package com.example.doanqlsinhvien.View.Admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doanqlsinhvien.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.doanqlsinhvien.Data.SQLiteAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.doanqlsinhvien.Control.Subject.GridAdapterSubject;
import com.example.doanqlsinhvien.Model.subject;


public class ViewSubject extends AppCompatActivity {
    FloatingActionButton fab;
    ArrayList<subject> lSubject = new ArrayList<>();
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject);

        UpdateData();
//        UpdateData2();
        ShowSubject();

        fab = findViewById(R.id.fabAdd_admin_subject);
        fab.setOnClickListener(v -> showItemDialog(-1));
    }

    private void UpdateData2() {
        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("SELECT * FROM Subject");
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String nameClass = cursor.getString(2);
            String timeStart = cursor.getString(3);
            String timeEnd = cursor.getString(4);

            lSubject.add(new subject(id, name, nameClass, timeStart, timeEnd));
        }
        sqlite.Close();
    }

    private void ShowSubject() {
        GridView grid = findViewById(R.id.grid_admin_subject);
        GridAdapterSubject adapter = new GridAdapterSubject(this, R.id.grid_admin_subject, lSubject);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view1, position, id) -> {
            showItemDialog(position);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void UpdateData() {
        // Chuyển chuỗi thành đối tượng LocalDate
        List<String> possibleDateFormats = Arrays.asList("dd-MM-yyyy", "d-M-yyyy", "dd-M-yyyy", "d-MM-yyyy", "yyyy-MM-dd");

        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();
        sqlite.Open();
//        sqlite.SQLRun("delete from Subject");
        Cursor cursor = sqlite.SQLTable("SELECT * FROM Subject");
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String nameClass = cursor.getString(2);
            String timeStart = cursor.getString(3);
            String timeEnd = cursor.getString(4);

            LocalDate endDate = null;
            for (String dateFormat : possibleDateFormats) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                    endDate = LocalDate.parse(timeEnd, formatter);
                    break;
                } catch (Exception e) {

                }
            }

            if(currentDate.isAfter(endDate)){
                sqlite.SQLRun("delete from Subject where idSubject = " + id);
            }
            else{
                lSubject.add(new subject(id, name, nameClass, timeStart, timeEnd));
            }
        }

        sqlite.SQLRun("DELETE FROM Classroom_Detail WHERE idSubject NOT IN (SELECT DISTINCT idSubject FROM Subject)");
        sqlite.SQLRun("DELETE FROM Teach WHERE idSubject NOT IN (SELECT DISTINCT idSubject FROM Subject)");
        sqlite.SQLRun("DELETE FROM Learn WHERE idSubject NOT IN (SELECT DISTINCT idSubject FROM Subject)");
        sqlite.Close();
    }
    private void showItemDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_admin_subject, null);
        dialogBuilder.setView(dialogView);
        //chuan bi cho popup
        final EditText edtSubClass = dialogView.findViewById(R.id.edtSubClass);
        final EditText edtDateStart = dialogView.findViewById(R.id.edtDateStart);
        final EditText edtDateEnd = dialogView.findViewById(R.id.edtDateEnd);

        CheckBox checkBoxMonday = dialogView.findViewById(R.id.checkBoxMonday);
        CheckBox checkBoxTuesday = dialogView.findViewById(R.id.checkBoxTuesday);
        CheckBox checkBoxWednesday = dialogView.findViewById(R.id.checkBoxWednesday);
        CheckBox checkBoxThursday = dialogView.findViewById(R.id.checkBoxThursday);
        CheckBox checkBoxFriday = dialogView.findViewById(R.id.checkBoxFriday);
        CheckBox checkBoxSaturday = dialogView.findViewById(R.id.checkBoxSaturday);

        //spinner subject name
        final Spinner spinnerNameSubject = dialogView.findViewById(R.id.spinnerSubName);
        final String[] nameSubjectSelected = new String[1];
        String[] statusNameSubject = {"Chọn môn học", "C/C++", "JAVA", "PYTHON", "ANDROID", "PHOTOSHOP"};

        ArrayAdapter<String> adapterSubName = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusNameSubject);
        adapterSubName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNameSubject.setAdapter(adapterSubName);
        spinnerNameSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn
                nameSubjectSelected[0] = statusNameSubject[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        //spinner shift time
        final Spinner spinnerShift = dialogView.findViewById(R.id.spinnerShiftTime);
        final String[] shiftSelected = new String[1];
        String[] statusShift = {"1", "2", "3", "4", "5", "6"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusShift);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShift.setAdapter(adapter);
        spinnerShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn
                shiftSelected[0] = statusShift[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        //spinner room
        final Spinner spinnerRoom = dialogView.findViewById(R.id.spinnerClassroom);
        ArrayList<String> lRoom = new ArrayList<>();
        ArrayList<Integer> lRoomId = new ArrayList<>();
        sqlite.Open();
        Cursor rooms = sqlite.SQLTable("Select idClassroom, name from Classroom");
        while (rooms.moveToNext()){
            lRoomId.add(rooms.getInt(0));
            lRoom.add(rooms.getString(1));
        }
        rooms.close();
        sqlite.Close();
        final int[] idClassroom = new int[1];
        String[] statusRooms = lRoom.toArray(new String[0]);

        ArrayAdapter<String> adapterRoom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusRooms);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(adapterRoom);
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedStatus = statusRooms[position];
                // Xử lý khi một mục được chọn
                idClassroom[0] = lRoomId.get(position).intValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        //spinner teacher
        final Spinner spinnerTeacher = dialogView.findViewById(R.id.spinnerTeacher);
        ArrayList<Integer> lTeacherId = new ArrayList<>();
        ArrayList<String> lTeacherName = new ArrayList<>();
        sqlite.Open();
        Cursor teachers = sqlite.SQLTable("Select idTeacher, name from Teacher where tStatement = 1");
        while (teachers.moveToNext()){
            lTeacherId.add(teachers.getInt(0));
            lTeacherName.add(teachers.getString(1));
        }
        teachers.close();
        sqlite.Close();
        String[] statusTeacher = lTeacherName.toArray(new String[0]);
        final int[] idTeacher = new int[1];
        ArrayAdapter<String> adapterTeacher = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusTeacher);
        adapterTeacher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher.setAdapter(adapterTeacher);
        spinnerTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý khi một mục được chọn
                idTeacher[0] = lTeacherId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        edtDateStart.setFocusable(false);
        edtDateStart.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, monthOfYear, dayOfMonth) -> edtDateStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);

            datePickerDialog.show();
        });

        edtDateEnd.setFocusable(false);
        edtDateEnd.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, monthOfYear, dayOfMonth) -> edtDateEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);

            datePickerDialog.show();
        });

        // Đặt sự kiện cho các CheckBox

        List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(checkBoxMonday);
        checkBoxList.add(checkBoxTuesday);
        checkBoxList.add(checkBoxWednesday);
        checkBoxList.add(checkBoxThursday);
        checkBoxList.add(checkBoxFriday);
        checkBoxList.add(checkBoxSaturday);

        if(position == -1){
            dialogBuilder.setTitle("Thêm lớp học");
            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                String subName = spinnerNameSubject.getSelectedItem().toString();
                String subClass = edtSubClass.getText().toString();
                String subDateStart = edtDateStart.getText().toString();
                String subDateEnd = edtDateEnd.getText().toString();
                String shift = spinnerShift.getSelectedItem().toString();
                String dates = "";
                for (int i = 0; i < checkBoxList.size();i++) {
                    if(checkBoxList.get(i).isChecked()) dates += (i+2);
                }

                sqlite.Open();
                Cursor cursor = sqlite.SQLTable("select count(idSubject) from Subject");
                cursor.moveToFirst();
                int id = cursor.getInt(0)+1;
                lSubject.add(new subject(id, subName, subClass, subDateStart, subDateEnd));

                ContentValues valuesSubject = new ContentValues();
                valuesSubject.put("nameSubject", subName);
                valuesSubject.put("nameClass", subClass);
                valuesSubject.put("timeStart", subDateStart);
                valuesSubject.put("timeEnd", subDateEnd);

                ContentValues valuesClassDetail = new ContentValues();
                valuesClassDetail.put("idClassroom", idClassroom[0]);
                valuesClassDetail.put("idSubject", id);
                valuesClassDetail.put("timeShift", shift);
                valuesClassDetail.put("dateLearn", dates);

                ContentValues valuesTeach = new ContentValues();
                valuesTeach.put("idTeacher", idTeacher[0]);
                valuesTeach.put("idSubject", id);

                try {
                    sqlite.Insert("Subject", valuesSubject);
                    sqlite.Insert("Teach", valuesTeach);
                    sqlite.Insert("Classroom_Detail", valuesClassDetail);
                } catch (Exception e) {
                    // Xử lý lỗi
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    sqlite.Close();
                }
            });
        }
        else {
            dialogBuilder.setTitle("Thông tin lớp học");
            sqlite.Open();

            Cursor cursor = sqlite.SQLTable("SELECT idSubject from Subject WHERE nameClass = '" + lSubject.get(position).getNameClass() + "'");
            cursor.moveToLast();

            int idSub = cursor.getInt(0);
            cursor.close();
            //name Subject
            for(int i=0;i< statusNameSubject.length;i++){
                if(statusNameSubject[i].equals(lSubject.get(position).getName())){
                    spinnerNameSubject.setSelection(i);
                    break;
                }
            }

            //shift time
            Cursor shift = sqlite.SQLTable("select timeShift from Classroom_Detail where idSubject = " + idSub);
            shift.moveToFirst();
            for(int i=0;i< statusShift.length;i++){
                if(statusShift[i].equals(shift.getString(0))){
                    spinnerShift.setSelection(i);
                    break;
                }
            }
            shift.close();
            //teacher
            Cursor teacher = sqlite.SQLTable("select name from Teacher a join Teach b on a.idTeacher = b.idTeacher where idSubject = " + idSub);
            teacher.moveToFirst();
            for(int i=0;i< statusTeacher.length;i++){
                if(statusTeacher[i].equals(teacher.getString(0))){
                    spinnerTeacher.setSelection(i);
                    break;
                }
            }
            shift.close();

            //classroom
            Cursor classroom = sqlite.SQLTable("select name from Classroom a join Classroom_Detail b on a.idClassroom = b.idClassroom where idSubject = " + idSub);
            classroom.moveToFirst();
            for(int i=0;i< statusRooms.length;i++){
                if(statusRooms[i].equals(classroom.getString(0))){
                    spinnerRoom.setSelection(i);
                    break;
                }
            }
            shift.close();

            edtSubClass.setText(lSubject.get(position).getNameClass());
            edtDateStart.setText(lSubject.get(position).getTimeStart());
            edtDateEnd.setText(lSubject.get(position).getTimeEnd());

            sqlite.Close();

            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                String dates = "";
                for (int i = 0; i < checkBoxList.size();i++) {
                    if(checkBoxList.get(i).isChecked()) dates += (i+2);
                }

                lSubject.get(position).setName(nameSubjectSelected[0]);
                lSubject.get(position).setNameClass(edtSubClass.getText().toString());
                lSubject.get(position).setTimeStart(edtDateStart.getText().toString());
                lSubject.get(position).setTimeEnd(edtDateEnd.getText().toString());

                sqlite.Open();
                String updateQuery = "UPDATE Subject SET nameSubject = '" + lSubject.get(position).getName() +
                        "', nameClass = '" + lSubject.get(position).getNameClass() +
                        "', timeStart = '" + lSubject.get(position).getTimeStart() +
                        "', timeEnd = '" + lSubject.get(position).getTimeEnd() +
                        "' WHERE idSubject = " + idSub;
                sqlite.SQLRun(updateQuery);
                sqlite.SQLRun("update Teach Set idTeacher = " + idTeacher[0] + " where idSubject = " + idSub);
                sqlite.SQLRun("update Classroom_Detail set idClassroom = " + idClassroom[0] + ", timeShift = " + shiftSelected[0] +
                        ", dateLearn = '" + dates + "' where idSubject = " + idSub);
                sqlite.Close();

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            });
        }

        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}