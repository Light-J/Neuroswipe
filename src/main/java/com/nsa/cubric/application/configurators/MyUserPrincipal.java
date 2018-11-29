package com.nsa.cubric.application.configurators;

import com.nsa.cubric.application.domain.Account;
//import com.nsa.cubric.application.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class MyUserPrincipal implements UserDetails {
    private Account user;
    private List<String> userRoles;

    public MyUserPrincipal(Account user, List<String> userRoles) {
        this.user = user;
        this.userRoles = userRoles;
    }

    public MyUserPrincipal() {
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //

    public Account getUser() {
        return user;
    }
}

