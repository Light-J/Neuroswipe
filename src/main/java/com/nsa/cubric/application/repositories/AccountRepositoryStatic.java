package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;

import java.util.List;

public interface AccountRepositoryStatic {
    Account getAccountByEmail(String email);
    void insertNewAccount(AccountDTO account);
    List<Account> getAllAccounts();
    Profile getProfileByAccountId(Long accountId);
    Profile getProfileByEmail(String email);
    Account getAccountById(Long Id);
    Integer removeUserResponses(Long userId);
    List<Account> searchUsers(String searchTerm, int offset);
    boolean updateProfile(Profile profile);
    boolean removeUser(Long userId);
    boolean updateUserRole(Long userId, String role);


}
