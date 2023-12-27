package com.example.doanqlsinhvien.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String databaseName = "QLSinhVien.sqlite";
    private static final int version = 1;

    //Tao cau lenh
    private String sqlCreateTableAccount = "CREATE TABLE Account\n" +
            "(\n" +
            "  idAccount INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  userName varchar(30),\n" +
            "  password varchar(20),\n" +
            "  aStatement INTEGER\n" +
            ");";

    private String sqlCreateTableStudent = "CREATE TABLE Student\n" +
            "(\n" +
            "  idStudent INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  name varchar(50),\n" +
            "  phone varchar(11),\n" +
            "  email varchar(50),\n" +
            "  dob date,\n" +
            " sStatement INTEGER\n" +
            ");";

    private String sqlCreateTableTeacher = "CREATE TABLE Teacher\n" +
            "(\n" +
            "  idTeacher INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  name varchar(50),\n" +
            "  phone varchar(11),\n" +
            "  email varchar(50),\n" +
            "  tStatement INTEGER\n" +
            ");";

    private String sqlCreateTableSubject = "CREATE TABLE Subject\n" +
            "(\n" +
            "  idSubject INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  nameSubject varchar(50),\n" +
            "  nameClass varchar(50),\n" +
            "  timeStart date,\n" +
            "  timeEnd date\n" +
            ");";

    private String sqlCreateTableClassroom = "CREATE TABLE Classroom\n" +
            "(\n" +
            "  idClassroom INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  name varchar(30),\n" +
            "  address varchar(50)\n" +
            ");";

    private String sqlTeacher_Account = "create table Teacher_Account(" +
            " idTeacher INTEGER," +
            " idAccount INTEGER," +
            " PRIMARY KEY (idTeacher, idAccount)," +
            " FOREIGN KEY (idTeacher) REFERENCES Teacher(idTeacher)," +
            " FOREIGN KEY (idAccount) REFERENCES Account(idAccount));";

    private String sqlStudent_Account = "CREATE TABLE Student_Account(" +
            "  idStudent INTEGER," +
            "  idAccount INTEGER," +
            "  PRIMARY KEY (idStudent, idAccount)," +
            "  FOREIGN KEY (idStudent) REFERENCES Student(idStudent)," +
            "  FOREIGN KEY (idAccount) REFERENCES Account(idAccount));";

    private String sqlCreateTableResult = "CREATE TABLE Result\n" +
            "(\n" +
            " idResult INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " score1 FLOAT,\n" +
            " score2 FLOAT,\n" +
            " finalScore FLOAT,\n" +
            " rStatement INTEGER,\n" +
            " idSubject INTEGER,\n" +
            " idStudent INTEGER,\n" +
            " FOREIGN KEY (idSubject) REFERENCES Subject(idSubject),\n" +
            " FOREIGN KEY (idStudent) REFERENCES Student(idStudent)\n" +
            ");";

    private String sqlCreateTableTeach = "CREATE TABLE Teach\n" +
            "(\n" +
            " idTeacher INTEGER,\n" +
            " idSubject INTEGER,\n" +
            " PRIMARY KEY (idTeacher, idSubject),\n" +
            " FOREIGN KEY (idTeacher) REFERENCES Teacher(idTeacher),\n" +
            " FOREIGN KEY (idSubject) REFERENCES Subject(idSubject)\n" +
            ");";

    private String sqlCreateTableLearn = "CREATE TABLE Learn\n" +
            "(\n" +
            " idSubject INTEGER,\n" +
            " idStudent INTEGER,\n" +
            " statement INTEGER,\n" +
            " PRIMARY KEY (idSubject, idStudent),\n" +
            " FOREIGN KEY (idSubject) REFERENCES Subject(idSubject),\n" +
            " FOREIGN KEY (idStudent) REFERENCES Student(idStudent)\n" +
            ");";

    private String sqlCreateTableClassRoom_Detail = "CREATE TABLE Classroom_Detail\n" +
            "(\n" +
            " idClassroom INTEGER,\n" +
            " idSubject INTEGER,\n" +
            " timeShift INTEGER,\n" +
            " dateLearn varchar(10),\n" +
            " PRIMARY KEY (idClassroom, idSubject),\n" +
            " FOREIGN KEY (idClassroom) REFERENCES Classroom(idClassroom),\n" +
            " FOREIGN KEY (idSubject) REFERENCES Subject(idSubject)\n" +
            ");";

    public SQLiteHelper(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableAccount);
        db.execSQL(sqlCreateTableStudent);
        db.execSQL(sqlCreateTableTeacher);
        db.execSQL(sqlCreateTableClassroom);
        db.execSQL(sqlCreateTableSubject);
        db.execSQL(sqlStudent_Account);
        db.execSQL(sqlTeacher_Account);
        db.execSQL(sqlCreateTableResult);
        db.execSQL(sqlCreateTableTeach);
        db.execSQL(sqlCreateTableLearn);
        db.execSQL(sqlCreateTableClassRoom_Detail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will not destroy old data");
    }
}
