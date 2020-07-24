package com.example.demo.service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public UserOrder submitOrder(String username) {

        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        UserOrder order = UserOrder.createFromCart(user.getCart());

        UserOrder savedOrder = this.orderRepository.save(order);

        return savedOrder;
    }

    public List<UserOrder> getOrdersForUser(String username) {

        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        return this.orderRepository.findByUser(user);
    }
}
