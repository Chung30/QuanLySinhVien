package vn.itplus.doanqlsinhvien.Model;

public class result {
    int idResult;
    String nameStudent;
    float score1, score2, finalScore;
    int rStatement;

    public result(int idResult, String nameStudent, float score1, float score2, float finalScore, int rStatement) {
        this.idResult = idResult;
        this.nameStudent = nameStudent;
        this.score1 = score1;
        this.score2 = score2;
        this.finalScore = finalScore;
        this.rStatement = rStatement;
    }

    public int getIdResult() {
        return idResult;
    }

    public void setIdResult(int idResult) {
        this.idResult = idResult;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public float getScore1() {
        return score1;
    }

    public void setScore1(float score1) {
        this.score1 = score1;
    }

    public float getScore2() {
        return score2;
    }

    public void setScore2(float score2) {
        this.score2 = score2;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }

    public int getrStatement() {
        return rStatement;
    }

    public void setrStatement(int rStatement) {
        this.rStatement = rStatement;
    }
}
