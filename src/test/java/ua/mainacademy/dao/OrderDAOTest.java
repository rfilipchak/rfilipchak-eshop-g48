package ua.mainacademy.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.OrderEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrderEntityWithoutId;
import static ua.mainacademy.prototype.UserPrototype.aNewUserEntity;

@SpringBootTest
@ActiveProfiles("test")
class OrderDAOTest {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;

    private OrderEntity savedOrder;


    @BeforeEach
    void setUp() {
        savedOrder = orderDAO.save(aNewOrderEntityWithoutId());
    }

    @AfterEach
    void tearDown() {
        orderDAO.delete(savedOrder);
        userDAO.deleteAll();
    }

    @Test
    void shouldSaveOrderTest() {
        assertThat(savedOrder.getId())
                .isNotNull();
    }

    @Test
    void findAllTest(){
        OrderEntity newOrder = orderDAO.save(aNewOrderEntityWithoutId()
                .toBuilder()
                .creationTime(54321L)
                .build());

        List<OrderEntity> orders = orderDAO.findAll();

        assertThat(orders.size())
                .isEqualTo(2);
        assertThat(orders.get(0).getId())
                .isEqualTo(savedOrder.getId());
        assertThat(orders.get(1).getId())
                .isEqualTo(newOrder.getId());

        orderDAO.delete(newOrder);
    }

    @Test
    void findAllByUserAndStatusTest(){
        List<OrderEntity> orders = orderDAO.findAllByUserAndStatus(savedOrder.getUser(), savedOrder.getStatus());

        assertThat(orders.size())
                .isEqualTo(1);
        assertThat(orders.get(0))
                .isEqualTo(savedOrder);
    }

    @Test
    void findFirstByUserAndStatusTest(){
        OrderEntity order = orderDAO.findFirstByUserAndStatus(savedOrder.getUser(),
                savedOrder.getStatus()).get();

        assertThat(order)
                .isEqualTo(savedOrder);
    }
}