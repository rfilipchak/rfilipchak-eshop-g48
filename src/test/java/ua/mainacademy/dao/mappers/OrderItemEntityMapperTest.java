package ua.mainacademy.dao.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.OrderItemEntity;
import ua.mainacademy.model.OrderItem;
import ua.mainacademy.prototype.OrderItemPrototype;

import static org.junit.jupiter.api.Assertions.*;
import static ua.mainacademy.prototype.OrderItemPrototype.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderItemEntityMapperTest {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private OrderEntityMapper orderMapper = new OrderEntityMapper(userEntityMapper);
    @Autowired
    private ItemEntityMapper itemMapper;
    @Autowired
    private OrderItemEntityMapper mapper = new OrderItemEntityMapper(orderMapper, itemMapper);

    @Test
    void toOrderItemTest() {
        OrderItem expected = aNewOrderItem();
        OrderItemEntity orderItemEntity = aNewOrderItemEntity();

        Assertions.assertThat(mapper.toOrderItem(orderItemEntity))
                .isEqualTo(expected);
    }

    @Test
    void testToOrderItemTest() {
        OrderItem orderItem = aNewOrderItem();
        OrderItemEntity expected = aNewOrderItemEntity();

        Assertions.assertThat(mapper.toOrderItemEntity(orderItem))
                .isEqualTo(expected);
    }
}