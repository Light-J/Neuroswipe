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
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
        );

        profileMapper = (rs, i) -> new Profile(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("postcode"),
                rs.getInt("age"),
                rs.getString("gender")
        );
    }

    @Override
    public Account getAccountByEmail(String email){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, email, password, role from useraccounts WHERE email = ?",
                    new Object[]{email},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public Account getAccountById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, email, password, role from useraccounts WHERE id = ?",
                    new Object[]{id},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insertNewAccount(AccountDTO account){
        System.out.println("Inserting for "+account.getEmail());
        jdbcTemplate.update(
                "INSERT into useraccounts (email, password, role) values (?,?,?)",
                account.getEmail(), account.getPassword(), "user");
        System.out.println("Insert for "+account.getEmail() + " was successful");
    }

    @Override
    public List<Account> getAllAccounts(){
        return jdbcTemplate.query(
                "SELECT id, password, email, role FROM useraccounts",
                new Object[]{},accountMapper
        );
    }

    @Override
    public void insertNewProfile(Profile profile){
        jdbcTemplate.update(
                "INSERT INTO userprofiles (username, postcode, useraccountid, age, gender) VALUES (?,?,?,?,?)",
        profile.getUsername(), profile.getPostcode(), profile.getUserAccountId(), profile.getAge(), profile.getGender());
    }

    @Override
    public boolean updateProfile(Profile profile){
        try {
            jdbcTemplate.update(
                    "UPDATE userprofiles SET username=?, postcode=?, age=?, gender=? WHERE id=?",
                    profile.getUsername(), profile.getPostcode(), profile.getAge(), profile.getGender(), profile.getId());
            System.out.println("User Profile Updated");
            return true;
        } catch (Exception e){
            System.out.println("ERROR in updating user profile: " + e.toString());
            return false;
        }
    }

    @Override
    public Profile getProfileByAccountId(Long accountId){
            return jdbcTemplate.queryForObject(
                    "SELECT id, username, postcode, age, gender FROM userprofiles WHERE id = ?",
                    new Object[]{accountId},profileMapper);
    }

    @Override
    public Profile getProfileByEmail(String email) {
        Account account = getAccountByEmail(email);
        return getProfileByAccountId(account.getId());
    }

    @Override
    public boolean removeUser(Long userId){
        int rowsAffected = jdbcTemplate.update("DELETE FROM useraccounts WHERE id=?;",(userId));
        jdbcTemplate.update("DELETE FROM userprofiles WHERE id=?",(userId));
        return rowsAffected == 1;
    }

    @Override
    public Integer removeUserResponses(Long userId){
        int rowsAffected = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM userratings WHERE userprofileid = \n" +
                        "(SELECT id FROM userprofiles WHERE useraccountid = ?);",
                new Object[]{userId}, Integer.class);

        jdbcTemplate.update("CALL removeUserRatings(?)", (userId));
        return rowsAffected;
    }

    @Override
    public List<Account> searchUsers(String searchTerm, int offset){
        return jdbcTemplate.query("SELECT * FROM useraccounts WHERE email like ? LIMIT ?, 10",
                new Object[]{'%'+searchTerm+'%', offset}, accountMapper);
    }

    @Override
    public boolean updateUserRole(Long userId, String role){
        int rowsAffected = jdbcTemplate.update("UPDATE useraccounts SET role = ? WHERE id = ?", role, userId);
        return rowsAffected == 1;
    }
}
