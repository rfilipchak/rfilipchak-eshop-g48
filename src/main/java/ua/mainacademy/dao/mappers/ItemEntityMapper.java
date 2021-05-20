package ua.mainacademy.dao.mappers;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.mainacademy.dao.entity.ItemEntity;
import ua.mainacademy.model.Item;

@Component
@NoArgsConstructor
public class ItemEntityMapper {

    public Item toItem(ItemEntity itemEntity) {
        return Item.builder()
                .id(itemEntity.getId())
                .itemCode(itemEntity.getItemCode())
                .initPrice(itemEntity.getInitPrice())
                .name(itemEntity.getName())
                .price(itemEntity.getPrice())
                .build();
    }

    public ItemEntity toItemEntity(Item item) {
        return ItemEntity.builder()
                .id(item.getId())
                .itemCode(item.getItemCode())
                .initPrice(item.getInitPrice())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
}
