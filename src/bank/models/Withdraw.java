package bank.models;

/**
 * Withdraw Class
 *
 * For WITHDRAW operation
 */

public class Withdraw {
    private String accNo;
    private String date;
    private double amt;

    public Withdraw(String accNo, String date, double amt) {
        this.accNo = accNo;
        this.date = date;
        this.amt = amt;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }
}
