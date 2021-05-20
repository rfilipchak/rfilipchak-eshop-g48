package ua.mainacademy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mainacademy.model.Order;
import ua.mainacademy.model.OrderStatus;
import ua.mainacademy.service.OrderService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrder;
import static ua.mainacademy.prototype.UserPrototype.aNewUser;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @MockBean
    private OrderService service;
    private OrderController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new OrderController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void successCreateOpenOrder() throws Exception {
        Order expectedOrder = aNewOrder();
        long userId = expectedOrder.getUser().getId();

        when(service.createNewOrGetOpenOrderForUser(userId)).thenReturn(expectedOrder);
        mockMvc.perform(put("/order/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.creationTime").value(1234))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.login").value("roman123"))
                .andExpect(jsonPath("$.user.password").value("1234"))
                .andExpect(jsonPath("$.user.firstName").value("Roman"))
                .andExpect(jsonPath("$.user.lastName").value("Fill"))
                .andExpect(jsonPath("$.user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.user.phone").value("+321781"));
    }

    @Test
    void failedToCreateOpenOrderForNotExistingUser() throws Exception {
        long userId = 100L;

        when(service.createNewOrGetOpenOrderForUser(userId)).thenThrow(new RuntimeException());
        mockMvc.perform(put("/order/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failedToCreateOpenOrderForNull() throws Exception {
        Long userId = null;

        mockMvc.perform(put("/order/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successCloseOrderForUser() throws Exception {
        Order expectedOrder = aNewOrder().toBuilder().status(OrderStatus.CLOSED).build();
        long userId = expectedOrder.getUser().getId();

        when(service.closeOrderForUser(userId)).thenReturn(expectedOrder);
        mockMvc.perform(post("/order/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.creationTime").value(1234))
                .andExpect(jsonPath("$.status").value("CLOSED"))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.login").value("roman123"))
                .andExpect(jsonPath("$.user.password").value("1234"))
                .andExpect(jsonPath("$.user.firstName").value("Roman"))
                .andExpect(jsonPath("$.user.lastName").value("Fill"))
                .andExpect(jsonPath("$.user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.user.phone").value("+321781"));
    }

    @Test
    void failedToCloseOrderForUser() throws Exception {
        long userId = 1L;

        when(service.closeOrderForUser(userId)).thenThrow(new RuntimeException());
        mockMvc.perform(post("/order/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failedToCloseOrderForNull() throws Exception {
        Long userId = null;

        mockMvc.perform(post("/order/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successFindOneById() throws Exception {
        Order expectedOrder = aNewOrder();
        long userId = expectedOrder.getUser().getId();

        when(service.findActiveOrdersForUser(userId)).thenReturn(expectedOrder);
        mockMvc.perform(get("/order/open-orders/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.creationTime").value(1234))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.login").value("roman123"))
                .andExpect(jsonPath("$.user.password").value("1234"))
                .andExpect(jsonPath("$.user.firstName").value("Roman"))
                .andExpect(jsonPath("$.user.lastName").value("Fill"))
                .andExpect(jsonPath("$.user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.user.phone").value("+321781"));
    }

    @Test
    void failedToFindOneById() throws Exception {
        long userId = 1L;

        when(service.findActiveOrdersForUser(userId)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/order/open-orders/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failedToFindOneByIdForNull() throws Exception {
        Long userId = null;

        mockMvc.perform(get("/order/open-orders/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successFindAllClosedOrders() throws Exception {
        Order firstExpectedOrder = aNewOrder().toBuilder().status(OrderStatus.CLOSED).build();
        Order secondExpectedOrder = aNewOrder().toBuilder()
                .id(2L)
                .creationTime(12345L)
                .status(OrderStatus.CLOSED).build();
        long userId = secondExpectedOrder.getUser().getId();

        when(service.historyOfOrdersForUser(userId)).thenReturn(List.of(firstExpectedOrder, secondExpectedOrder));
        mockMvc.perform(get("/order/closed-orders/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].creationTime").value(1234L))
                .andExpect(jsonPath("$[0].status").value("CLOSED"))
                .andExpect(jsonPath("$[0].user.id").value(1L))
                .andExpect(jsonPath("$[0].user.login").value("roman123"))
                .andExpect(jsonPath("$[0].user.password").value("1234"))
                .andExpect(jsonPath("$[0].user.firstName").value("Roman"))
                .andExpect(jsonPath("$[0].user.lastName").value("Fill"))
                .andExpect(jsonPath("$[0].user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$[0].user.phone").value("+321781"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].creationTime").value(12345L))
                .andExpect(jsonPath("$[1].status").value("CLOSED"))
                .andExpect(jsonPath("$[1].user.id").value(1L))
                .andExpect(jsonPath("$[1].user.login").value("roman123"))
                .andExpect(jsonPath("$[1].user.password").value("1234"))
                .andExpect(jsonPath("$[1].user.firstName").value("Roman"))
                .andExpect(jsonPath("$[1].user.lastName").value("Fill"))
                .andExpect(jsonPath("$[1].user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$[1].user.phone").value("+321781"));
    }

    @Test
    void emptyResultForFindAllClosedOrders() throws Exception {
        long userId = 1L;

        when(service.historyOfOrdersForUser(userId)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/order/closed-orders/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    void failedToFindAllClosedOrdersForNull() throws Exception {
        Long userId = null;

        mockMvc.perform(get("/order/closed-orders/"+ userId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successDeleteById() throws Exception {
        long id = 1L;

        mockMvc.perform(delete("/order/"+ id)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void failedToDeleteById() throws Exception {
        Long id = null;

        mockMvc.perform(delete("/order/"+ id)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}