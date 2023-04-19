package sg.edu.nus.iss.b1assessmentpractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.b1assessmentpractice.model.Transact;
import sg.edu.nus.iss.b1assessmentpractice.repository.AccountRepository;

@Service
public class LogAuditService {
    
    @Autowired
    private AccountRepository acctRepo;

    public void logDetails(Transact transact) {

        acctRepo.logDetails(transact);
        
    }

}
