package pl.wybankuj.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String bankName;

    private String logo;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Agency> agencies = new ArrayList<>();

    @OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Mortgage> mortgages = new ArrayList<>();

    public Bank() {
    }

    public Bank(String bankName, String logo) {
        this.bankName = bankName;
        this.logo = logo;
    }

    public Bank(Long id, String bankName, String logo) {
        this.id = id;
        this.bankName = bankName;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Mortgage> getMortgages() {
        return mortgages;
    }

    public void setMortgages(List<Mortgage> mortgages) {
        this.mortgages = mortgages;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", logo='" + logo + '\'' +
                ", agencies=" + agencies +
                ", loans=" + loans +
                ", mortgages=" + mortgages +
                '}';
    }
}
