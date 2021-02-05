package pl.wybankuj.entity;

import java.math.BigDecimal;

public class MortgageWithPayment {
    private Mortgage mortgage;
    private Long bankId;
    private String logo;
    private BigDecimal payment;

    public MortgageWithPayment() {
    }

    public MortgageWithPayment(Mortgage mortgage, Long bankId, String logo, BigDecimal payment) {
        this.mortgage = mortgage;
        this.bankId = bankId;
        this.logo = logo;
        this.payment = payment;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Mortgage getMortgage() {
        return mortgage;
    }

    public void setMortgage(Mortgage mortgage) {
        this.mortgage = mortgage;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}
