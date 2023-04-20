package sg.edu.nus.iss.b1assessmentpractice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.b1assessmentpractice.model.Account;
import sg.edu.nus.iss.b1assessmentpractice.model.Transact;
import sg.edu.nus.iss.b1assessmentpractice.service.AccountService;
import sg.edu.nus.iss.b1assessmentpractice.service.FundsTransferService;
import sg.edu.nus.iss.b1assessmentpractice.service.LogAuditService;

@Controller
@RequestMapping
public class FundsTransferController {

    @Autowired
    private AccountService acctSvc;
    @Autowired
    private FundsTransferService fundsTransferSvc;
    @Autowired
    private LogAuditService laSvc;
    
    @PostMapping(path="/transfer")
    public String postTransfer(Model m, @ModelAttribute @Valid Account account
        , BindingResult binding) {

        if (binding.hasErrors()) {
            return "index";
        }

        List<ObjectError> errors = acctSvc.transferValidation(account);
        if (!errors.isEmpty()) {
            for (ObjectError e : errors) {
                binding.addError(e);
            }
            return "index";
        }

        Transact transact = new Transact();

        try {
        boolean isTransferred = fundsTransferSvc.transferFunds(account);
        if (!isTransferred) {
            return "index";
        }

        laSvc.logDetails(transact);

        transact.setSenderName(FundsTransferService.senderName);
        transact.setReceiverName(FundsTransferService.receiverName);

        m.addAttribute("transact", transact);
        } catch (Exception ex) {
            ex.printStackTrace();
			m.addAttribute("error", ex.getMessage());
            return "index";
        }
        
        return "transfer";
}
}
