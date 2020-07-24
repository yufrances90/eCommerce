package com.example.demo.service;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        return this.itemRepository.findAll();
    }

    public Item getItemById(Long itemId) {
       return this.itemRepository.findById(itemId).orElse(null);
    }

    public List<Item> findByItemName(String name) {
        return this.itemRepository.findByName(name);
    }
}
