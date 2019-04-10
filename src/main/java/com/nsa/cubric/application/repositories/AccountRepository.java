package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.PasswordResetToken;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.domain.Account;

import java.util.List;

public interface AccountRepository {
    Account getAccountByEmail(String email);
    void insertNewAccount(AccountDto account);
    List<Account> getAllAccounts();
    ProfileDto getProfileByAccountId(Long accountId);
    ProfileDto getProfileByEmail(String email);
    Account getAccountById(Long Id);
    Integer removeUserResponses(Long userId);
    List<Account> searchUsers(String searchTerm, int offset);
    boolean updateProfile(ProfileDto profileDto);
    boolean removeUser(Long userId);
    boolean updateUserRole(Long userId, String role);
    boolean updateUserEmail(String oldEmail, String newEmail);
    boolean disableUser(Long userId);
    boolean updateUserDisabledStatus(Long userId, boolean disabled);
    void removeExistingResetTokenForUser(Long accountId);
    boolean addResetToken(PasswordResetToken token);
    PasswordResetToken getResetToken(Long accountId);

}
