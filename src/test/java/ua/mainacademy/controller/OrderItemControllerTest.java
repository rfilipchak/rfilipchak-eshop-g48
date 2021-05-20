package ua.mainacademy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mainacademy.model.Item;
import ua.mainacademy.model.Order;
import ua.mainacademy.model.OrderItem;
import ua.mainacademy.service.OrderItemService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ua.mainacademy.prototype.OrderItemPrototype.aNewOrderItem;
import static ua.mainacademy.prototype.OrderPrototype.aNewOrder;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrderItemControllerTest {

    @MockBean
    private OrderItemService service;
    private OrderItemController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new OrderItemController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void successCreateNewOrderItem() throws Exception {
        OrderItem expectedOrderItem = aNewOrderItem();
        long userId = 1L;
        long itemId = 1L;
        int amount = 10;

        when(service.createNewOrderItem(userId, itemId, amount)).thenReturn(expectedOrderItem);
        mockMvc.perform(put("/order-item?userId=1&itemId=1&amount=10")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(10))
                .andExpect(jsonPath("$.order.id").value(1L))
                .andExpect(jsonPath("$.order.creationTime").value(1234))
                .andExpect(jsonPath("$.order.status").value("OPEN"))
                .andExpect(jsonPath("$.order.user.id").value(1L))
                .andExpect(jsonPath("$.order.user.login").value("roman123"))
                .andExpect(jsonPath("$.order.user.password").value("1234"))
                .andExpect(jsonPath("$.order.user.firstName").value("Roman"))
                .andExpect(jsonPath("$.order.user.lastName").value("Fill"))
                .andExpect(jsonPath("$.order.user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.order.user.phone").value("+321781"))
                .andExpect(jsonPath("$.item.id").value(1L))
                .andExpect(jsonPath("$.item.price").value(12))
                .andExpect(jsonPath("$.item.name").value("item"))
                .andExpect(jsonPath("$.item.initPrice").value(10))
                .andExpect(jsonPath("$.item.itemCode").value("code"));
    }

    @Test
    void failedToCreateNewOrderItem() throws Exception {
        long userId = 1L;
        long itemId = 1L;
        int amount = 10;

        when(service.createNewOrderItem(userId, itemId, amount)).thenThrow(new RuntimeException());
        mockMvc.perform(put("/order-item?userId=1&itemId=1&amount=10")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failedToCreateNullNewOrderItem() throws Exception {

        mockMvc.perform(put("/order-item")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successUpdateOrderItemAmountById() throws Exception {
        OrderItem expectedOrderItem = aNewOrderItem();
        long orderItemId = 1L;
        int amount = 10;

        when(service.updateOrderItemAmountByOrderItemId(orderItemId, amount)).thenReturn(expectedOrderItem);
        mockMvc.perform(post("/order-item?orderItemId=1&amount=10")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(10))
                .andExpect(jsonPath("$.order.id").value(1L))
                .andExpect(jsonPath("$.order.creationTime").value(1234))
                .andExpect(jsonPath("$.order.status").value("OPEN"))
                .andExpect(jsonPath("$.order.user.id").value(1L))
                .andExpect(jsonPath("$.order.user.login").value("roman123"))
                .andExpect(jsonPath("$.order.user.password").value("1234"))
                .andExpect(jsonPath("$.order.user.firstName").value("Roman"))
                .andExpect(jsonPath("$.order.user.lastName").value("Fill"))
                .andExpect(jsonPath("$.order.user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.order.user.phone").value("+321781"))
                .andExpect(jsonPath("$.item.id").value(1L))
                .andExpect(jsonPath("$.item.price").value(12))
                .andExpect(jsonPath("$.item.name").value("item"))
                .andExpect(jsonPath("$.item.initPrice").value(10))
                .andExpect(jsonPath("$.item.itemCode").value("code"));
    }

    @Test
    void failedToUpdateOrderItemAmountById() throws Exception {
        long orderItemId = 1L;
        int amount = 10;

        when(service.updateOrderItemAmountByOrderItemId(orderItemId, amount)).thenThrow(new RuntimeException());
        mockMvc.perform(post("/order-item?orderItemId=1&amount=10")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failedToUpdateForNullOrderItemAmountById() throws Exception {

        mockMvc.perform(post("/order-item")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successFindOrderItemAmountById() throws Exception {
        OrderItem expectedOrderItem = aNewOrderItem();
        long orderId = 1L;

        when(service.findAll(orderId)).thenReturn(List.of(expectedOrderItem));
        mockMvc.perform(get("/order-item/"+ orderId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value(10))
                .andExpect(jsonPath("$[0].order.id").value(1L))
                .andExpect(jsonPath("$[0].order.creationTime").value(1234))
                .andExpect(jsonPath("$[0].order.status").value("OPEN"))
                .andExpect(jsonPath("$[0].order.user.id").value(1L))
                .andExpect(jsonPath("$[0].order.user.login").value("roman123"))
                .andExpect(jsonPath("$[0].order.user.password").value("1234"))
                .andExpect(jsonPath("$[0].order.user.firstName").value("Roman"))
                .andExpect(jsonPath("$[0].order.user.lastName").value("Fill"))
                .andExpect(jsonPath("$[0].order.user.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$[0].order.user.phone").value("+321781"))
                .andExpect(jsonPath("$[0].item.id").value(1L))
                .andExpect(jsonPath("$[0].item.price").value(12))
                .andExpect(jsonPath("$[0].item.name").value("item"))
                .andExpect(jsonPath("$[0].item.initPrice").value(10))
                .andExpect(jsonPath("$[0].item.itemCode").value("code"));
    }


    @Test
    void successFindOrderItemAmountByIdEmptyResult() throws Exception {
        long orderId = 1L;

        when(service.findAll(orderId)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/order-item/"+ orderId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    void failedToFindOrderItemAmountById() throws Exception {
        long orderId = 1L;

        when(service.findAll(orderId)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/order-item/"+ orderId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void failedToFindOrderItemAmountByIdForNull() throws Exception {
        Long orderId = null;

        mockMvc.perform(get("/order-item/"+ orderId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successDeleteByOrderItemId() throws Exception {
        long orderItemId = 1L;

        mockMvc.perform(delete("/order-item/"+ orderItemId)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void failedToDeleteByOrderItemId() throws Exception {
        Long orderItemId = null;

        mockMvc.perform(delete("/order-item/"+ orderItemId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}