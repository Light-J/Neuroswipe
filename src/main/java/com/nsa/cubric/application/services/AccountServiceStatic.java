package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.PasswordResetToken;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.repositories.AccountRepository;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nsa.cubric.application.configurators.WebSecurityConfig.passwordEncoder;

@Service
public class AccountServiceStatic implements AccountService {

    private AccountRepository accountRepository;

    private LoggedUserService loggedUserService;

    private JavaMailSender sender;

    @Autowired
    public AccountServiceStatic(AccountRepository aRepo, LoggedUserService loggedUserService, JavaMailSender sender){
        this.accountRepository = aRepo;
        this.loggedUserService = loggedUserService;
        this.sender = sender;
    }

    @Transactional
    @Override
    public Account registerNewUserAccount(AccountDto account) throws EmailExistsException {
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
    public ProfileDto getProfileByEmail(String email){
        return accountRepository.getProfileByEmail(email);
    }

    @Override
    public Account getAccountById(Long id){
        return accountRepository.getAccountById(id);
    }

    @Override
    public Boolean updateProfile(ProfileDto profileDto) {
        profileDto.setId(accountRepository.getProfileByEmail(loggedUserService.getUsername()).getId());
        return accountRepository.updateProfile(profileDto);
    }

    @Override
    public List<Account> searchUsers(String searchTerm, int page){
        return accountRepository.searchUsers(searchTerm, (10 * (page-1)));
    }

    @Override
    public BindingResult checkPasswordStrength(AccountDto account, BindingResult result){
        Zxcvbn strengthChecker = new Zxcvbn();

        if(strengthChecker.measure(account.getPassword()).getScore() < 2){
            result.addError(new ObjectError("password", "Password is too weak"));
        }

        return result;
    }

    @Override
    public Boolean updateEmail(String newEmail){
        String oldEmail = loggedUserService.getUsername();
        return accountRepository.updateUserEmail(oldEmail, newEmail);
    }

    @Override
    public Boolean checkEmailFormat(String email){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean disableUser(){
        Long userId = loggedUserService.getUserProfileId();
        return accountRepository.disableUser(userId);
    }

    @Override
    public boolean deleteUser(){
        Long userId = loggedUserService.getUserProfileId();
        return accountRepository.removeUser(userId);
    }

    public Boolean emailExist(String email) {
        return (accountRepository.getAccountByEmail(email) != null);
    }

    @Override
    public PasswordResetToken createResetToken(String email) {
        Long accountId = accountRepository.getAccountByEmail(email).getId();
        PasswordResetToken token = new PasswordResetToken(accountId);
        accountRepository.addResetToken(token);
        return token;
    }

    @Override
    public boolean sendResetToken(PasswordResetToken token){
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo("lightjp@cardiff.ac.uk");
            helper.setText(token.getToken());
            helper.setSubject("Password reset");
            sender.send(message);

        } catch (MessagingException e){
            return false;
        }
        return true;
    }

    @Override
    public void removeExistingTokens(String email) {
        Long accountId = accountRepository.getAccountByEmail(email).getId();
        accountRepository.removeExistingResetTokenForUser(accountId);
    }



}
