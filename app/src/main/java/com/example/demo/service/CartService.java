package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Cart addToCart(ModifyCartRequest request) {

        String username = request.getUsername();

        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        Long itemId = request.getItemId();

        Optional<Item> optionalItem = this.itemRepository.findById(itemId);

        if (!optionalItem.isPresent()) {
            return null;
        }

        Item item = optionalItem.get();

        Cart cart = user.getCart();

        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item));

        Cart savedCart = cartRepository.save(cart);

        return savedCart;
    }
}
