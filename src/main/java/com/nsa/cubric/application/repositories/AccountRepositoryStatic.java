package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
import com.nsa.cubric.application.domain.Account;

import java.util.List;

public interface AccountRepositoryStatic {
    public Account findByEmail(String email);
    public void insertNewAccount(AccountDTO account);
    public List<Account> getAll();
    public void insertNewProfile(ProfileDTO profile);
    public boolean removeUser(Long userId);
    public Long removeUserResponses(Long userId);
}
