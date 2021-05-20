package ua.mainacademy.dao.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.OrderEntity;
import ua.mainacademy.model.Order;
import ua.mainacademy.prototype.OrderPrototype;

import static ua.mainacademy.prototype.OrderPrototype.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderEntityMapperTest {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private OrderEntityMapper mapper = new OrderEntityMapper(userEntityMapper);

    @Test
    void toOrderTest() {
        Order expected = aNewOrder();
        OrderEntity orderEntity = aNewOrderEntity();

        Assertions.assertThat(mapper.toOrder(orderEntity)).isEqualTo(expected);
    }

    @Test
    void toOrderEntityTest() {
        Order order = aNewOrder();
        OrderEntity expected = aNewOrderEntity();

        Assertions.assertThat(mapper.toOrderEntity(order)).isEqualTo(expected);
    }
}