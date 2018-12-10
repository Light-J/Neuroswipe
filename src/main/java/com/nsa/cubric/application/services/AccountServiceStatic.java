package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;

public interface AccountServiceStatic {
    public Account registerNewUserAccount(AccountDTO account) throws EmailExistsException;
    public Account getAccountByEmail(String email);
    public Account getAccountById(Long id);
    public Profile getProfileByEmail(String email);
    public Boolean updateProfile(Profile profile);
}
