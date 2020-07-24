package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void loadUserByUsernameNotFound() {

        String username = "hello";

        UserDetails userDetails =
                this.userService.loadUserByUsername(username);

        Assertions.assertThat(userDetails).isNull();
    }

    @Test
    public void createUserWithInvalidPasswordLength() {

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setPassword("hello");

        User user = this.userService.createUser(createUserRequest);

        Assertions.assertThat(user).isNull();
    }

    @Test
    public void createUserWithUnmatchedPassword() {

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setPassword("helloworld");
        createUserRequest.setConfirmedPassword("hello");

        User user = this.userService.createUser(createUserRequest);

        Assertions.assertThat(user).isNull();
    }

    @Test
    public void createUser() {

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setUsername("hello");
        createUserRequest.setPassword("helloworld");
        createUserRequest.setConfirmedPassword("helloworld");

        User user = this.userService.createUser(createUserRequest);

        Assertions.assertThat(user).isNotNull();

        Cart cart = user.getCart();

        Assertions.assertThat(cart).isNotNull();

        Long cartId = cart.getId();

        Assertions.assertThat(cartId).isNotNull();

        Cart savedCart = this.cartRepository.getOne(cartId);

        Assertions.assertThat(savedCart).isNotNull();

        User user1 = savedCart.getUser();

        Assertions.assertThat(user1.getId()).isEqualTo(user.getId());
        Assertions.assertThat(user1.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(user1.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void loadUserByUsername() {

        User user = new User();

        user.setUsername("hello1");
        user.setPassword("helloworld");
        user.setCart(new Cart());

        this.userRepository.save(user);

        UserDetails userDetails = this.userService.loadUserByUsername(
                user.getUsername());

        Assertions.assertThat(userDetails).isNotNull();

        Assertions.assertThat(userDetails.getUsername()).isEqualTo(
                user.getUsername());
        Assertions.assertThat(userDetails.getPassword()).isEqualTo(
                user.getPassword());
    }

    @Test
    public void findUserByIdWithoutUserFound() {

        User user = this.userService.findUserById(199);

        Assertions.assertThat(user).isNull();
    }

    @Test
    public void findUserById() {

        User user = new User();

        user.setUsername("hello2");
        user.setPassword("helloworld");
        user.setCart(new Cart());

        User savedUser = this.userRepository.save(user);

        User user1 = this.userService.findUserById(savedUser.getId());

        Assertions.assertThat(user1).isNotNull();

        Assertions.assertThat(user1.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(user1.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void findUserByUsername() {

        User user = new User();

        user.setUsername("hello3");
        user.setPassword("helloworld");
        user.setCart(new Cart());

        this.userRepository.save(user);

        User savedUser =
                this.userService.findUserByUsername(user.getUsername());

        Assertions.assertThat(savedUser).isNotNull();

        Assertions.assertThat(savedUser.getUsername()).isEqualTo(
                user.getUsername());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(
                user.getPassword());
    }
}
