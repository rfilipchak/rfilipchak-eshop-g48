package ua.mainacademy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.OrderDAO;
import ua.mainacademy.dao.UserDAO;
import ua.mainacademy.dao.entity.OrderEntity;
import ua.mainacademy.model.Order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.mainacademy.model.OrderStatus.CLOSED;
import static ua.mainacademy.model.OrderStatus.OPEN;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrder;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrderEntity;
import static ua.mainacademy.prototype.UserPrototype.aNewUserEntity;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService service;
    @MockBean
    private OrderDAO orderDAO;
    @MockBean
    private UserDAO userDAO;

    @Test
    void createNewOrderForUserTest() {
        Order expectedOrder = aNewOrder();

        when(userDAO.findById(expectedOrder.getUser().getId())).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findFirstByUserAndStatus(aNewUserEntity(), OPEN)).thenReturn(Optional.empty());
        when(orderDAO.save(any(OrderEntity.class))).thenReturn(aNewOrderEntity());

        Order createdOrder = service.createNewOrGetOpenOrderForUser(expectedOrder.getUser().getId());

        assertThat(createdOrder).isEqualTo(expectedOrder);
    }

    @Test
    void openExistingOrderForUserTest() {
        Order expectedOrder = aNewOrder();

        when(userDAO.findById(expectedOrder.getUser().getId())).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findFirstByUserAndStatus(aNewUserEntity(), OPEN)).thenReturn(Optional.of(aNewOrderEntity()));

        Order createdOrder = service.createNewOrGetOpenOrderForUser(expectedOrder.getUser().getId());

        assertThat(createdOrder).isEqualTo(expectedOrder);
    }

    @Test
    void failedCreateNewOrderOnUserValidationTest() {
        when(userDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.createNewOrGetOpenOrderForUser(1L);
        });
    }

    @Test
    void closeOrderForUserTest() {
        long userId = 1L;
        Order expectedOrder = aNewOrder().toBuilder().status(CLOSED).build();

        when(userDAO.findById(userId)).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findFirstByUserAndStatus(aNewUserEntity(), OPEN)).thenReturn(Optional.of(aNewOrderEntity()));
        when(orderDAO.save(aNewOrderEntity().toBuilder().status(CLOSED).build()))
                .thenReturn(aNewOrderEntity().toBuilder().status(CLOSED).build());

        Order closedOrder = service.closeOrderForUser(userId);

        assertThat(closedOrder).isEqualTo(expectedOrder);
    }

    @Test
    void failedOrderStatusUpdatingTest() {
        long userId = 1L;

        when(userDAO.findById(userId)).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findFirstByUserAndStatus(aNewUserEntity(), OPEN)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.closeOrderForUser(userId);
        });
    }

    @Test
    void failedOrderStatusUpdatingOnUSerValidationTest() {
        long userId = 1L;

        when(userDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.closeOrderForUser(userId);
        });
    }

    @Test
    void findActiveOrdersForUserTest() {
        long userId = 1L;

        when(userDAO.findById(userId)).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findFirstByUserAndStatus(aNewUserEntity(), OPEN)).thenReturn(Optional.of(aNewOrderEntity()));

        Order activeOrdersForUser = service.findActiveOrdersForUser(userId);

        assertThat(activeOrdersForUser).isEqualTo(aNewOrder());
    }

    @Test
    void failedFindActiveOrdersForEmptyTest() {
        long userId = 1L;

        when(userDAO.findById(userId)).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findFirstByUserAndStatus(aNewUserEntity(), OPEN)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.findActiveOrdersForUser(userId);
        });
    }

    @Test
    void failedFindActiveOrdersOnUserValidationTest() {
        long userId = 1L;

        when(userDAO.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.findActiveOrdersForUser(userId);
        });
    }

    @Test
    void historyOfOrdersForUserTest() {
        long userId = 1L;
        Order expected = aNewOrder().toBuilder().status(CLOSED).build();

        when(userDAO.findById(userId)).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findAllByUserAndStatus(aNewUserEntity(), CLOSED))
                .thenReturn(List.of(aNewOrderEntity().toBuilder().status(CLOSED).build()));

        List<Order> closedOrders = service.historyOfOrdersForUser(userId);

        assertThat(closedOrders).isEqualTo(List.of(expected));
    }

    @Test
    void emptyHistoryOfOrdersForUserTest() {
        long userId = 1L;

        when(userDAO.findById(userId)).thenReturn(Optional.of(aNewUserEntity()));
        when(orderDAO.findAllByUserAndStatus(aNewUserEntity(), CLOSED))
                .thenReturn(Collections.emptyList());

        List<Order> closedOrders = service.historyOfOrdersForUser(userId);

        assertThat(closedOrders).isEqualTo(Collections.emptyList());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(1L);
        verify(orderDAO, times(1)).deleteById(1L);
    }
}