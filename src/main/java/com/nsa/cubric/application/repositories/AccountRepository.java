package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
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
    private RowMapper<ProfileDTO> profileMapper;

    @Autowired
    public AccountRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        accountMapper = (rs, i) -> new Account(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
        );

        profileMapper = (rs, i) -> new ProfileDTO(
                rs.getString("username"),
                rs.getString("postcode"),
                rs.getLong("loggedInUserId"),
                rs.getInt("age"),
                rs.getString("gender")
        );
    }

    @Override
    public Account findByEmail(String email){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, email, password, role from useraccount WHERE email = ?",
                    new Object[]{email},accountMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insertNewAccount(AccountDTO account){
        System.out.println("Inserting for "+account.getEmail());
        jdbcTemplate.update(
                "INSERT into useraccount (email, password, role) values (?,?,?)",
                account.getEmail(), account.getPassword(), "user");
        System.out.println("Insert for "+account.getEmail() + " was successful");
    }

    @Override
    public List<Account> getAll(){
        return jdbcTemplate.query(
                "SELECT id, password, email, role FROM useraccount",
                new Object[]{},accountMapper
        );
    }

    @Override
    public void insertNewProfile(ProfileDTO profile){
        jdbcTemplate.update(
                "INSERT INTO userprofiles (username, postcode, useraccountid, age, gender) values (?,?,?,?,?)",
        profile.getUsername(), profile.getPostcode(), profile.getLoggedInUserId(), profile.getAge(), profile.getGender());
    }

    @Override
    public ProfileDTO getProfileByAccountID(long accountID){
        try{
            System.out.println("accountID:");
            System.out.println(accountID);
            return jdbcTemplate.queryForObject(
                    "select id, username, postcode, age, gender from userprofile WHERE useraccountid = ?",
                    new Object[]{accountID},profileMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public ProfileDTO getProfileByEmail(String email) {
        try {
            Account userAcount = findByEmail(email);

            return getProfileByAccountID(userAcount.getId());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean removeUser(Integer userId){

        int rowsAffected = jdbcTemplate.update("DELETE FROM useraccount WHERE id=?;",(userId));
        jdbcTemplate.update("DELETE FROM userprofile WHERE useraccountid=?",(userId));
        return rowsAffected == 1;
    }

    @Override
    public Integer removeUserResponses(Integer userId){
        return jdbcTemplate.update("CALL removeUserRatings(?)", (userId));
    }
}
