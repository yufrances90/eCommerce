package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTests {

    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @Before
    public void setUp() {

        given(this.orderService.submitOrder("hello1")).willReturn(
                this.getUserOrder());

        given(this.orderService.getOrdersForUser("hello1")).willReturn(Arrays.asList(this.getUserOrder()));

        given(this.orderService.getOrdersForUser("hello")).willReturn(null);
    }

    @Test
    public void submitOrderWithNonexistUser() {

        ResponseEntity<UserOrder> orderResponseEntity =
                this.orderController.submit("hello");

        Assertions.assertThat(orderResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void submitOrder() {

        ResponseEntity<UserOrder> orderResponseEntity =
                this.orderController.submit("hello1");

        Assertions.assertThat(orderResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);

        Assertions.assertThat(orderResponseEntity.getBody()).isNotNull();
    }

    @Test
    public void getOrdersForUserWithNonexistUser() {

        ResponseEntity<List<UserOrder>> userOrderResponseEntity =
                this.orderController.getOrdersForUser("hello");

        Assertions.assertThat(userOrderResponseEntity).isNotNull();

        Assertions.assertThat(userOrderResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getOrdersForUser() {

        ResponseEntity<List<UserOrder>> userOrderResponseEntity =
                this.orderController.getOrdersForUser("hello1");

        Assertions.assertThat(userOrderResponseEntity).isNotNull();

        Assertions.assertThat(userOrderResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<UserOrder> userOrders = userOrderResponseEntity.getBody();

        Assertions.assertThat(userOrders).isNotNull();
        Assertions.assertThat(userOrders).isNotEmpty();
    }

    private UserOrder getUserOrder() {

        User user = new User();

        user.setUsername("hello1");
        user.setPassword("Asdfsdfasdf");

        UserOrder userOrder = new UserOrder();

        userOrder.setId(1L);
        userOrder.setTotal(BigDecimal.TEN);
        userOrder.setUser(user);

        return userOrder;
    }
}
