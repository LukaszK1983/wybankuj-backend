package pl.wybankuj.entity;

public class UserLoan {
    private int amount;
    private int creditPeriod;
    private int age;
    private String chooseServiceCharge;
    private String chooseInsurance;

    public UserLoan() {
    }

    public UserLoan(int amount, int creditPeriod, int age, String chooseServiceCharge, String chooseInsurance) {
        this.amount = amount;
        this.creditPeriod = creditPeriod;
        this.age = age;
        this.chooseServiceCharge = chooseServiceCharge;
        this.chooseInsurance = chooseInsurance;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(int creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getChooseServiceCharge() {
        return chooseServiceCharge;
    }

    public void setChooseServiceCharge(String chooseServiceCharge) {
        this.chooseServiceCharge = chooseServiceCharge;
    }

    public String getChooseInsurance() {
        return chooseInsurance;
    }

    public void setChooseInsurance(String chooseInsurance) {
        this.chooseInsurance = chooseInsurance;
    }
}
