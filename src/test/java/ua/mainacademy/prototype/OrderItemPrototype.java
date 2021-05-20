package ua.mainacademy.prototype;

import ua.mainacademy.dao.entity.OrderItemEntity;
import ua.mainacademy.model.OrderItem;

import static ua.mainacademy.prototype.ItemPrototype.*;
import static ua.mainacademy.prototype.OrderPrototype.*;

public class OrderItemPrototype {

    public static OrderItem aNewOrderItem(){
        return OrderItem.builder()
                .id(1L)
                .amount(10)
                .item(aNewItem())
                .order(aNewOrder())
                .build();
    }

    public static OrderItemEntity aNewOrderItemEntity(){
        return OrderItemEntity.builder()
                .id(1L)
                .amount(10)
                .item(aNewItemEntity())
                .order(aNewOrderEntity())
                .build();
    }

    public static OrderItemEntity aNewOrderItemEntityWithoutId(){
        return OrderItemEntity.builder()
                .amount(10)
                .item(aNewItemEntity())
                .order(aNewOrderEntity())
                .build();
    }
}
