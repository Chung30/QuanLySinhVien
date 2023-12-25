package vn.itplus.doanqlsinhvien.View.Admin.FragmentAcc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Control.Student.GridAdapterStudent;
import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.Model.student;
import vn.itplus.doanqlsinhvien.R;

public class StudentFragment extends Fragment {
    private final ArrayList<student> students = new ArrayList<>();
    private SQLiteAdapter sqlite;
    private FloatingActionButton fabAddStudent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlite = new SQLiteAdapter(requireContext());
        UpdateStudents();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc_student, container, false);

        fabAddStudent = view.findViewById(R.id.fabAdd_Student);
        ShowStudents(view);

        fabAddStudent.setOnClickListener(v -> showItemDialog(-1));

        return view;
    }

    private void ShowStudents(View view) {
        GridView grid = view.findViewById(R.id.grid_acc_student);
        GridAdapterStudent adapter = new GridAdapterStudent(view.getContext(), R.layout.gird_item, students);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view1, position, id) -> showItemDialog(position));
    }

    private void UpdateStudents() {
        sqlite.Open();
        Cursor students = sqlite.SQLTable("SELECT * FROM Student");

        while (students.moveToNext()) {
            try {
                int idStudent = students.getInt(0);
                String name = students.getString(1);
                String dob = students.getString(2);
                String email = students.getString(3);
                String phone = students.getString(4);
                int statement = students.getInt(5);

                this.students.add(new student(idStudent, name, email, phone, dob, statement));
            } catch (Exception e) {
                Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        students.close();
        sqlite.Close();
    }
    private void showItemDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_student, null);
        dialogBuilder.setView(dialogView);
        //chuan bi cho popup
        final EditText edtName = dialogView.findViewById(R.id.edtName);
        final EditText edtDoB = dialogView.findViewById(R.id.edtDOB);
        final EditText edtPhoneNumber = dialogView.findViewById(R.id.edtPhoneNumber);
        final EditText edtEmail = dialogView.findViewById(R.id.edtEmail);

        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(spinnerAdapter);

        edtDoB.setFocusable(false);
        edtDoB.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, monthOfYear, dayOfMonth) -> edtDoB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);

            datePickerDialog.show();
        });


        if(position == -1){
            dialogBuilder.setTitle("Thêm học viên");
            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String dob = edtDoB.getText().toString();
                String phoneNumber = edtPhoneNumber.getText().toString();
                int statement;

                if(spinnerStatus.getSelectedItem().toString().equals("Kích hoạt")) statement = 1;
                else statement = 0;

                int id = students.size()+1;
                students.add(new student(id, name, email, phoneNumber, dob, statement));
                sqlite.Open();

                //Them student
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("phone", phoneNumber);
                values.put("email", email);
                values.put("dob", dob);
                values.put("sStatement", statement);
                //Them Account
                ContentValues valueAcc = new ContentValues();
                valueAcc.put("userName", email);
                valueAcc.put("password", "123456");
                if(statement == 1) valueAcc.put("aStatement", 3);
                else valueAcc.put("aStatement", 0);
                //Them student_account
                ContentValues valuesStudentAcc = new ContentValues();
                valuesStudentAcc.put("idStudent", id);
                Cursor idAcc = sqlite.SQLTable("select count(*) from Account");
                idAcc.moveToFirst();
                valuesStudentAcc.put("idAccount", idAcc.getInt(0)+1);
                try {
                    sqlite.Insert("Student", values);
                    sqlite.Insert("Account", valueAcc);
                    sqlite.Insert("Student_Account", valuesStudentAcc);
                } catch (Exception e) {
                    // Xử lý lỗi
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    sqlite.Close();
                }
            });
        }
        else {
            dialogBuilder.setTitle("Thông tin học viên");
            edtName.setText(students.get(position).getName());
            edtDoB.setText(students.get(position).getDob());
            edtPhoneNumber.setText(students.get(position).getPhone());
            edtEmail.setText(students.get(position).getEmail());

            sqlite.Open();
            Cursor cursor = sqlite.SQLTable("SELECT * FROM Student WHERE idStudent = " + (position + 1));
            cursor.moveToFirst();

            int studentId = cursor.getInt(0);
            if(students.get(position).getStatement() == 1) spinnerStatus.setSelection(1);
            else spinnerStatus.setSelection(0);

            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                students.get(position).setName(edtName.getText().toString());
                students.get(position).setDob(edtDoB.getText().toString());
                students.get(position).setPhone(edtPhoneNumber.getText().toString());
                students.get(position).setEmail(edtEmail.getText().toString());

                String selectedStatus = spinnerStatus.getSelectedItem().toString();
                int statement;

                if ("Chưa kích hoạt".equals(selectedStatus)) {
                    statement = 0; // "Chưa kích hoạt"
                } else {
                    statement = 1; // "Kích hoạt"
                }
                students.get(position).setStatement(statement);


                sqlite.Open();
                String updateQuery = "UPDATE Student SET name = '" + students.get(position).getName() +
                        "', phone = '" + students.get(position).getPhone() +
                        "', email = '" + students.get(position).getEmail() +
                        "', dob = '" + students.get(position).getDob() + "', sStatement = " + statement +
                        " WHERE idStudent = " + studentId;
                sqlite.SQLRun(updateQuery);
                int aStatement = 0;
                if(statement == 1) aStatement = 3;
                sqlite.SQLRun("update Account set userName = '" + students.get(position).getEmail() +
                        "', aStatement = " + aStatement + " where " +
                        "idAccount in (select idAccount from Student_Account where idStudent = " + studentId + ")");
                sqlite.Close();
            });
        }

        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
