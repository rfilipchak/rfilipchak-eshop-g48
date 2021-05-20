package ua.mainacademy.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mainacademy.model.Order;
import ua.mainacademy.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/{userId}")
    public ResponseEntity<Order> createOpenOrder(@PathVariable long userId) {
        try {
            return new ResponseEntity<>(orderService.createNewOrGetOpenOrderForUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Order> closeOrderForUser(@PathVariable long userId) {
        try {
            return new ResponseEntity<>(orderService.closeOrderForUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/open-orders/{userId}")
    public ResponseEntity<Order> findOrderById(@PathVariable long userId) {
        try {
            return new ResponseEntity<>(orderService.findActiveOrdersForUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/closed-orders/{userId}")
    public ResponseEntity<List<Order>> findAllClosedOrdersForUser(@PathVariable long userId) {
        return new ResponseEntity<>(orderService.historyOfOrdersForUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable long id) {
        try {
            orderService.deleteById(id);
        } catch (Exception e) {
            log.warn("Delete method was processed with exception for order with id {}", id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
