package vn.itplus.doanqlsinhvien.View.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Control.SubSystem.GridAdapterSystem;
import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.Model.subSystem;
import vn.itplus.doanqlsinhvien.R;

public class MainTeacher extends AppCompatActivity {
    private ArrayList<subSystem> lSubSystem = new ArrayList<>();
    private SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("idAccount", -1);

        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("select idTeacher from Teacher_Account where idAccount = " + id);
        cursor.moveToFirst();
        sqlite.Close();
        UpdateSubSystem();
        EditGrid(lSubSystem, cursor.getInt(0));
    }

    private void EditGrid(ArrayList<subSystem> lSubSystem, int idTeacher) {
        GridView grid = findViewById(R.id.gridViewTeacher);
        GridAdapterSystem adapter = new GridAdapterSystem(this, R.layout.gird_item, lSubSystem);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 1: {
                    Intent intent = new Intent(MainTeacher.this, CalenderTeacher.class);
                    intent.putExtra("idTeacher", idTeacher);
                    startActivity(intent);
                    break;
                }

                case 2: {
                    Intent intent = new Intent(MainTeacher.this, MarkTeacher.class);
                    intent.putExtra("idTeacher", idTeacher);
                    startActivity(intent);
                    break;
                }

                default:{
                    Toast.makeText(this, "Chưa cập nhật menu này.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdateSubSystem() {
        lSubSystem.add(new subSystem("TIN TỨC", R.drawable.news));
        lSubSystem.add(new subSystem("LỊCH DẠY", R.drawable.calendar));
        lSubSystem.add(new subSystem("BẢNG ĐIỂM", R.drawable.score));
        lSubSystem.add(new subSystem("DANH SÁCH LỚP", R.drawable.join_class));
    }
}