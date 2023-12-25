package vn.itplus.doanqlsinhvien.Control.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.itplus.doanqlsinhvien.R;

public class CustomViewAcc extends LinearLayout {
    TextView txtUserName, txtPassword;
    public CustomViewAcc(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.inflate(R.layout.item_admin, this, true);
        txtUserName = findViewById(R.id.txtUserNameAdmin);
        txtPassword = findViewById(R.id.txtPasswordAdmin);
    }
}
