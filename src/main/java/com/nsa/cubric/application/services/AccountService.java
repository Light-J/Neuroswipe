package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nsa.cubric.application.configurators.WebSecurityConfig.passwordEncoder;

@Service
public class AccountService implements AccountServiceStatic {

    private AccountRepositoryStatic accountRepository;

    @Autowired
    public AccountService(AccountRepositoryStatic aRepo){
        accountRepository = aRepo;
    }

    @Transactional
    @Override
    public Account registerNewUserAccount(AccountDTO account) throws EmailExistsException {
        if(emailExist(account.getEmail())){
            throw new EmailExistsException("Account already exists");
        }
        account.setPassword(passwordEncoder().encode(account.getPassword()));
        accountRepository.insertNewAccount(account);
        return accountRepository.getAccountByEmail(account.getEmail());
    }

    @Override
    public Account getAccountByEmail(String email){
        return accountRepository.getAccountByEmail(email);
    }

    @Override
    public Profile getProfileByEmail(String email){
        return accountRepository.getProfileByEmail(email);
    }

    @Override
    public Account getAccountById(Long id){
        return accountRepository.getAccountById(id);
    }

    @Override
    public Boolean updateProfile(Profile profile) {
        return accountRepository.updateProfile(profile);
    }

    @Override
    public List<Account> searchUsers(String searchTerm, int page){
        return accountRepository.searchUsers(searchTerm, (10 * (page-1)));
    }

    private boolean emailExist(String email) {
        return (accountRepository.getAccountByEmail(email) != null);
    }
}
