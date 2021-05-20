package ua.mainacademy.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mainacademy.model.OrderItem;
import ua.mainacademy.service.OrderItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("order-item")
@AllArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PutMapping
    public ResponseEntity<OrderItem> createNewOrderItem(@RequestParam long userId,
                                                        @RequestParam long itemId,
                                                        @RequestParam int amount) {
        try {
            return new ResponseEntity<>(orderItemService.createNewOrderItem(userId, itemId, amount), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<OrderItem> updateOrderItemAmountById(@RequestParam long orderItemId,
                                                               @RequestParam int amount) {
        try {
            return new ResponseEntity<>(orderItemService.updateOrderItemAmountByOrderItemId(orderItemId,amount), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItem>> findOrderItemAmountByOrderId(@PathVariable long orderId) {
        try {
            return new ResponseEntity<>(orderItemService.findAll(orderId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItemAmountById(@PathVariable long orderItemId) {
        try {
            orderItemService.deleteOrderItemById(orderItemId);
        } catch (Exception e) {
            log.warn("Delete method was processed with exception for order item with id {}", orderItemId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
