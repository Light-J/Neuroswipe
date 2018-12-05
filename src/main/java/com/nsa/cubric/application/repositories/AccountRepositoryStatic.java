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


    boolean updateProfile(ProfileDTO profile);

    ProfileDTO getProfileByAccountID(long accountID);

    ProfileDTO getProfileByEmail(String email);

    public boolean removeUser(Integer userId);
    public Integer removeUserResponses(Integer userId);

}
