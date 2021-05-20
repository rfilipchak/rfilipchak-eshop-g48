package ua.mainacademy.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.OrderItemEntity;

import java.util.List;

import static ua.mainacademy.prototype.ItemPrototype.aNewItemEntityWithoutId;
import static ua.mainacademy.prototype.OrderItemPrototype.aNewOrderItemEntityWithoutId;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrderEntityWithoutId;

@SpringBootTest
@ActiveProfiles("test")
class OrderItemDAOTest {

    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private UserDAO userDAO;

    private OrderItemEntity savedOrderItem;

    @BeforeEach
    void setUp() {
        OrderItemEntity entity = aNewOrderItemEntityWithoutId()
                .toBuilder()
                .order(aNewOrderEntityWithoutId())
                .item(aNewItemEntityWithoutId())
                .build();
        savedOrderItem = orderItemDAO.save(entity);
    }

    @AfterEach
    void tearDown() {
        orderItemDAO.delete(savedOrderItem);
        itemDAO.deleteAll();
        orderDAO.deleteAll();
        userDAO.deleteAll();
    }

    @Test
    void shouldSaveOrderItemTest() {
        Assertions.assertThat(savedOrderItem.getId())
                .isNotNull();
    }

    @Test
    void shouldFindAllByOrderId() {
        long orderId = savedOrderItem.getOrder().getId();
        Assertions.assertThat(orderItemDAO.findAllByOrderId(orderId))
                .isEqualTo(List.of(savedOrderItem));
    }
}