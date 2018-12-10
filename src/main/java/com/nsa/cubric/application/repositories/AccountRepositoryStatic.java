package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;

import java.util.List;

public interface AccountRepositoryStatic {
    public Account getAccountByEmail(String email);
    public void insertNewAccount(AccountDTO account);
    public List<Account> getAllAccounts();
    public void insertNewProfile(Profile profile);

    boolean updateProfile(Profile profile);

    Profile getProfileByAccountId(Long accountId);

    Profile getProfileByEmail(String email);
    Account getAccountById(Long Id);

    public boolean removeUser(Long userId);
    public Long removeUserResponses(Long userId);
}
