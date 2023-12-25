package vn.itplus.doanqlsinhvien.Control.Teacher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Model.teacher;
import vn.itplus.doanqlsinhvien.R;

public class GridAdapterTeacher extends ArrayAdapter<teacher> {
    Context mContext;
    ArrayList<teacher> mGridData;
    public GridAdapterTeacher(@NonNull Context context, int resource, @NonNull ArrayList<teacher> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewTeacher(mContext);
        }

        ViewHolderTeacher holder = new ViewHolderTeacher();

        teacher item = mGridData.get(position);
        if(item != null) {
            holder.txtName = row.findViewById(R.id.txtTeacherName);
            holder.txtPhone = row.findViewById(R.id.txtTeacherPhone);
            holder.txtEmail = row.findViewById(R.id.txtTeacherEmail);
            holder.statement = row.findViewById(R.id.txtTeacherStatement);

            holder.txtName.setText(item.getName());
            holder.txtPhone.setText(item.getPhone());
            holder.txtEmail.setText(item.getEmail());
            if(item.gettStatement() == 1) holder.statement.setText("Đang công tác");
            else holder.statement.setText("Chưa kích hoạt");
        }
        notifyDataSetChanged();

        return row;
    }
}
