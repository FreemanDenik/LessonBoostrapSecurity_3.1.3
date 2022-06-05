package com.denik.vy.services;

import com.denik.vy.helper.MyObjects;
import com.denik.vy.models.Role;
import com.denik.vy.models.User;
import com.denik.vy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserSecurityService implements UserDetailsService {
    final UserRepository userRepository;
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = MyObjects.requireNonNullElseThrow(userRepository.findUserByUsername(username), new UsernameNotFoundException("not find user"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> grantedAuthorities(List<Role> roles) {
        return roles.stream().map( w -> new SimpleGrantedAuthority(w.getName())).collect(Collectors.toList());
    }
}
