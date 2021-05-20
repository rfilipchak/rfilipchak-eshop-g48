package ua.mainacademy.prototype;

import ua.mainacademy.dao.entity.OrderEntity;
import ua.mainacademy.model.Order;
import ua.mainacademy.model.OrderStatus;

import static ua.mainacademy.prototype.UserPrototype.*;

public class OrderPrototype {

    public static Order aNewOrder(){
        return Order.builder()
                .id(1L)
                .status(OrderStatus.OPEN)
                .creationTime(1234L)
                .user(aNewUser())
                .build();
    }

    public static OrderEntity aNewOrderEntity(){
        return OrderEntity.builder()
                .id(1L)
                .status(OrderStatus.OPEN)
                .creationTime(1234L)
                .user(aNewUserEntity())
                .build();
    }

    public static OrderEntity aNewOrderEntityWithoutId(){
        return OrderEntity.builder()
                .status(OrderStatus.OPEN)
                .creationTime(1234L)
                .user(aNewUserEntityWithoutId())
                .build();
    }
}
