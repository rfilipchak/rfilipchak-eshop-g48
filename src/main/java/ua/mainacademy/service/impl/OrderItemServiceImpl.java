package ua.mainacademy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.dao.OrderItemDAO;
import ua.mainacademy.dao.entity.ItemEntity;
import ua.mainacademy.dao.entity.OrderItemEntity;
import ua.mainacademy.dao.mappers.OrderEntityMapper;
import ua.mainacademy.dao.mappers.OrderItemEntityMapper;
import ua.mainacademy.model.OrderItem;
import ua.mainacademy.service.OrderItemService;
import ua.mainacademy.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderService orderService;
    private final OrderItemEntityMapper orderItemEntityMapper;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderItemDAO orderItemDAO;
    private final ItemDAO itemDAO;

    @Override
    public OrderItem createNewOrderItem(long userId, long itemId, int amount) {
        Optional<ItemEntity> item = itemDAO.findById(itemId);
        if (item.isPresent() && amount != 0) {
            OrderItemEntity save = orderItemDAO.save(OrderItemEntity.builder()
                    .item(item.get())
                    .order(orderEntityMapper.toOrderEntity(orderService.createNewOrGetOpenOrderForUser(userId)))
                    .amount(amount)
                    .build());
            return orderItemEntityMapper.toOrderItem(save);
        }
        throw new RuntimeException(String.format("Can not create new orderItem for userId = %s item = %s amount = %s",
                userId, itemId, amount));
    }

    @Override
    public OrderItem updateOrderItemAmountByOrderItemId(long orderItemId, int amount) {
        Optional<OrderItemEntity> orderItemById = orderItemDAO.findById(orderItemId);
        if (orderItemById.isEmpty() || amount == 0) {
            throw new RuntimeException(String.format("Can not update orderItem = %s amount = %s",
                    orderItemId, amount));
        }
        return orderItemEntityMapper.toOrderItem(orderItemDAO.save(orderItemById.get().toBuilder().amount(amount).build()));
    }

    @Override
    public List<OrderItem> findAll(long orderId) {
        return orderItemDAO.findAllByOrderId(orderId).stream()
                .map(orderItemEntityMapper::toOrderItem)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItemById(long orderItemId) {
        OrderItemEntity orderItemById = orderItemDAO.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem does not exist"));
        long orderId = orderItemById.getOrder().getId();
        orderItemDAO.deleteById(orderItemId);
        if (orderItemDAO.findAllByOrderId(orderId).isEmpty()) {
            orderService.deleteById(orderId);
        }
    }
}
