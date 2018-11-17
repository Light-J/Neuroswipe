package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;

public interface AccountRepositoryStatic {
    public Account findByEmail(String email);
    public void insertNewAccount(AccountDTO account);
}
