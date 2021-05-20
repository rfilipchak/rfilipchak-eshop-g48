package ua.mainacademy.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.mainacademy.prototype.ItemPrototype.aNewItemEntityWithoutId;

@SpringBootTest
@ActiveProfiles("test")
class ItemDAOTest {

    @Autowired
    private ItemDAO itemDAO;

    private ItemEntity savedItem;

    @BeforeEach
    void setUp() {
        savedItem = itemDAO.save(aNewItemEntityWithoutId());
    }

    @AfterEach
    void tearDown() {
        itemDAO.delete(savedItem);
    }

    @Test
    void shouldSaveItem(){
        assertThat(savedItem.getId()).isNotNull();
    }

    @Test
    void findItemEntityByItemCode() {
        assertThat(itemDAO.findItemEntityByItemCode("code").get())
                .isEqualTo(savedItem);
    }

    @Test
    void findEmptyItemEntityByItemCode() {
        assertThat(itemDAO.findItemEntityByItemCode("code123"))
                .isEqualTo(Optional.empty());
    }

    @Test
    void shouldFindAll(){
        ItemEntity newItem = itemDAO.save(aNewItemEntityWithoutId().toBuilder().itemCode("next_code").build());

        List<ItemEntity> items = itemDAO.findAll();

        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getId()).isEqualTo(savedItem.getId());
        assertThat(items.get(1).getId()).isEqualTo(newItem.getId());

        itemDAO.delete(newItem);
    }
}