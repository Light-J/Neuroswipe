package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.accountUtils.EmailExistsException;

public interface AccountServiceStatic {
    public Account registerNewUser(AccountDTO account) throws EmailExistsException;
    public Account loginUser(AccountDTO account) throws Exception;

}
