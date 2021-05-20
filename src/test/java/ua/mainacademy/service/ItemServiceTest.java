package ua.mainacademy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.dao.entity.ItemEntity;
import ua.mainacademy.dao.mappers.ItemEntityMapper;
import ua.mainacademy.model.Item;
import ua.mainacademy.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.mainacademy.prototype.ItemPrototype.aNewItem;
import static ua.mainacademy.prototype.ItemPrototype.aNewItemEntity;
import static ua.mainacademy.prototype.UserPrototype.aNewUser;
import static ua.mainacademy.prototype.UserPrototype.aNewUserEntity;

@SpringBootTest
@ActiveProfiles("test")
class ItemServiceTest {

    @Autowired
    private ItemService service;
    @Autowired
    private ItemEntityMapper mapper;
    @MockBean
    private ItemDAO itemDAO;

    @Test
    void createTest() {
        Item itemForCreating = aNewItem().toBuilder().id(null).build();

        when(itemDAO.findItemEntityByItemCode(itemForCreating.getItemCode())).thenReturn(Optional.empty());
        when(itemDAO.save(mapper.toItemEntity(itemForCreating))).thenReturn(aNewItemEntity());

        assertThat(service.create(itemForCreating)).isEqualTo(aNewItem());
    }

    @Test
    void creatingErrorForExistingId() {
        Item itemForCreating = aNewItem();

        assertThrows(RuntimeException.class, () -> {
            service.create(itemForCreating);
        });
    }

    @Test
    void creatingErrorForNull() {
        assertThrows(RuntimeException.class, () -> {
            service.create(null);
        });
    }

    @Test
    void creatingErrorForExistingItemCode() {
        Item itemForCreating = aNewItem().toBuilder().id(null).build();

        when(itemDAO.findItemEntityByItemCode(itemForCreating.getItemCode())).thenReturn(Optional.of(aNewItemEntity()));

        assertThrows(RuntimeException.class, () -> {
            service.create(itemForCreating);
        });
    }

    @Test
    void updateTest() {
        Item itemForUpdate = aNewItem().toBuilder().initPrice(100500).build();
        Item expectedItem = aNewItem().toBuilder().initPrice(100500).build();
        ItemEntity updatedItemEntity = aNewItemEntity().toBuilder().initPrice(100500).build();

        when(itemDAO.findById(itemForUpdate.getId())).thenReturn(Optional.of(aNewItemEntity()));
        when(itemDAO.save(mapper.toItemEntity(itemForUpdate)))
                .thenReturn(updatedItemEntity);

        assertThat(service.update(itemForUpdate)).isEqualTo(expectedItem);
    }

    @Test
    void updatingErrorForNewItem() {
        Item itemForUpdate = aNewItem().toBuilder().id(null).build();

        assertThrows(RuntimeException.class, () -> {
            service.update(itemForUpdate);
        });
    }

    @Test
    void updatingErrorForEmptyItemDAOResult() {
        Item itemForUpdate = aNewItem();

        when(itemDAO.findById(itemForUpdate.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.update(itemForUpdate);
        });
    }

    @Test
    void updatingErrorForNull() {
        assertThrows(RuntimeException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void findOneByIdTest() {
        when(itemDAO.findById(1L)).thenReturn(Optional.of(aNewItemEntity()));

        assertThat(service.findOneById(1L)).isEqualTo(aNewItem());
    }

    @Test
    void errorForFindOneByIdTest() {
        when(itemDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.findOneById(1L);
        });
    }

    @Test
    void findAllTest() {
        when(itemDAO.findAll()).thenReturn(List.of(aNewItemEntity(), aNewItemEntity()
                .toBuilder().id(2L)
                .itemCode("newCode")
                .build()));

        assertThat(service.findAll()).isEqualTo(List.of(aNewItem(), aNewItem()
                .toBuilder().id(2L)
                .itemCode("newCode")
                .build()));
    }

    @Test
    void findAllEmptyTest() {
        when(itemDAO.findAll()).thenReturn(emptyList());

        assertThat(service.findAll()).isEqualTo(emptyList());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(1L);
        verify(itemDAO, times(1)).deleteById(1L);
    }
}