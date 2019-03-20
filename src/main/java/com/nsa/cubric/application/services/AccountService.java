package com.nsa.cubric.application.services;

import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface AccountService {
    Account registerNewUserAccount(AccountDto account) throws EmailExistsException;
    Account getAccountByEmail(String email);
    Account getAccountById(Long id);
    ProfileDto getProfileByEmail(String email);
    Boolean updateProfile(ProfileDto profileDto);
    List<Account> searchUsers(String searchTerm, int page);
    BindingResult checkPasswordStrength(AccountDto account, BindingResult result);
    Boolean emailExist(String email);
    Boolean updateEmail(String email);
    Boolean checkEmailFormat(String email);
}
