package vn.itplus.doanqlsinhvien.Model;

public class teacher {
    int id;
    String name, phone, email;
    int tStatement;

    public teacher(int id, String name, String phone, String email, int tStatement) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tStatement = tStatement;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int gettStatement() {
        return tStatement;
    }

    public void settStatement(int tStatement) {
        this.tStatement = tStatement;
    }
}
