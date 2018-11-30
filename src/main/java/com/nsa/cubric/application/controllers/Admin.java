package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.LoggedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping(value = "/admin")
public class Admin {
    private static final Logger LOG = LoggerFactory.getLogger(Practice.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAdminPage(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /admin/");
        System.out.println();

        if(!checkIfAdmin()){
            return "noaccess";
        }

        return "admin";
    }

    @RequestMapping(value = "/noaccess", method=RequestMethod.GET)
    public String showNoAccessPage(){
        return "noaccess";
    }

    public Boolean checkIfAdmin(){
        if(LoggedUserService.getUserRole() != null){
            if(LoggedUserService.getUserRole().equals("admin")){
                return true;
            }
        }
        return false;
    }
}
