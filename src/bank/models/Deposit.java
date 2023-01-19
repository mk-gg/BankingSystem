package bank.models;

/**
 * Deposit Class
 *
 * for DEPOSIT operations
 */

public class Deposit {

    private String accNo;
    private double amt;
    private String date;



    public Deposit(String accNo, double amt, String date) {
        this.accNo = accNo;
        this.amt = amt;
        this.date = date;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
