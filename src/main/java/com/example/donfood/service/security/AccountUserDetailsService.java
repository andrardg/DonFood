package com.example.donfood.service.security;

import com.example.donfood.model.Account;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@RequiredArgsConstructor
@Slf4j
@Profile("mysql")
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null)
            throw new UsernameNotFoundException("Username: " + username);

        log.info("loadUserByUsername for " + account.getEmail());
        UserDetails user = new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPasswordEncoded(),
                true, true,true, true, getAuthorities(account.getAccessRights()));
        return user;

    }
    private Collection<? extends GrantedAuthority> getAuthorities(Right right) {
        Set<Right> r = new HashSet<>();
        r.add(right);

        Set<GrantedAuthority> roles = new HashSet<>();
        r.forEach((role) -> { roles.add(new SimpleGrantedAuthority(role.toString())); });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;

    }

}
