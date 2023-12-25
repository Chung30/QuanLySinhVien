package vn.itplus.doanqlsinhvien.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.R;
import vn.itplus.doanqlsinhvien.View.Admin.MainAdmin;
import vn.itplus.doanqlsinhvien.View.Student.MainStudent;
import vn.itplus.doanqlsinhvien.View.Teacher.MainTeacher;

public class Login extends AppCompatActivity {
    EditText txtUserName, txtPassword;
    Button btnLogin;
    SQLiteAdapter sqlite = new SQLiteAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AddData();
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            String user = txtUserName.getText().toString();
            String pass = txtPassword.getText().toString();
            sqlite.Open();
            Cursor cursor = sqlite.SQLTable("select * from Account " +
                    "where userName = '" + user + "' and password = '" + pass + "'");
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                int id = cursor.getInt(0);
                int statement = cursor.getInt(3);
                if(statement == 0){
                    Toast.makeText(Login.this, "Tài khoản của bạn chưa được kích hoạt.", Toast.LENGTH_SHORT).show();
                } else{
                    if(statement == 1){
                        Intent intent = new Intent(Login.this, MainAdmin.class);
                        startActivity(intent);
                    }
                    else if(statement == 2){
                        Intent intent = new Intent(Login.this, MainTeacher.class);
                        intent.putExtra("idAccount", id);
                        startActivity(intent);
                    }
                    else if(statement == 3) {
                        Intent intent = new Intent(Login.this, MainStudent.class);
                        intent.putExtra("idAccount", id);
                        startActivity(intent);
                    }
                    sqlite.Close();
                    finish();
                }
            } else {
                Toast.makeText(Login.this, "Tài khoản chưa tồn tại.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddData() {
        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("select count(*) from Account");
        cursor.moveToFirst();
        if(cursor.getInt(0) == 0){
            sqlite.SQLRun("insert into Account(userName, Password, aStatement) values('admin1', '123456', 1)");
        }

        Cursor rooms = sqlite.SQLTable("select count(*) from Classroom");
        rooms.moveToFirst();
        if(rooms.getInt(0) == 0){
            sqlite.SQLRun("insert into Classroom(name, address) values('802', 'Phòng 2 tầng 8')");
            sqlite.SQLRun("insert into Classroom(name, address) values('803', 'Phòng 3 tầng 8')");
            sqlite.SQLRun("insert into Classroom(name, address) values('804', 'Phòng 4 tầng 8')");
            sqlite.SQLRun("insert into Classroom(name, address) values('805', 'Phòng 5 tầng 8')");
            sqlite.SQLRun("insert into Classroom(name, address) values('806', 'Phòng 6 tầng 8')");
            sqlite.SQLRun("insert into Classroom(name, address) values('807', 'Phòng 7 tầng 8')");
            sqlite.SQLRun("insert into Classroom(name, address) values('808', 'Phòng 8 tầng 8')");
        }
        sqlite.Close();
    }
}