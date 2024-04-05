/**
 * @author <Nguyen A Luy - S3891919>
 */
package model;

public class BankInfo {
    private String bankName;
    private String customerName;
    private String bankNumber;
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public BankInfo(String bankName, String customerName, String bankNumber) {
        this.bankName = bankName;
        this.customerName = customerName;
        this.bankNumber = bankNumber;
    }

    public BankInfo() {
        this.bankName = "Default";
        this.customerName = "Default";
        this.bankNumber = "Default";
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankName='" + bankName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                '}';
    }
}
