package sg.edu.nus.iss.b1assessmentpractice.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.b1assessmentpractice.model.Account;
import sg.edu.nus.iss.b1assessmentpractice.model.Transact;
import sg.edu.nus.iss.b1assessmentpractice.service.AccountService;
import sg.edu.nus.iss.b1assessmentpractice.service.FundsTransferService;

import static sg.edu.nus.iss.b1assessmentpractice.repository.DBQueries.*;

@Repository
public class AccountRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired @Qualifier("transact")
    private RedisTemplate<String, String> redisTemplate;

    public List<Account> getAllNamesAndId() {
        
        List<Account> accounts = new ArrayList<Account>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_DETAILS);

        while (rs.next()) {
            accounts.add(Account.createFromRs(rs));
        }
        return accounts;
    }

    public Optional<Account> getBalance(String account_id) {
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_BALANCE_BY_ACCOUNT_ID, account_id);
        if (rs.first()) {
            return Optional.of(Account.createBalFromRs(rs));
        } 
        return Optional.empty();
    }

    public boolean updateAccountBalance(BigDecimal balance, String account_id) {
        
        return jdbcTemplate.update(UPDATE_ACCOUNT_BALANCE, balance, account_id) > 0;
    }

    public void logDetails(Transact transact) {

        transact.setTransactionId(FundsTransferService.transaction_id);
        transact.setDate(new Date());
        transact.setFrom_account(AccountService.fromAcct);
        transact.setTo_account(AccountService.toAcct);
        transact.setAmount(AccountService.transactAmt);

        this.redisTemplate.opsForValue()
            .set(transact.getTransactionId(), transact.toJSON().toString());

    }

    public Optional<Transact> getSenderName(String account_id) {
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_NAME_BY_ACCOUNT_ID, account_id);
        if (rs.first()) {
            return Optional.of(Transact.createSenderNameFromRs(rs));
        } 
        return Optional.empty();
    }

    public Optional<Transact> getReceiverName(String account_id) {
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_NAME_BY_ACCOUNT_ID, account_id);
        if (rs.first()) {
            return Optional.of(Transact.createReceiverNameFromRs(rs));
        } 
        return Optional.empty();
    }

}
