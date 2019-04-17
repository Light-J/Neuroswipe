package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.PasswordResetToken;
import com.nsa.cubric.application.domain.Profile;
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
    private RowMapper<Profile> profileMapper;
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

        profileMapper = (rs, i) -> new Profile(
                rs.getLong("profile_id"),
                rs.getLong("account_id"),
                rs.getString("display_name"),
                rs.getInt("age"),
                (Boolean) rs.getObject("disability"),
                (Boolean) rs.getObject("gender_identity_match"),
                rs.getString("sex"),
                rs.getInt("ethnicity"),
                rs.getInt("religion"),
                rs.getInt("sexual_orientation"),
                rs.getInt("relationship")
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
    public boolean updateProfile(Profile profile){
        try {
            jdbcTemplate.update("UPDATE profile SET display_name=?, age=?, gender=? WHERE profile_id=?",
                        profileDto.getUsername(), profileDto.getAge(), profileDto.getGender());
            return true;
        } catch (Exception e){
            LOG.debug(e.getMessage());
            return false;
        }
    }

    @Override
    public Profile getProfileByAccountId(Long accountId){
        return jdbcTemplate.queryForObject("SELECT profile.profile_id,\n" +
                "       profile.account_id,\n" +
                "       profile.display_name,\n" +
                "       profile.age,\n" +
                "       profile.disability,\n" +
                "       e.ethnicity,\n" +
                "       profile.gender_identity_match,\n" +
                "       r2.religion,\n" +
                "       profile.sex,\n" +
                "       so.sexual_orientation,\n" +
                "       r.relationship\n" +
                "FROM profile\n" +
                "    JOIN caring_responsibilities cr on profile.caring_responsibilities_id = cr.caring_responsibilities_id\n" +
                "    JOIN ethnicity e on profile.ethnicity_id = e.ethnicity_id\n" +
                "    JOIN relationship r on profile.relationship_id = r.relationship_id\n" +
                "    JOIN sexual_orientation so on profile.sexual_orientation_id = so.sexual_orientation_id\n" +
                "    JOIN religion r2 on profile.religion_id = r2.religion_id WHERE account_id = ?; ",
                new Object[]{accountId},profileMapper);
    }

    @Override
    public Profile getProfileByEmail(String email) {
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
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
