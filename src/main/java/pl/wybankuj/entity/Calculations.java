package pl.wybankuj.entity;

import java.math.BigDecimal;

public class Calculations {
    Loan loan;
    BigDecimal payment;
    BigDecimal serviceCharge;
    BigDecimal insurance;
    BigDecimal interests;
    BigDecimal totalCost;
    String logo;
    Long bankId;

    public Calculations(Loan loan, BigDecimal payment, BigDecimal serviceCharge, BigDecimal insurance, BigDecimal interests, BigDecimal totalCost, String logo, Long bankId) {
        this.loan = loan;
        this.payment = payment;
        this.serviceCharge = serviceCharge;
        this.insurance = insurance;
        this.interests = interests;
        this.totalCost = totalCost;
        this.logo = logo;
        this.bankId = bankId;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getInsurance() {
        return insurance;
    }

    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    public BigDecimal getInterests() {
        return interests;
    }

    public void setInterests(BigDecimal interests) {
        this.interests = interests;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}
