package sg.edu.nus.iss.b1assessmentpractice.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import sg.edu.nus.iss.b1assessmentpractice.model.Account;
import sg.edu.nus.iss.b1assessmentpractice.repository.AccountRepository;

@Service
public class AccountService {

    public static String fromAcct;
    public static String toAcct;
    public static BigDecimal transactAmt;

    @Autowired
    private AccountRepository acctRepo;
    
    public List<ObjectError> transferValidation(Account account) {

        List<ObjectError> errors = new LinkedList<>();

        fromAcct = account.getAccount_id().substring(0, 10);
        account.setFromAcctId(fromAcct);
        
        if (account.getAccount_id().length() >= 21) {
        toAcct = account.getAccount_id().substring(11, 21);
        account.setToAcctId(toAcct);
        }
        if (account.getFromAcctId().equalsIgnoreCase(account.getToAcctId())) {
            FieldError e = new FieldError("account", "fromAcctId", "From and to accounts cannot be the same");
            errors.add(e);
        }

        Optional<Account> senderBal = acctRepo.getBalance(account.getFromAcctId());
        
        transactAmt = account.getAmount();
        if (!senderBal.isEmpty()) {
            account.setBalance(senderBal.get().getBalance());
            if (account.getBalance().compareTo(transactAmt) < 0) {
                FieldError e = new FieldError("account", "balance", "Insufficient balance!");
                errors.add(e);
            }
        }
        return errors;
    }

}
