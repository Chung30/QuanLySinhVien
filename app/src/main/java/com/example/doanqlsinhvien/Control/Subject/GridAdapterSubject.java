package com.example.doanqlsinhvien.Control.Subject;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanqlsinhvien.Data.SQLiteAdapter;
import com.example.doanqlsinhvien.Model.subject;
import com.example.doanqlsinhvien.R;

import java.util.ArrayList;



public class GridAdapterSubject extends ArrayAdapter {
    Context mContext;
    ArrayList<subject> mGridData;
    SQLiteAdapter sqlite;

    public GridAdapterSubject(@NonNull Context context, int resource, @NonNull ArrayList<subject> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
        this.sqlite = new SQLiteAdapter(mContext); // Khởi tạo SQLiteAdapter
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewSubject(mContext);
        }

        ViewHolderSubject holder = new ViewHolderSubject();

        subject item = mGridData.get(position);
        if(item != null) {
            holder.txtSubName = row.findViewById(R.id.txtSubName);
            holder.txtSubNameClass  =row.findViewById(R.id.txtSubNameClass);
            holder.txtTimeStart = row.findViewById(R.id.txtSubTimeStart);
            holder.txtTimeEnd = row.findViewById(R.id.txtSubTimeEnd);
            holder.txtClassroom = row.findViewById(R.id.txtSubClassroom);

            holder.txtSubName.setText("Môn: " + item.getName());
            holder.txtSubNameClass.setText("Lớp: " + item.getNameClass());
            holder.txtTimeStart.setText("Bắt đầu: " + item.getTimeStart());
            holder.txtTimeEnd.setText("Kết thúc: " + item.getTimeEnd());

            sqlite.Open();
            Cursor id = sqlite.SQLTable("select idSubject from Subject where nameClass = '" + item.getNameClass() + "'");
            id.moveToLast();
            int idSub = id.getInt(0);
            System.out.println("-------------------------" + idSub);
            id.close();
            Cursor cursor = sqlite.SQLTable("select a.name, timeShift, dateLearn from Classroom a join Classroom_Detail b on " +
                    "a.idClassroom = b.idClassroom where idSubject = " + idSub);
            cursor.moveToFirst();

            String date = cursor.getString(2), date2 = "";
            for(int i=0;i<date.length();i++){
                if(date.charAt(i)=='2') date2 += "2,";
                else if (date.charAt(i)=='3') date2 += "3,";
                else if (date.charAt(i)=='4') date2 += "4,";
                else if (date.charAt(i)=='5') date2 += "5,";
                else if (date.charAt(i)=='6') date2 += "6,";
                else if (date.charAt(i)=='7') date2 += "7,";
            }

            date2 = date2.substring(0, date2.length() - 1);
            holder.txtClassroom.setText("Phòng: " + cursor.getString(0) + " ca " + cursor.getInt(1) + " thứ " + date2);
            cursor.close();
            sqlite.Close();
        }
        notifyDataSetChanged();

        return row;
    }
}
