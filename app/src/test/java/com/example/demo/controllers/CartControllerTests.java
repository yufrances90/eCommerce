package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTests {

    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    @Test
    public void addToCartWithFailure() {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("sdfdf");
        modifyCartRequest.setItemId(99L);
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> cartResponseEntity =
                this.cartController.addTocart(modifyCartRequest);

        Assertions.assertThat(cartResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void addToCart() {

        Cart cart = this.getCart();

        Assertions.assertThat(cart).isNotNull();

        given(this.cartService.addToCart(any())).willReturn(cart);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("sdfdf");
        modifyCartRequest.setItemId(99L);
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> cartResponseEntity =
                this.cartController.addTocart(modifyCartRequest);

        Assertions.assertThat(cartResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);
    }

    @Test
    public void removeFromCartWithFailure() {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("sdfdf");
        modifyCartRequest.setItemId(99L);
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> cartResponseEntity =
                this.cartController.removeFromcart(modifyCartRequest);

        Assertions.assertThat(cartResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void removeFromCart() {

        Cart cart = this.getCart();

        Assertions.assertThat(cart).isNotNull();

        given(this.cartService.removeFromCart(any())).willReturn(cart);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("sdfdf");
        modifyCartRequest.setItemId(99L);
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> cartResponseEntity =
                this.cartController.removeFromcart(modifyCartRequest);

        Assertions.assertThat(cartResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);
    }

    private Cart getCart() {

        Item item = new Item();

        item.setName("product");
        item.setPrice(new BigDecimal(4));

        Cart cart = new Cart();

        cart.addItem(item);

        return cart;
    }
}
