package sg.edu.nus.iss.b1assessmentpractice.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.b1assessmentpractice.model.Account;
import sg.edu.nus.iss.b1assessmentpractice.model.Transact;
import sg.edu.nus.iss.b1assessmentpractice.repository.AccountRepository;

@Service
public class FundsTransferService {

    @Autowired
    private AccountRepository acctRepo;

    public static String transaction_id;
    public static String senderName;
    public static String receiverName;
    
    @Transactional(rollbackFor = Exception.class)
    public boolean transferFunds(Account account) {

        transaction_id = UUID.randomUUID().toString()
                            .substring(0, 8);
        
        System.out.println("Transaction Id: " + transaction_id);

        Optional<Account> senderBal = acctRepo.getBalance(account.getFromAcctId());
        Optional<Account> receiverBal = acctRepo.getBalance(account.getToAcctId());
        BigDecimal newSenderBal = senderBal.get().getBalance().subtract(account.getAmount());
        BigDecimal newReceiverBal = receiverBal.get().getBalance().add(account.getAmount());

        Optional<Transact> getSenderName = acctRepo.getSenderName(account.getFromAcctId());
        senderName = getSenderName.get().getSenderName();
        Optional<Transact> getReceiverName = acctRepo.getReceiverName(account.getToAcctId());
        receiverName = getReceiverName.get().getReceiverName();
        System.out.println("Transferring from " + senderName + " (" + account.getFromAcctId() + 
        ") to " + receiverName + " (" + account.getToAcctId() + ")");
        System.out.println("Transfer Amount: " + account.getAmount());
        System.out.println("Sender balance from " + senderBal + " to " + newSenderBal);
        System.out.println("Receiver balance from " + receiverBal + " to " + newReceiverBal);

        boolean updatedFrom = acctRepo.updateAccountBalance(newSenderBal, account.getFromAcctId());
        boolean updatedTo = acctRepo.updateAccountBalance(newReceiverBal, account.getToAcctId());

        if ((updatedFrom) && (updatedTo)) {
            return true;
        } else {
            return false;
        }
    }
}
