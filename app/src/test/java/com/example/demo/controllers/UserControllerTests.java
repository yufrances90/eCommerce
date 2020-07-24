package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {

        User user1 = this.getUser1();

        given(this.userService.findUserById(1L)).willReturn(user1);

        given(this.userService.findUserByUsername("sdfdfsdf")).willReturn(user1);
    }

    @Test
    public void findByIdWithNonexistUserId() {

        ResponseEntity<User> userResponseEntity =
                this.userController.findById(2L);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findById() {

        ResponseEntity<User> userResponseEntity =
                this.userController.findById(1L);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        User user = userResponseEntity.getBody();

        Assertions.assertThat(user).isNotNull();

        User expectedUser = this.getUser1();

        Assertions.assertThat(user.getUsername()).isEqualTo(expectedUser.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo(expectedUser.getPassword());
    }

    @Test
    public void findByUserNameWithNonexistUsername() {

        ResponseEntity<User> userResponseEntity =
                this.userController.findByUserName("hello");

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void findByUserName() {

        ResponseEntity<User> userResponseEntity =
                this.userController.findByUserName("sdfdfsdf");

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);

        User user = userResponseEntity.getBody();

        Assertions.assertThat(user).isNotNull();

        User expectedUser = this.getUser1();

        Assertions.assertThat(user.getUsername()).isEqualTo(expectedUser.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo(expectedUser.getPassword());
    }

    @Test
    public void createUserWithInvalidPassword() {

        given(this.userService.createUser(any())).willReturn(null);

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setConfirmedPassword("Sdf");
        createUserRequest.setPassword("Sdf");
        createUserRequest.setUsername("Sdfsdfe");

        ResponseEntity<User> userResponseEntity =
                this.userController.createUser(createUserRequest);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser() {

        User user1 = this.getUser1();

        user1.setId(1L);

        given(this.userService.createUser(any())).willReturn(user1);

        CreateUserRequest createUserRequest = this.getValidCreateUserRequest();

        ResponseEntity<User> userResponseEntity =
                this.userController.createUser(createUserRequest);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private CreateUserRequest getValidCreateUserRequest() {

        User user1 = this.getUser1();

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setUsername(user1.getUsername());
        createUserRequest.setPassword(user1.getPassword());
        createUserRequest.setConfirmedPassword(user1.getPassword());

        return createUserRequest;
    }

    private User getUser1() {
        User user = new User();

        user.setUsername("sdfdfsdf");
        user.setPassword("sdfsdf1212");

        return user;
    }
}
