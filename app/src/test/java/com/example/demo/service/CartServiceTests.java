package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @Test
    public void addToCart() {

        ModifyCartRequest request = new ModifyCartRequest();

        Cart cart = this.cartService.addToCart(request);

        Assertions.assertThat(cart).isNull();
    }
}
