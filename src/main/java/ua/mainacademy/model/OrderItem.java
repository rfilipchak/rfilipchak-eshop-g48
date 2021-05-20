package ua.mainacademy.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderItem {

    private Long id;
    @NonNull
    private Item item;
    @NonNull
    private Order order;

    private Integer amount;
}
