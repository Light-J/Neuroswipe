package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.services.LoggedUserService;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    private AccountService accountService;

    private LoggedUserService loggedUserService;

    @Autowired
    public RegistrationController(AccountService aRepo, LoggedUserService loggedUserService){
        this.accountService = aRepo;
        this.loggedUserService = loggedUserService;
    }
    
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /registration/account");

        model.addAttribute("account", new AccountDto());
        return "register_account";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetailsForm(Model model){
        ProfileDto userProfileDto = new ProfileDto(accountService.getProfileByEmail(loggedUserService.getUsername()));
        model.addAttribute("profile", userProfileDto);
        model = addOptionsToModelView(model);
        return "register_details";
    }

    @RequestMapping(value="/details", method = RequestMethod.POST)
    public ModelAndView updateUserProfile(@ModelAttribute("profile") @Valid ProfileDto profileDto, BindingResult result, Model model){
        model.addAttribute("profile", profileDto);
        model = addOptionsToModelView(model);

        if (result.hasErrors()){
            return new ModelAndView("register_details", "model", model);
        }
        Boolean updateProfile = accountService.updateProfile(profileDto);
        return new ModelAndView("training", "profile", profileDto);
    }

    private Model addOptionsToModelView(Model model){
        model.addAttribute("ethnicityOptions", accountService.getAllEthnicityOptions());
        model.addAttribute("religionOptions", accountService.getAllReligionOptions());
        model.addAttribute("relationshipOptions", accountService.getAllRelationshipOptions());
        model.addAttribute("sexualOrientationOptions", accountService.getAllSexualOrientationOptions());
        model.addAttribute("caringResponsibilityOptions", accountService.getAllCarerResponsibilityOptions());
        return model;
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("account") @Valid AccountDto accountDto,
            BindingResult result,
            HttpServletRequest request){

        Account registered = new Account();
        String originalPassword = accountDto.getPassword();

        result = accountService.checkPasswordStrengthOnAccount(accountDto, result);

        if(!result.hasErrors()){
            LOG.debug("Creating user account");
            registered = createUserAccount(accountDto);
        }

        if(registered == null){
            result.addError(new ObjectError("email", "Email already exists"));
        }

        if(result.hasErrors()){
            return new ModelAndView("register_account", "account", accountDto);
        } else {
            authWithHttpServletRequest(request, accountDto.getEmail(), originalPassword);
            return new ModelAndView("redirect:/registration/details", "account", accountDto);
        }
    }

    private Account createUserAccount(AccountDto accountDto){
        Account registered = null;
        try{
            registered = accountService.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e){
            return null;
        }
        return registered;
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            LOG.debug("Error logging in");
        }
    }
}
