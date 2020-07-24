package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public User createUser(CreateUserRequest createUserRequest) {

        if (!this.passwordService.validateUserCreation(createUserRequest)) {
            return null;
        }

        User user = new User();

        user.setUsername(createUserRequest.getUsername());

        user.setPassword(
                this.passwordService.encodePassword(createUserRequest.getPassword()));

        Cart cart = new Cart();

        user.setCart(cart);

        userRepository.save(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        return new org.springframework.security.core.userdetails.User(
                username, user.getPassword(), Collections.emptyList());
    }

    public User findUserById(long userId) {

        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            return null;
        }

        return optionalUser.isPresent()? optionalUser.get() : null;
    }

    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
