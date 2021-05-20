package ua.mainacademy.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.mainacademy.model.OrderStatus;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private UserEntity user;

    @Column(name = "creation_time", nullable = false)
    private Long creationTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
