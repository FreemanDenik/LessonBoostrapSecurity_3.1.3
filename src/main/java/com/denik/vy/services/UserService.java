package com.denik.vy.services;

import com.denik.vy.models.User;
import com.denik.vy.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
    public void create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void edit(User user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(()-> new NullPointerException("not find user by: " + user.getId()));
        findUser.setUsername(user.getUsername());
        findUser.setEmail(user.getEmail());
        findUser.setPassword(passwordEncoder.encode(user.getPassword()));
        findUser.setRoles(user.getRoles());
        userRepository.save(findUser);
    }
    public void delete(long id){
        userRepository.deleteById(id);
    }
    public List<User> users(){
        return userRepository.findAll();
    }
}
