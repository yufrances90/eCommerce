package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void submitOrderWithNonexistUsername() {

        UserOrder userOrder = this.orderService.submitOrder("hello");

        Assertions.assertThat(userOrder).isNull();
    }

    @Test
    public void submitOrder() {

        this.saveUser("hello11");

        UserOrder userOrder = this.orderService.submitOrder("hello11");

        Assertions.assertThat(userOrder).isNotNull();

        Assertions.assertThat(userOrder.getUser()).isNotNull();
        Assertions.assertThat(userOrder.getUser().getUsername()).isEqualTo(
                "hello11");

        List<Item> items = userOrder.getItems();

        Assertions.assertThat(items).isNotNull();
        Assertions.assertThat(items).isNotEmpty();

        Item item = items.get(0);

        Assertions.assertThat(item.getName()).isEqualTo("product");
    }

    @Test
    public void getOrdersForUserWithNonexistUser() {

        List<UserOrder> userOrders = this.orderService.getOrdersForUser(
             "hello");

        Assertions.assertThat(userOrders).isNull();
    }

    @Test
    public void getOrdersForUser() {

        this.saveUser("hello12");

        this.orderService.submitOrder("hello12");

        List<UserOrder> userOrders = this.orderService.getOrdersForUser(
                "hello12");

        Assertions.assertThat(userOrders).isNotNull();

        Assertions.assertThat(userOrders).isNotEmpty();

        UserOrder userOrder = userOrders.get(0);

        User user = userOrder.getUser();

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getUsername()).isEqualTo(
                "hello12");

        Assertions.assertThat(user.getPassword()).isEqualTo("sasesfsdf");
    }

    public void saveUser(String username) {

        User user = new User();

        user.setUsername(username);
        user.setPassword("sasesfsdf");

        this.userRepository.save(user);

        Item item = new Item();

        item.setName("product");
        item.setPrice(BigDecimal.TEN);
        item.setDescription("this is a test");

        this.itemRepository.save(item);

        List<Item> items = this.itemRepository.findByName(item.getName());

        Cart cart = user.getCart();

        cart.setItems(items);

        this.cartRepository.save(cart);
    }
}
