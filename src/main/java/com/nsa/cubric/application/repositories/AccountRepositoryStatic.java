package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.PasswordResetToken;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class AccountRepositoryStatic implements AccountRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AccountRepositoryStatic.class);
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Account> accountMapper;
    private RowMapper<ProfileDto> profileMapper;
    private RowMapper<PasswordResetToken> tokenMapper;

    @Autowired
    public AccountRepositoryStatic(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        accountMapper = (rs, i) -> new Account(
                rs.getLong("account_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role"),
                (Boolean) rs.getObject("account_disabled")
        );

        profileMapper = (rs, i) -> new ProfileDto(
                rs.getLong("profile_id"),
                rs.getString("display_name"),
                rs.getString("postcode"),
                rs.getLong("account_id"),
                rs.getInt("age"),
                rs.getString("gender")
        );

        tokenMapper = (rs, i) -> new PasswordResetToken(
                rs.getString("token"),
                rs.getLong("account_id"),
                rs.getDate("expiry_date")
        );
    }

    @Override
    public Account getAccountByEmail(String email){
        try{
            return jdbcTemplate.queryForObject(
                    "select * from account WHERE email = ?",
                    new Object[]{email},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public Account getAccountById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "select * from account WHERE account_id = ?",
                    new Object[]{id},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insertNewAccount(AccountDto account){
        jdbcTemplate.update(
                "INSERT into account (email, password, role) values (?,?,?)",
                account.getEmail(), account.getPassword(), "user");
    }

    @Override
    public List<Account> getAllAccounts(){
        return jdbcTemplate.query(
                "SELECT account_id, password, email, role FROM account",
                new Object[]{},accountMapper
        );
    }


    @Override
    public boolean updateProfile(ProfileDto profileDto){
        try {
            if(profileDto.getPostcode().equals("")) {
                jdbcTemplate.update("UPDATE profile SET postcode_id = null WHERE profile_id = ?", profileDto.getId());
            } else {
                jdbcTemplate.update("UPDATE profile SET postcode_id = (SELECT brainschema.check_or_add_postcode(?)) WHERE profile_id = ?", profileDto.getPostcode(), profileDto.getId());
            }

            jdbcTemplate.update("UPDATE profile SET display_name=?, age=?, gender=? WHERE profile_id=?",
                        profileDto.getUsername(), profileDto.getAge(), profileDto.getGender(), profileDto.getId());
            return true;
        } catch (Exception e){
            LOG.debug(e.getMessage());
            return false;
        }
    }

    @Override
    public ProfileDto getProfileByAccountId(Long accountId){
            return jdbcTemplate.queryForObject(
                    "SELECT profile.profile_id, profile.display_name, postcode.postcode, profile.age, profile.gender, profile.account_id \n" +
                            "   FROM profile \n" +
                            "    LEFT JOIN postcode ON postcode.postcode_id = profile.postcode_id\n" +
                            "    WHERE account_id = ?;",
                    new Object[]{accountId},profileMapper);
    }

    @Override
    public ProfileDto getProfileByEmail(String email) {
        Account account = getAccountByEmail(email);
        return getProfileByAccountId(account.getId());
    }

    @Override
    public boolean removeUser(Long userId){
        int rowsAffected = jdbcTemplate.update("DELETE FROM account WHERE account_id=?;",(userId));
        return rowsAffected == 1;
    }

    @Override
    public Integer removeUserResponses(Long userId){
        int rowsAffected = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM rating WHERE profile_id = \n" +
                        "(SELECT profile_id FROM profile WHERE account_id = ?);",
                new Object[]{userId}, Integer.class);

        jdbcTemplate.update("CALL remove_user_ratings(?)", (userId));
        return rowsAffected;
    }

    @Override
    public List<Account> searchUsers(String searchTerm, int offset){
        return jdbcTemplate.query("SELECT * FROM account WHERE email like ? LIMIT ?, 10",
                new Object[]{'%'+searchTerm+'%', offset}, accountMapper);
    }

    @Override
    public boolean updateUserRole(Long userId, String role){
        int rowsAffected = jdbcTemplate.update("UPDATE account SET role = ? WHERE account_id = ?", role, userId);
        return rowsAffected == 1;
    }

    @Override
    public boolean updateUserEmail(String oldEmail, String newEmail){
        int rowsAffected = jdbcTemplate.update("UPDATE account SET email = ? WHERE email = ?", newEmail, oldEmail);
        return rowsAffected == 1;
    }

    @Override
    public boolean disableUser(Long userId){
        int rowsAffected = jdbcTemplate.update("UPDATE account SET account_disabled = 1 WHERE account_id = (SELECT account_id FROM profile WHERE profile_id = ?)", userId);
        return rowsAffected == 1;
    }

    @Override
    public boolean updateUserDisabledStatus(Long userId, boolean disabled){
        int rowsAffected = jdbcTemplate.update("UPDATE account set account_disabled = ? WHERE account_id = ?;", disabled, userId);
        return rowsAffected == 1;
    }

    @Override
    public void removeExistingResetTokenForUser(Long accountId){
        jdbcTemplate.update("DELETE FROM password_reset_token WHERE account_id = ? ", accountId);
    }

    @Override
    public boolean addResetToken(PasswordResetToken token){

        System.out.println("Inserting new token with details " + token.toString());

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        java.sql.Date date = new java.sql.Date(token.getExpiryDate().getTime());

//        String datetime = sdf.format(token.getExpiryDate());
        System.out.println("########" + date.toString());

        int rowsAffected = jdbcTemplate.update("INSERT INTO password_reset_token (token, account_id, expiry_date) values (?, ?, ?)", token.getToken(), token.getAccountId(), sdf.format(token.getExpiryDate()));
        return rowsAffected == 1;
    }

    @Override
    public PasswordResetToken getResetToken(String token){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM password_reset_token WHERE token = ?", new Object[]{token}, tokenMapper);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void ChangeUserPassword(Long accountId, String password){
        jdbcTemplate.update("UPDATE account set password = ? WHERE account_id = ?", password, accountId);
    }
}
