package ua.mainacademy.prototype;

import ua.mainacademy.dao.entity.ItemEntity;
import ua.mainacademy.model.Item;

public class ItemPrototype {

    public static Item aNewItem(){
        return Item.builder()
                .id(1L)
                .price(12)
                .name("item")
                .initPrice(10)
                .itemCode("code")
                .build();
    }

    public static ItemEntity aNewItemEntity(){
        return ItemEntity.builder()
                .id(1L)
                .price(12)
                .name("item")
                .initPrice(10)
                .itemCode("code")
                .build();
    }
    public static ItemEntity aNewItemEntityWithoutId(){
        return ItemEntity.builder()
                .price(12)
                .name("item")
                .initPrice(10)
                .itemCode("code")
                .build();
    }
}
