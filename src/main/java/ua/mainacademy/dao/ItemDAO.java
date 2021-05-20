package ua.mainacademy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mainacademy.dao.entity.ItemEntity;
import ua.mainacademy.model.Item;

import java.util.Optional;

@Repository
public interface ItemDAO extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findItemEntityByItemCode(String itemCode);
}
