package ua.mainacademy.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Order {

    private Long id;
    @NonNull
    private User user;

    private Long creationTime;
    private OrderStatus status;
}
