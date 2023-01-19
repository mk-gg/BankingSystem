package bank.models;

/**
 * Transfer Class
 *
 * For TRANSFER operations
 */
public class Transfer {
    private String custAccNo;
    private String targetAccNo;
    private double amt;
    private String date;

    public Transfer(String custAccNo, String date, double amt, String targetAccNo){
        this.custAccNo = custAccNo;
        this.date = date;
        this.amt = amt;
        this.targetAccNo = targetAccNo;
    }

    public String getTargetAccNo() {
        return targetAccNo;
    }

    public void setTargetAccNo(String targetAccNo) {
        this.targetAccNo = targetAccNo;
    }

    public String getCustAccNo() {
        return custAccNo;
    }

    public void setCustAccNo(String custAccNo) {
        this.custAccNo = custAccNo;
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
