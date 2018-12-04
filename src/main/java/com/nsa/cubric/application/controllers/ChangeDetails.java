package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ChangeDetails {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAdminPage(Model model){

        String userName = LoggedUserService.getUsername();



        model.addAttribute("profile", new ProfileDTO());

        return "changedetails";
    }

}
