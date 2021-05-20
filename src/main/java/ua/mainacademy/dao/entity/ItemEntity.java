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
@Table(name = "items")
@Entity
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @Column(nullable = false)
    private String name;

    private Integer price;

    @Column(name = "init_price")
    private Integer initPrice;
}
