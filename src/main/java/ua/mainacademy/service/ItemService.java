package ua.mainacademy.service;

import ua.mainacademy.model.Item;

import java.util.List;

public interface ItemService {

    Item create(Item item);

    Item update(Item item);

    Item findOneById(long id);

    List<Item> findAll();

    void deleteById(long id);
}
