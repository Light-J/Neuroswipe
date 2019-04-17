package com.nsa.cubric.application.services;

import com.nsa.cubric.application.configurators.PasswordStrengthConfig;
import com.nsa.cubric.application.domain.*;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.repositories.AccountRepository;

import com.nulabinc.zxcvbn.Zxcvbn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nsa.cubric.application.configurators.WebSecurityConfig.passwordEncoder;

@Service
public class AccountServiceStatic implements AccountService {

    private final PasswordStrengthConfig passwordStrengthConfig;

    private AccountRepository accountRepository;

    private LoggedUserService loggedUserService;

    private JavaMailSender sender;


    @Autowired
    public AccountServiceStatic(AccountRepository aRepo, LoggedUserService loggedUserService, JavaMailSender sender, PasswordStrengthConfig passwordStrengthConfig){
        this.accountRepository = aRepo;
        this.loggedUserService = loggedUserService;
        this.sender = sender;
        this.passwordStrengthConfig = passwordStrengthConfig;
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
    public Profile getProfileByEmail(String email){
        return accountRepository.getProfileByEmail(email);
    }

    @Override
    public Account getAccountById(Long id){
        return accountRepository.getAccountById(id);
    }

    @Override
    public Boolean updateProfile(ProfileDto profileDto) {
        Long profileId = loggedUserService.getUserProfileId();
        Long accountId = loggedUserService.getUserAccountId();
        return accountRepository.updateProfile(new Profile(profileDto, profileId, accountId));
    }

    @Override
    public List<Account> searchUsers(String searchTerm, int page){
        return accountRepository.searchUsers(searchTerm, (10 * (page-1)));
    }

    @Override
    public BindingResult checkPasswordStrengthOnAccount(AccountDto account, BindingResult result){
        if(checkPasswordStrength(account.getPassword()) < passwordStrengthConfig.getStrength()){
            result.addError(new ObjectError("password", "Password is too weak"));
        }

        return result;
    }

    public int checkPasswordStrength(String password){
        Zxcvbn strengthChecker = new Zxcvbn();
        return strengthChecker.measure(password).getScore();
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
    public boolean sendResetToken(PasswordResetToken token, String contextPath){
        try {

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(getAccountById(token.getAccountId()).getEmail());
            helper.setText("You have requested a password reset for your account. Please follow this link to reset your password: " + contextPath + "/login/changePassword?token="+ token.getToken() + "&id=" + token.getAccountId().toString());
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


    public String validatePasswordResetToken(Long accountId, String token){
        PasswordResetToken passwordResetToken = accountRepository.getResetToken(token);
        if(passwordResetToken == null || !passwordResetToken.getAccountId().equals(accountId) || !passwordResetToken.isValid()){
            return "invalid";
        }

        Account user = accountRepository.getAccountById(accountId);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    public void changeUserPassword(String password, Long accountId){
        accountRepository.ChangeUserPassword(accountId, passwordEncoder().encode(password));
        accountRepository.removeExistingResetTokenForUser(accountId);
    }

    public List<Relationship> getAllRelationshipOptions(){
        return accountRepository.getAllRelationshipOptions();
    }
    public List<Religion> getAllReligionOptions(){
        return  accountRepository.getAllReligionOptions();
    }
    public List<SexualOrientation> getAllSexualOrientationOptions(){
        return accountRepository.getAllSexualOrientationOptions();
    }
    public List<Ethnicity> getAllEthnicityOptions(){
        return accountRepository.getAllEthnicityOptions();
    }

}
