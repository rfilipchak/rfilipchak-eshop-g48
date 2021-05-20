package ua.mainacademy.service;

import ua.mainacademy.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem createNewOrderItem(long userId, long itemId, int amount);

    OrderItem updateOrderItemAmountByOrderItemId(long orderItemId, int amount);

    List<OrderItem> findAll(long orderId);

    void deleteOrderItemById(long orderItemId);
}
