package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository implements AccountRepositoryStatic {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Account> accountMapper;
    private RowMapper<Profile> profileMapper;

    @Autowired
    public AccountRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        accountMapper = (rs, i) -> new Account(
                rs.getLong("account_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
        );

        profileMapper = (rs, i) -> new Profile(
                rs.getLong("profile_id"),
                rs.getString("display_name"),
                rs.getString("postcode"),
                rs.getInt("age"),
                rs.getString("gender")
        );
    }

    @Override
    public Account getAccountByEmail(String email){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, email, password, role from account WHERE email = ?",
                    new Object[]{email},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public Account getAccountById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "select account_id, email, password, role from account WHERE id = ?",
                    new Object[]{id},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insertNewAccount(AccountDTO account){
        System.out.println("Inserting for "+account.getEmail());
        jdbcTemplate.update(
                "INSERT into account (email, password, role) values (?,?,?)",
                account.getEmail(), account.getPassword(), "user");
        System.out.println("Insert for "+account.getEmail() + " was successful");
    }

    @Override
    public List<Account> getAllAccounts(){
        return jdbcTemplate.query(
                "SELECT account_id, password, email, role FROM account",
                new Object[]{},accountMapper
        );
    }

    @Override
    public void insertNewProfile(Profile profile){
        int postcode_id = jdbcTemplate.queryForObject("SELECT brainschema.check_or_add_postcode(?)",
                new Object[]{profile.getPostcode()}, Integer.class);

        jdbcTemplate.update(
                "INSERT INTO profile (display_name, postcode_id, account_id, age, gender) VALUES (?,?,?,?,?)",
        profile.getUsername(), postcode_id, profile.getUserAccountId(), profile.getAge(), profile.getGender());
    }

    @Override
    public boolean updateProfile(Profile profile){
        try {
            if(profile.getPostcode().isEmpty()) {
                jdbcTemplate.update(
                        "UPDATE profile SET display_name=?, age=?, gender=? WHERE profile_id=?",
                        profile.getUsername(), profile.getAge(), profile.getGender(), profile.getId());
            } else {
                int postcode_id = jdbcTemplate.queryForObject("SELECT brainschema.check_or_add_postcode(?)",
                        new Object[]{profile.getPostcode()}, Integer.class);

                jdbcTemplate.update(
                        "UPDATE profile SET display_name=?, age=?, gender=?, postcode_id = ? WHERE profile_id=?",
                        profile.getUsername(), profile.getAge(), profile.getGender(), profile.getId(), postcode_id);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Profile getProfileByAccountId(Long accountId){
            return jdbcTemplate.queryForObject(
                    "SELECT profile.profile_id, profile.display_name, postcode.postcode, profile.age, profile.gender \n" +
                            "\tFROM profile \n" +
                            "    INNER JOIN postcode ON postcode.postcode_id = profile.postcode_id\n" +
                            "    WHERE profile_id = ?;",
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
                        "(SELECT id FROM profile WHERE account_id = ?);",
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
}
