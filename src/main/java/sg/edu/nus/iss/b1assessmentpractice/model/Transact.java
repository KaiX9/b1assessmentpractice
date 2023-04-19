package sg.edu.nus.iss.b1assessmentpractice.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Transact {
    
    private String transactionId;
    private Date date;
    private String from_account;
    private String to_account;
    private BigDecimal amount;
    private Account account;
    private String senderName;
    private String receiverName;

    public Transact() {

    }

    public Transact(String transactionId, Date date, String from_account, String to_account, BigDecimal amount,
            Account account, String senderName, String receiverName) {
        this.transactionId = transactionId;
        this.date = date;
        this.from_account = from_account;
        this.to_account = to_account;
        this.amount = amount;
        this.account = account;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getFrom_account() {
        return from_account;
    }
    public void setFrom_account(String from_account) {
        this.from_account = from_account;
    }
    public String getTo_account() {
        return to_account;
    }
    public void setTo_account(String to_account) {
        this.to_account = to_account;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transact [transactionId=" + transactionId + ", date=" + date + ", from_account=" + from_account
                + ", to_account=" + to_account + ", amount=" + amount + "]";
    }

    public JsonObject toJSON() {
        
        return Json.createObjectBuilder()
            .add("transactionId", this.getTransactionId())
            .add("date", dateFormatting(this.getDate()))
            .add("from_account", this.getFrom_account())
            .add("to_account", this.getTo_account())
            .add("amount", this.getAmount())
            .build();
    }

    public String dateFormatting(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public static Transact createSenderNameFromRs(SqlRowSet rs) {
        Transact transact = new Transact();

        transact.setSenderName(rs.getString("name"));
        return transact;
    }

    public static Transact createReceiverNameFromRs(SqlRowSet rs) {
        Transact transact = new Transact();

        transact.setReceiverName(rs.getString("name"));
        return transact;
    }
    
}
