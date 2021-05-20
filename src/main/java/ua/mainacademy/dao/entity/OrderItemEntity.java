package ua.mainacademy.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
@Entity
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "item_id", nullable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private ItemEntity item;

    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private OrderEntity order;

    @Column(nullable = false)
    private Integer amount;
}
