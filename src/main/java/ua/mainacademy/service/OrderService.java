package ua.mainacademy.service;

import ua.mainacademy.model.Order;

import java.util.List;

public interface OrderService {

    Order createNewOrGetOpenOrderForUser(long userId);

    Order closeOrderForUser(long userId);

    Order findActiveOrdersForUser(long userId);

    List<Order> historyOfOrdersForUser(long userId);

    void deleteById(long id);
}
