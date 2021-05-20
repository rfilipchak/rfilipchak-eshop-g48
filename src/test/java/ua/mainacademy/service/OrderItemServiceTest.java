package ua.mainacademy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.dao.OrderItemDAO;
import ua.mainacademy.dao.mappers.OrderEntityMapper;
import ua.mainacademy.dao.mappers.OrderItemEntityMapper;
import ua.mainacademy.model.OrderItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.mainacademy.prototype.ItemPrototype.aNewItemEntity;
import static ua.mainacademy.prototype.OrderItemPrototype.*;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrder;

@SpringBootTest
@ActiveProfiles("test")
class OrderItemServiceTest {

    @Autowired
    private OrderItemService orderItemService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderItemDAO orderItemDAO;
    @MockBean
    private ItemDAO itemDAO;

    @Test
    void createNewOrderItemTest() {
        long itemId = 1L;
        long userId = 1L;
        int amount = 10;

        when(itemDAO.findById(itemId)).thenReturn(Optional.of(aNewItemEntity()));
        when(orderService.createNewOrGetOpenOrderForUser(userId)).thenReturn(aNewOrder());
        when(orderItemDAO.save(aNewOrderItemEntityWithoutId())).thenReturn(aNewOrderItemEntity());

        OrderItem newOrderItem = orderItemService.createNewOrderItem(userId, itemId, amount);

        assertThat(newOrderItem).isEqualTo(aNewOrderItem());
    }

    @Test
    void failedCreationNewOrderItemForZeroTest() {
        long itemId = 1L;
        long userId = 1L;
        int amount = 0;

        when(itemDAO.findById(itemId)).thenReturn(Optional.of(aNewItemEntity()));

        assertThrows(RuntimeException.class, () -> {
            orderItemService.createNewOrderItem(userId, itemId, amount);
        });
    }

    @Test
    void failedCreationNewOrderItemForEmptyItemTest() {
        long itemId = 1L;
        long userId = 1L;
        int amount = 10;

        when(itemDAO.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderItemService.createNewOrderItem(userId, itemId, amount);
        });
    }

    @Test
    void updateOrderItemAmountByOrderItemIdTest() {
        long orderItemId = 1L;
        int amount = 11;
        OrderItem expected = aNewOrderItem().toBuilder().amount(amount).build();

        when(orderItemDAO.findById(orderItemId)).thenReturn(Optional.of(aNewOrderItemEntity()));
        when(orderItemDAO.save(aNewOrderItemEntity().toBuilder().amount(amount).build()))
                .thenReturn(aNewOrderItemEntity().toBuilder().amount(amount).build());

        OrderItem updatedOrderItem = orderItemService.updateOrderItemAmountByOrderItemId(orderItemId, amount);

        assertThat(updatedOrderItem).isEqualTo(expected);
    }

    @Test
    void failedUpdatingByEmptyOrderItemTest() {
        long orderItemId = 1L;
        int amount = 11;

        when(orderItemDAO.findById(orderItemId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderItemService.updateOrderItemAmountByOrderItemId(orderItemId, amount);
        });
    }

    @Test
    void failedUpdatingByZeroAmountTest() {
        long orderItemId = 1L;
        int amount = 0;

        when(orderItemDAO.findById(orderItemId)).thenReturn(Optional.of(aNewOrderItemEntity()));

        assertThrows(RuntimeException.class, () -> {
            orderItemService.updateOrderItemAmountByOrderItemId(orderItemId, amount);
        });
    }

    @Test
    void findAllTest() {
        long orderId = 1L;
        List<OrderItem> expected = List.of(aNewOrderItem(), aNewOrderItem().toBuilder().id(2L).build());

        when(orderItemDAO.findAllByOrderId(orderId)).thenReturn(List.of(aNewOrderItemEntity(), aNewOrderItemEntity().toBuilder().id(2L).build()));

        List<OrderItem> allOrderItems = orderItemService.findAll(orderId);

        assertThat(allOrderItems).isEqualTo(expected);
    }

    @Test
    void emptyResultForFindAllTest() {
        long orderId = 1L;

        when(orderItemDAO.findAllByOrderId(orderId)).thenReturn(Collections.emptyList());

        List<OrderItem> allOrderItems = orderItemService.findAll(orderId);

        assertThat(allOrderItems).isEqualTo(Collections.emptyList());
    }

    @Test
    void deleteOrderItemByItemIdTest() {
        long orderItemId = 1L;
        long orderId = 1L;

        when(orderItemDAO.findById(orderItemId)).thenReturn(Optional.of(aNewOrderItemEntity()));
        when(orderItemDAO.findAllByOrderId(orderId)).thenReturn(List.of(aNewOrderItemEntity()));

        orderItemService.deleteOrderItemById(orderItemId);

        verify(orderItemDAO, times(1)).deleteById(orderItemId);
        verify(orderService, times(0)).deleteById(orderId);
    }

    @Test
    void deleteOrderItemByItemIdWithOrderDeletingTest() {
        long orderItemId = 1L;
        long orderId = 1L;

        when(orderItemDAO.findById(orderItemId)).thenReturn(Optional.of(aNewOrderItemEntity()));
        when(orderItemDAO.findAllByOrderId(orderId)).thenReturn(Collections.emptyList());

        orderItemService.deleteOrderItemById(orderItemId);

        verify(orderItemDAO, times(1)).deleteById(orderItemId);
        verify(orderService, times(1)).deleteById(orderId);
    }
}