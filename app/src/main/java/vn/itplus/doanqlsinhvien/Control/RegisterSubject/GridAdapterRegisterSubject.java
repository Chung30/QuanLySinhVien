package vn.itplus.doanqlsinhvien.Control.RegisterSubject;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Control.Subject.CustomViewSubject;
import vn.itplus.doanqlsinhvien.Control.Subject.ViewHolderSubject;
import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.Model.registerSubject;
import vn.itplus.doanqlsinhvien.Model.subject;
import vn.itplus.doanqlsinhvien.R;

public class GridAdapterRegisterSubject extends ArrayAdapter<registerSubject> {
    Context mContext;
    ArrayList<registerSubject> mGridData;
    SQLiteAdapter sqlite;
    private int selectedPosition = -1;
    public GridAdapterRegisterSubject(@NonNull Context context, int resource, @NonNull ArrayList<registerSubject> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
        sqlite = new SQLiteAdapter(mContext);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewRegisterSubject(mContext);
        }

        ViewHolderRegisterSubject holder = new ViewHolderRegisterSubject();

        registerSubject item = mGridData.get(position);
        if(item != null) {
            holder.txtSubName = row.findViewById(R.id.txtSubName);
            holder.txtSubNameClass = row.findViewById(R.id.txtSubNameClass);
            holder.txtTimeStart = row.findViewById(R.id.txtSubTimeStart);
            holder.txtTimeEnd = row.findViewById(R.id.txtSubTimeEnd);
            holder.txtClassroom = row.findViewById(R.id.txtSubClassroom);
            holder.rbRegister = row.findViewById(R.id.rbRegister);

            holder.txtSubName.setText("Môn: " + item.getName());
            holder.txtSubNameClass.setText("Lớp: " + item.getNameClass());
            holder.txtTimeStart.setText("Bắt đầu: " + item.getTimeStart());
            holder.txtTimeEnd.setText("Kết thúc: " + item.getTimeEnd());

            holder.rbRegister.setOnClickListener(view -> {
                if (position != selectedPosition) {
                    // Bỏ chọn RadioButton tại vị trí cũ
                    if (selectedPosition != -1) {
                        mGridData.get(selectedPosition).setRbCheck(false);
                    }

                    // Chọn RadioButton tại vị trí mới
                    selectedPosition = position;
                    mGridData.get(selectedPosition).setRbCheck(true);

                    // Thông báo cập nhật dữ liệu
                    notifyDataSetChanged();
                }
            });

            // Đảm bảo rằng RadioButton chỉ có thể chọn một lần
//            holder.rbRegister.setEnabled(!item.isRbCheck());

            // Set trạng thái của RadioButton
            holder.rbRegister.setChecked(item.isRbCheck());

            sqlite.Open();
            Cursor id = sqlite.SQLTable("select idSubject from Subject where nameClass = '" + item.getNameClass() + "'");
            id.moveToLast();
            int idSub = id.getInt(0);
            id.close();
            Cursor cursor = sqlite.SQLTable("select a.name, timeShift, dateLearn from Classroom a join Classroom_Detail b on " +
                    "a.idClassroom = b.idClassroom where idSubject = " + idSub);
            cursor.moveToLast();

            String date = cursor.getString(2), date2 = "";
            for (int i = 0; i < date.length(); i++) {
                if (date.charAt(i) == '2') date2 += "2,";
                else if (date.charAt(i) == '3') date2 += "3,";
                else if (date.charAt(i) == '4') date2 += "4,";
                else if (date.charAt(i) == '5') date2 += "5,";
                else if (date.charAt(i) == '6') date2 += "6,";
                else if (date.charAt(i) == '7') date2 += "7,";
            }

            date2 = date2.substring(0, date2.length() - 1);
            holder.txtClassroom.setText("Phòng: " + cursor.getString(0) + " ca " + cursor.getInt(1) + " thứ " + date2);
            cursor.close();
            sqlite.Close();
        }
        return row;
    }
}
