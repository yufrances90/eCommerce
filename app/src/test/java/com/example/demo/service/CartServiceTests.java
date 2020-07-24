package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

    private User user;
    private Item item;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() {

        this.saveItem();
    }

    @Test
    public void addToCartWithNonexistUser() {

        ModifyCartRequest request = new ModifyCartRequest();

        Cart cart = this.cartService.addToCart(request);

        Assertions.assertThat(cart).isNull();
    }

    @Test
    public void addToCartWithNonexisItem() {

        this.saveUser("hello01");

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setItemId(99L);
        modifyCartRequest.setUsername(this.user.getUsername());

        Cart cart = this.cartService.addToCart(modifyCartRequest);

        Assertions.assertThat(cart).isNull();
    }

    @Test
    public void addToCart() {

        this.saveUser("hello02");

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setItemId(this.item.getId());
        modifyCartRequest.setUsername(this.user.getUsername());
        modifyCartRequest.setQuantity(1);

        Cart cart = this.cartService.addToCart(modifyCartRequest);

        Assertions.assertThat(cart).isNotNull();

        Assertions.assertThat(cart.getItems().size()).isNotZero();
    }

    @Test
    public void removeFromCartWithNonexistUser() {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        Cart cart = this.cartService.removeFromCart(modifyCartRequest);

        Assertions.assertThat(cart).isNull();
    }

    @Test
    public void removeFromCart() {

        this.saveUser("hello04");

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setItemId(this.item.getId());
        modifyCartRequest.setUsername(this.user.getUsername());
        modifyCartRequest.setQuantity(1);

        Cart cart = this.cartService.addToCart(modifyCartRequest);

        Assertions.assertThat(cart).isNotNull();

        Assertions.assertThat(cart.getItems().size()).isNotZero();

        cart = this.cartService.removeFromCart(modifyCartRequest);

        Assertions.assertThat(cart.getItems().size()).isZero();
    }

    @Test
    public void removeFromCartWithNonexisItem() {

        this.saveUser("hello03");

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setItemId(99L);
        modifyCartRequest.setUsername(this.user.getUsername());

        Cart cart = this.cartService.removeFromCart(modifyCartRequest);

        Assertions.assertThat(cart).isNull();
    }

    private void saveItem() {

        Item item = new Item();

        item.setName("product");
        item.setDescription("this is a test product");

        item.setPrice(new BigDecimal(3));

        this.item = this.itemRepository.save(item);
    }

    private void saveUser(String username) {

        User user = new User();

        user.setPassword("sdfsdfsdf");
        user.setUsername(username);

        this.user = this.userRepository.save(user);
    }
}
