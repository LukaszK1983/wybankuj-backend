package pl.wybankuj.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String name;
    private String phone;
    private String email;
    private String bankName;
    private String credit;
    private String offer;
    private int amount;
    private int creditPeriod;

    public User() {
    }

    public User(LocalDateTime date, String name, String phone, String email, String bankName, String credit, String offer, int amount, int creditPeriod) {
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bankName = bankName;
        this.credit = credit;
        this.offer = offer;
        this.amount = amount;
        this.creditPeriod = creditPeriod;
    }

    public User(Long id, LocalDateTime date, String name, String phone, String email, String bankName, String credit, String offer, int amount, int creditPeriod) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bankName = bankName;
        this.credit = credit;
        this.offer = offer;
        this.amount = amount;
        this.creditPeriod = creditPeriod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
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
}
