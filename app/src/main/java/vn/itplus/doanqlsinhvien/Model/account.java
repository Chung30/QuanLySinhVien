package vn.itplus.doanqlsinhvien.Model;

public class account {
    private String userName, password;
    private int aStatement;

    public account(String userName, String password, int aStatement) {
        this.userName = userName;
        this.password = password;
        this.aStatement = aStatement;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getaStatement() {
        return aStatement;
    }

    public void setaStatement(int aStatement) {
        this.aStatement = aStatement;
    }
}
