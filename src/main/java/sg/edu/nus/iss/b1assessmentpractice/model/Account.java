package sg.edu.nus.iss.b1assessmentpractice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Account implements Serializable {
    
    @Size(min=10, message="Must have at least 10 characters")
    private String account_id;
    private String name;
    private BigDecimal balance;

    @Min(value=10, message="Must be more than 10")
    @NotNull(message="Amount cannot be empty")
    private BigDecimal amount;

    private String fromAcctId;
    private String toAcctId;
    private String comments;
    
    public Account() {

    }

    public Account(String account_id, String name, BigDecimal balance, BigDecimal amount, String comments) {
        this.account_id = account_id;
        this.name = name;
        this.balance = balance;
        this.amount = amount;
        this.comments = comments;
    }

    public String getFromAcctId() {
        return fromAcctId;
    }

    public void setFromAcctId(String fromAcctId) {
        this.fromAcctId = fromAcctId;
    }

    public String getToAcctId() {
        return toAcctId;
    }

    public void setToAcctId(String toAcctId) {
        this.toAcctId = toAcctId;
    }

    public String getAccount_id() {
        return account_id;
    }
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Account [account_id=" + account_id + ", name=" + name + ", balance=" + balance + "]";
    }
    
    public static Account createFromRs(SqlRowSet rs){
        Account account = new Account();

        account.setAccount_id(rs.getString("account_id"));
        account.setName(rs.getString("name"));
        account.setBalance(rs.getBigDecimal("balance"));
        
        return account;
    }

    public static Account createBalFromRs(SqlRowSet rs) {
        Account account = new Account();

        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}
