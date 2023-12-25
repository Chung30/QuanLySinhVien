package vn.itplus.doanqlsinhvien.Control.RegisterSubject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import vn.itplus.doanqlsinhvien.R;

public class CustomViewRegisterSubject extends LinearLayout {
    TextView txtName, txtNameClass, txtTimeStart, txtTimeEnd, txtClass;
    RadioButton rbRegister;
    public CustomViewRegisterSubject(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout.inflate(R.layout.item_student_subject, this, true);
        txtName = findViewById(R.id.txtSubName);
        txtNameClass = findViewById(R.id.txtSubNameClass);
        txtTimeStart = findViewById(R.id.txtSubTimeStart);
        txtTimeEnd = findViewById(R.id.txtSubTimeEnd);
        txtClass = findViewById(R.id.txtSubClassroom);
        rbRegister = findViewById(R.id.rbRegister);
    }
}
