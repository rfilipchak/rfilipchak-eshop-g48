package ua.mainacademy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mainacademy.dao.OrderDAO;
import ua.mainacademy.dao.UserDAO;
import ua.mainacademy.dao.entity.OrderEntity;
import ua.mainacademy.dao.entity.UserEntity;
import ua.mainacademy.dao.mappers.OrderEntityMapper;
import ua.mainacademy.model.Order;
import ua.mainacademy.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static ua.mainacademy.model.OrderStatus.CLOSED;
import static ua.mainacademy.model.OrderStatus.OPEN;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final OrderEntityMapper mapper;

    @Override
    public Order createNewOrGetOpenOrderForUser(long userId) {
        UserEntity validatedUser = userValidation(userId);
        Optional<OrderEntity> firstByUserAndStatus = orderDAO.findFirstByUserAndStatus(validatedUser, OPEN);
        if (firstByUserAndStatus.isEmpty()) {
            return mapper.toOrder(orderDAO.save(OrderEntity.builder()
                    .user(validatedUser)
                    .creationTime(System.currentTimeMillis())
                    .status(OPEN)
                    .build()));
        }
        return mapper.toOrder(firstByUserAndStatus.get());
    }

    @Override
    public Order closeOrderForUser(long userId) {
        Optional<OrderEntity> firstByUserAndStatus = orderDAO.findFirstByUserAndStatus(userValidation(userId), OPEN);
        if (firstByUserAndStatus.isPresent()) {
            OrderEntity orderForStatusUpdate = firstByUserAndStatus.get();
            return mapper.toOrder(orderDAO.save(orderForStatusUpdate.toBuilder()
                    .status(CLOSED)
                    .build()));
        }
        throw new RuntimeException("Can not update closed order");
    }

    @Override
    public Order findActiveOrdersForUser(long userId) {
        Optional<OrderEntity> firstByUserAndStatus = orderDAO.findFirstByUserAndStatus(userValidation(userId), OPEN);
        if (firstByUserAndStatus.isPresent()) {
            return mapper.toOrder(firstByUserAndStatus.get());
        }
        throw new RuntimeException(String.format("Such User %s did not open order yet", userId));
    }

    @Override
    public List<Order> historyOfOrdersForUser(long userId) {
        return orderDAO.findAllByUserAndStatus(userValidation(userId), CLOSED).stream()
                .map(mapper::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        orderDAO.deleteById(id);
    }

    private UserEntity userValidation(Long userId) {
            return userDAO.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User was not found"));
    }
}
