package vn.itplus.doanqlsinhvien.Control.Account;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Model.account;
import vn.itplus.doanqlsinhvien.R;

public class GridAdapterAcc extends ArrayAdapter<account> {
    Context mContext;
    ArrayList<account> mGridData = new ArrayList<>();
    int resource;
    public GridAdapterAcc(@NonNull Context context, int resource, @NonNull ArrayList<account> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewAcc(mContext);
        }

        ViewHolderAcc holder = new ViewHolderAcc();

        account item = mGridData.get(position);
        if (row != null && item != null) {
            holder.txtUserName = (TextView) row.findViewById(R.id.txtUserNameAdmin);
            holder.txtPassword = (TextView) row.findViewById(R.id.txtPasswordAdmin);

            if (holder.txtUserName != null) {
                holder.txtUserName.setText("Username: " + item.getUserName());
            }

            if (holder.txtPassword != null) {
                holder.txtPassword.setText("Password: " + item.getPassword());
            }
        }
        notifyDataSetChanged();

        return row;
    }
}
