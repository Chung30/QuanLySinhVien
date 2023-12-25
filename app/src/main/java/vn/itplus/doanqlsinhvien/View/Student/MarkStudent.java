package vn.itplus.doanqlsinhvien.View.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.Control.MarkStudent.GridAdapterMarkStudent;
import vn.itplus.doanqlsinhvien.Model.result;
import vn.itplus.doanqlsinhvien.R;

public class MarkStudent extends AppCompatActivity {
    ArrayList<result> lResult = new ArrayList<>();
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("idStudent", -1);

        UpdateData(id);
        ShowData();
    }

    private void ShowData() {
        GridView grid = findViewById(R.id.gridViewSubjectMark);
        GridAdapterMarkStudent adapter = new GridAdapterMarkStudent(this, 0, lResult);
        grid.setAdapter(adapter);
    }

    private void UpdateData(int idStudent) {
        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("select idResult, score1, score2, finalScore, rStatement, nameSubject" +
                " from Result a join Subject b on a.idSubject = b.idSubject where idStudent = " + idStudent);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            float score1 = cursor.getFloat(1);
            float score2 = cursor.getFloat(2);
            float finalScore = cursor.getFloat(3);
            int statement = cursor.getInt(4);
            String nameSubject = cursor.getString(5);

            lResult.add(new result(idStudent, nameSubject, score1, score2, finalScore, statement));
        }
        sqlite.Close();
    }
}