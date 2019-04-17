package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.PasswordResetToken;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Profile;

import java.util.List;

public interface AccountRepository {
    Account getAccountByEmail(String email);
    void insertNewAccount(AccountDto account);
    List<Account> getAllAccounts();
    Profile getProfileByAccountId(Long accountId);
    Profile getProfileByEmail(String email);
    Account getAccountById(Long Id);
    Integer removeUserResponses(Long userId);
    List<Account> searchUsers(String searchTerm, int offset);
    boolean updateProfile(Profile profile);
    boolean removeUser(Long userId);
    boolean updateUserRole(Long userId, String role);
    boolean updateUserEmail(String oldEmail, String newEmail);
    boolean disableUser(Long userId);
    boolean updateUserDisabledStatus(Long userId, boolean disabled);
    void removeExistingResetTokenForUser(Long accountId);
    boolean addResetToken(PasswordResetToken token);
    PasswordResetToken getResetToken(String token);
    void ChangeUserPassword(Long accountId, String password);

}
