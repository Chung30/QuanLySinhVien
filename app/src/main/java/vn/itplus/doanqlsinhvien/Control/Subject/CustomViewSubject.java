package vn.itplus.doanqlsinhvien.Control.Subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.itplus.doanqlsinhvien.R;

public class CustomViewSubject extends LinearLayout {
    TextView txtName, txtNameClass, txtTimeStart, txtTimeEnd, txtClass;

    public CustomViewSubject(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.inflate(R.layout.item_admin_subject, this, true);
        txtName = findViewById(R.id.txtSubName);
        txtNameClass = findViewById(R.id.txtSubNameClass);
        txtTimeStart = findViewById(R.id.txtSubTimeStart);
        txtTimeEnd = findViewById(R.id.txtSubTimeEnd);
        txtClass = findViewById(R.id.txtSubClassroom);
    }
}
