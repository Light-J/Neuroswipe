package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;

import java.util.List;

public interface AccountServiceStatic {
    Account registerNewUserAccount(AccountDTO account) throws EmailExistsException;
    Account getAccountByEmail(String email);
    Account getAccountById(Long id);
    Profile getProfileByEmail(String email);
    Boolean updateProfile(Profile profile);
    List<Account> searchUsers(String searchTerm, int page);

}
