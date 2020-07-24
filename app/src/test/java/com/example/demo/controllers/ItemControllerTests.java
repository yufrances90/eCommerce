package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.service.ItemService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTests {

    private Item item;

    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    @Before
    public void setUp() {

        given(this.itemService.getItems()).willReturn(this.getItems());

        given(this.itemService.getItemById(1L)).willReturn(this.item);
        given(this.itemService.getItemById(99L)).willReturn(null);

        given(this.itemService.findByItemName("product")).willReturn(
                this.getItems());
        given(this.itemService.findByItemName("product1")).willReturn(
                null);
        given(this.itemService.findByItemName("produc")).willReturn(
                new ArrayList<>());
    }

    @Test
    public void getAllItems() {

        ResponseEntity<List<Item>> listResponseEntity =
                this.itemController.getItems();

        Assertions.assertThat(listResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);

        List<Item> items = listResponseEntity.getBody();

        Assertions.assertThat(items.size()).isEqualTo(1);
    }

    @Test
    public void getItemByIdWithNonexistId() {

       ResponseEntity<Item> itemResponseEntity =
               this.itemController.getItemById(99L);

        Assertions.assertThat(itemResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void getItemById() {

        ResponseEntity<Item> itemResponseEntity =
                this.itemController.getItemById(1L);

        Assertions.assertThat(itemResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);

        Item item = itemResponseEntity.getBody();

        Assertions.assertThat(item.getName()).isEqualTo(this.item.getName());
        Assertions.assertThat(item.getDescription()).isEqualTo(
                this.item.getDescription());
    }

    @Test
    public void getItemsByNameWithNullItems() {

        ResponseEntity<List<Item>> listResponseEntity =
                this.itemController.getItemsByName("product1");

        Assertions.assertThat(listResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void getItemsByNameWithEmptyItems() {

        ResponseEntity<List<Item>> listResponseEntity =
                this.itemController.getItemsByName("produc");

        Assertions.assertThat(listResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void getItemsByName() {

        ResponseEntity<List<Item>> listResponseEntity =
                this.itemController.getItemsByName("product");

        Assertions.assertThat(listResponseEntity.getStatusCode()).isEqualTo(
                HttpStatus.OK);

        List<Item> items = listResponseEntity.getBody();

        Assertions.assertThat(items).isNotNull();
        Assertions.assertThat(items.size()).isEqualTo(1);
    }

    private List<Item> getItems() {

        List<Item> items = new ArrayList<>();

        this.item = new Item();

        this.item.setName("product");
        this.item.setPrice(BigDecimal.TEN);
        this.item.setDescription("this is a test");

        items.add(item);

        return items;
    }
}
