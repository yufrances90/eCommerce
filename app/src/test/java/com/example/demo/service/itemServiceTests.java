package com.example.demo.service;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class itemServiceTests {

    private Item item;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() {

        Item item = new Item();

        item.setName("product");
        item.setPrice(BigDecimal.TEN);
        item.setDescription("this is a test");

        this.item = this.itemRepository.save(item);
    }

    @Test
    public void getItems() {

        List<Item> items = this.itemService.getItems();

        Assertions.assertThat(items.size()).isNotZero();
    }

    @Test
    public void getItemByIdWithNonexistItemId() {

        Item item = this.itemService.getItemById(100L);

        Assertions.assertThat(item).isNull();
    }

    @Test
    public void getItemById() {

        Item item = this.itemService.getItemById(this.item.getId());

        Assertions.assertThat(item).isNotNull();

        Assertions.assertThat(item.getName()).isEqualTo(this.item.getName());
        Assertions.assertThat(item.getDescription()).isEqualTo(
                this.item.getDescription());
    }

    @Test
    public void findByItemName() {

        List<Item> items = this.itemService.findByItemName(this.item.getName());

        Assertions.assertThat(items.size()).isNotZero();
    }
}
