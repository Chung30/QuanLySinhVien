package com.example.doanqlsinhvien.View.Admin.FragmentAcc;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doanqlsinhvien.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.doanqlsinhvien.Data.SQLiteAdapter;
import com.example.doanqlsinhvien.Model.teacher;

import java.util.ArrayList;

import com.example.doanqlsinhvien.Control.Teacher.GridAdapterTeacher;



public class TeacherFragment extends Fragment {
    private ArrayList<teacher> teachers = new ArrayList<>();
    private SQLiteAdapter sqlite;
    private FloatingActionButton fabAddTeacher;
    private EditText txtSearch;
    private ImageButton btnSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlite = new SQLiteAdapter(requireContext());
        UpdateTeachers("");
    }

    private void UpdateTeachers(String s) {
        sqlite.Open();

        Cursor cursor;
        if(s.equals("")){
            cursor = sqlite.SQLTable("SELECT * FROM Teacher");
        }
        else{
            cursor = sqlite.SQLTable("SELECT * FROM Teacher where name LIKE '%" + s + "%'");
        }

        this.teachers.clear();
        while (cursor.moveToNext()) {
            try {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String email = cursor.getString(3);
                int statement = cursor.getInt(4);

                this.teachers.add(new teacher(id, name, email, phone, statement));
            } catch (Exception e) {
                Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        cursor.close();
        sqlite.Close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc_teacher, container, false);

        fabAddTeacher = view.findViewById(R.id.fabAdd_Teacher);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

        ShowTeachers(view);

        btnSearch.setOnClickListener(v -> {
            UpdateTeachers(txtSearch.getText().toString().trim());
            txtSearch.clearFocus();
            hideKeyboard(view);
            ShowTeachers(view);
        });

        fabAddTeacher.setOnClickListener(v -> showItemDialog(-1));

        return view;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void ShowTeachers(View view) {
        GridView grid = view.findViewById(R.id.grid_acc_teacher);
        GridAdapterTeacher adapter = new GridAdapterTeacher(view.getContext(), R.layout.gird_item, teachers);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view1, position, id) -> {
            Toast.makeText(getContext(), teachers.get(position).getName(), Toast.LENGTH_SHORT).show();
            showItemDialog(position);
        });
    }

    private void showItemDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_teacher, null);
        dialogBuilder.setView(dialogView);
        //chuan bi cho popup
        final EditText edtName = dialogView.findViewById(R.id.edtName);
        final EditText edtPhoneNumber = dialogView.findViewById(R.id.edtPhoneNumber);
        final EditText edtEmail = dialogView.findViewById(R.id.edtEmail);

        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(spinnerAdapter);

        if(position == -1){
            dialogBuilder.setTitle("Thêm giảng viên");
            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String phoneNumber = edtPhoneNumber.getText().toString();
                int statement;

                if(spinnerStatus.getSelectedItem().toString().equals("Kích hoạt")) statement = 1;
                else statement = 0;
                int id = teachers.size()+1;
                teachers.add(new teacher(id, name, phoneNumber, email, statement));
                sqlite.Open();
                //Them Teacher
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("phone", phoneNumber);
                values.put("email", email);
                values.put("tStatement", statement);
                //Them Account cua teacher
                ContentValues valuesTeacher = new ContentValues();
                valuesTeacher.put("userName", email);
                valuesTeacher.put("password", "123456");
                if(statement == 1) valuesTeacher.put("aStatement", 2);
                else valuesTeacher.put("aStatement", 0);
                //Them teacher_account
                Cursor cntId = sqlite.SQLTable("select count(*) from Account");
                cntId.moveToNext();
                int idAcc = cntId.getInt(0) +1;
                ContentValues valuesTeacherAcc = new ContentValues();
                valuesTeacherAcc.put("idTeacher", id);
                valuesTeacherAcc.put("idAccount", idAcc);

                try {
                    sqlite.Insert("Teacher", values);
                    sqlite.Insert("Account", valuesTeacher);
                    sqlite.Insert("Teacher_Account", valuesTeacherAcc);
                } catch (Exception e) {
                    // Xử lý lỗi
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    sqlite.Close();
                }
            });
        }
        else {
            dialogBuilder.setTitle("Thông tin giảng viên");
            edtName.setText(teachers.get(position).getName());
            edtPhoneNumber.setText(teachers.get(position).getPhone());
            edtEmail.setText(teachers.get(position).getEmail());

            sqlite.Open();
            Cursor cursor = sqlite.SQLTable("SELECT * FROM Teacher WHERE idTeacher = " + (position+1));
            cursor.moveToFirst();

            int teacherId = cursor.getInt(0);
            if(teachers.get(position).gettStatement() == 1) spinnerStatus.setSelection(1);
            else spinnerStatus.setSelection(0);

            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                teachers.get(position).setName(edtName.getText().toString());
                teachers.get(position).setPhone(edtPhoneNumber.getText().toString());
                teachers.get(position).setEmail(edtEmail.getText().toString());

                String selectedStatus = spinnerStatus.getSelectedItem().toString();
                int statement;

                if ("Chưa kích hoạt".equals(selectedStatus)) {
                    statement = 0; // "Chưa kích hoạt"
                } else {
                    statement = 1; // "Kích hoạt"
                }
                teachers.get(position).settStatement(statement);

                sqlite.Open();
                String updateQuery = "UPDATE Teacher SET name = '" + teachers.get(position).getName() +
                        "', phone = '" + teachers.get(position).getPhone() +
                        "', email = '" + teachers.get(position).getEmail() +
                        "', tStatement = " + teachers.get(position).gettStatement() +
                        " WHERE idTeacher = " + teacherId;
                sqlite.SQLRun(updateQuery);
                int aStatement = 0;
                if(statement == 1) aStatement = 2;
                sqlite.SQLRun("update Account set userName = '" + teachers.get(position).getEmail() +
                        "', aStatement = " + aStatement + " where " +
                        "idAccount in (select idAccount from Teacher_Account where idTeacher = " + teacherId + ")");
                sqlite.Close();
            });
        }

        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
