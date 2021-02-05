package pl.wybankuj.entity;

import java.math.BigDecimal;

public class LoanWithPayment {
    private Loan loan;
    private Long bankId;
    private String logo;
    private BigDecimal payment;

    public LoanWithPayment() {
    }

    public LoanWithPayment(Loan loan, Long bankId, String logo, BigDecimal payment) {
        this.loan = loan;
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

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
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
