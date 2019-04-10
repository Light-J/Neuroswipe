package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.dto.ChangePasswordDto;
import com.nsa.cubric.application.services.AccountService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class CustomLoginController {

    private AccountService accountService;

    @Autowired
    public CustomLoginController(AccountService accountService){
        this.accountService = accountService;
    }


    @RequestMapping(path = "/disabled", method = RequestMethod.GET)
    public String disabledPage() {
        return "disabled";
    }

    @RequestMapping(path = "/reset", method = RequestMethod.GET)
    public ModelAndView resetPage(Model model){
        model.addAttribute("message", "none");
        return new ModelAndView("reset_request", "model", model);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Model model,
                                         @RequestParam("id") long acountId, @RequestParam("token") String token) {
        String result = accountService.validatePasswordResetToken(acountId, token);
        if (result != null) {
            model.addAttribute("message", "invalid");
            return "reset_request";
        }
        return "change_password";
    }

}
