package ua.mainacademy.dao.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.ItemEntity;
import ua.mainacademy.model.Item;
import ua.mainacademy.prototype.ItemPrototype;

import static org.junit.jupiter.api.Assertions.*;
import static ua.mainacademy.prototype.ItemPrototype.*;

@SpringBootTest
@ActiveProfiles("test")
class ItemEntityMapperTest {
    @Autowired
    private ItemEntityMapper mapper;

    @Test
    void toItemTest() {
        Item expected = aNewItem();
        ItemEntity itemEntity = aNewItemEntity();

        Assertions.assertThat(mapper.toItem(itemEntity))
                .isEqualTo(expected);
    }

    @Test
    void toItemEntityTest() {
        Item item = aNewItem();
        ItemEntity expected = aNewItemEntity();

        Assertions.assertThat(mapper.toItemEntity(item))
                .isEqualTo(expected);
    }
}