package vn.itplus.doanqlsinhvien.Model;

public class student {
    int id;
    String name, email, phone, dob;
    int statement;

    public student(int id, String name, String email, String phone, String dob, int statement) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.statement = statement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getStatement() {
        return statement;
    }

    public void setStatement(int statement) {
        this.statement = statement;
    }
}
