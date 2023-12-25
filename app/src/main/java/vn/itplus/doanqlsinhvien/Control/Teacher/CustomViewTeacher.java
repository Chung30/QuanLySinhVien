package vn.itplus.doanqlsinhvien.Control.Teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.itplus.doanqlsinhvien.R;

public class CustomViewTeacher extends LinearLayout {
    TextView txtName, txtPhone, txtEmail, statement;
    public CustomViewTeacher(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.inflate(R.layout.item_teacher, this, true);
        txtName = findViewById(R.id.txtTeacherName);
        txtPhone = findViewById(R.id.txtTeacherPhone);
        txtEmail = findViewById(R.id.txtTeacherEmail);
        statement = findViewById(R.id.txtTeacherStatement);
    }
}
