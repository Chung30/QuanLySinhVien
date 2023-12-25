package vn.itplus.doanqlsinhvien.View.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Control.Subject.GridAdapterSubject;
import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.Model.subject;
import vn.itplus.doanqlsinhvien.R;

public class CalenderStudent extends AppCompatActivity {
    ArrayList<subject> lSubject = new ArrayList<>();
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_student);
        Intent intent = getIntent();
        int id = intent.getIntExtra("idStudent", -1);
        UpdateData(id);
        ShowData();
    }

    private void ShowData() {
        GridView grid = findViewById(R.id.gridViewCalanderStudent);
        GridAdapterSubject adapter = new GridAdapterSubject(this, R.layout.item_student_subject, lSubject);
        grid.setAdapter(adapter);
    }

    private void UpdateData(int idStudent) {
        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("select b.idSubject, nameSubject, nameClass, timeStart, timeEnd" +
                " from Learn a join Subject b on a.idSubject = b.idSubject where a.idStudent = " + idStudent);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String nameClass = cursor.getString(2);
            String timeStart = cursor.getString(3);
            String timeEnd = cursor.getString(4);

            lSubject.add(new subject(id, name, nameClass, timeStart, timeEnd));
        }
    }
}