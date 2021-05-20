package ua.mainacademy.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Item {

    private Long id;
    @NonNull
    private String itemCode;
    @NonNull
    private String name;

    private Integer price;
    private Integer initPrice;
}
