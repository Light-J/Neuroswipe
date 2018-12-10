package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
import com.nsa.cubric.application.domain.Account;

import java.util.List;

public interface AccountRepositoryStatic {
    public Account getAccountByEmail(String email);
    public void insertNewAccount(AccountDTO account);
    public List<Account> getAllAccounts();
    public void insertNewProfile(ProfileDTO profile);

    boolean updateProfile(ProfileDTO profile);

    ProfileDTO getProfileByAccountId(Long accountId);

    ProfileDTO getProfileByEmail(String email);
    Account getAccountById(Long Id);

    public boolean removeUser(Long userId);
    public Long removeUserResponses(Long userId);
}
