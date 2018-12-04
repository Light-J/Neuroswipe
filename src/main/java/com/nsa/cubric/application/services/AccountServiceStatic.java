package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;

public interface AccountServiceStatic {
    public Account registerNewUserAccount(AccountDTO account) throws EmailExistsException;
    public void registerNewUserProfile(ProfileDTO profile);
    public Account findByEmail(String email);

}
