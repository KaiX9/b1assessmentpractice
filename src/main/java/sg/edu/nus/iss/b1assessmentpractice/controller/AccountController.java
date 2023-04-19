package sg.edu.nus.iss.b1assessmentpractice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.b1assessmentpractice.model.Account;
import sg.edu.nus.iss.b1assessmentpractice.repository.AccountRepository;

@Controller
@RequestMapping
public class AccountController {
    
    @Autowired
    private AccountRepository accRepo;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(path={"/", "/index.html"})
    public String getLoadingPage(Model m, @ModelAttribute Account account) {

        List<Account> options = accRepo.getAllNamesAndId();
    
        System.out.println("Options: " + options);
        m.addAttribute("options", options);
        m.addAttribute("account", account);
        return "index";
    }

}
