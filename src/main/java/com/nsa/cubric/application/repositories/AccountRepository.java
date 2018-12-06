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

    @Autowired
    public AccountRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        accountMapper = (rs, i) -> new Account(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
        );
    }

    @Override
    public Account findByEmail(String email){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, email, password, role from useraccounts WHERE email = ?",
                    new Object[]{email},accountMapper);

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
    public List<Account> getAll(){
        return jdbcTemplate.query(
                "SELECT id, password, email, role FROM useraccounts",
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
    public boolean removeUser(Long userId){

        int rowsAffected = jdbcTemplate.update("DELETE FROM useraccounts WHERE id=?;",(userId));
        jdbcTemplate.update("DELETE FROM userprofiles WHERE useraccountid=?",(userId));
        return rowsAffected == 1;
    }

    @Override
    public Long removeUserResponses(Long userId){
        return Long.valueOf(jdbcTemplate.update("CALL removeUserRatings(?)", (userId)));
    }
}
