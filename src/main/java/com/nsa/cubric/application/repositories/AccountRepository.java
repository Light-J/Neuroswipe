package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.queryForObject(
                "select email from useraccount WHERE email = ?",
                new Object[]{email},accountMapper);

    }

    @Override
    public void insertNewAccount(AccountDTO account){
        jdbcTemplate.update(
                "INSERT into useraccount (email, password, role) values (?,?,?)",
                account.getEmail(), account.getPassword(), "user");
    }
}
