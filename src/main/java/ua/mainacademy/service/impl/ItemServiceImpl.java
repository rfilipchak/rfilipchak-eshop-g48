package ua.mainacademy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.dao.mappers.ItemEntityMapper;
import ua.mainacademy.model.Item;
import ua.mainacademy.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;
    private final ItemEntityMapper mapper;

    @Override
    public Item create(Item item) {
        if (isNull(item.getId()) && itemDAO.findItemEntityByItemCode(item.getItemCode()).isEmpty() ) {
            return mapper.toItem(itemDAO.save(mapper.toItemEntity(item)));
        }
        throw new RuntimeException("Such item can not be saved");
    }

    @Override
    public Item update(Item item) {
        if (nonNull(item.getId()) && itemDAO.findById(item.getId()).isPresent()) {
            return mapper.toItem(itemDAO.save(mapper.toItemEntity(item)));
        }
        throw new RuntimeException("Such item can not be updated");
    }

    @Override
    public Item findOneById(long id) {
        return mapper.toItem(itemDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Item was not found")));
    }

    @Override
    public List<Item> findAll() {
        return itemDAO.findAll().stream()
                .map(mapper::toItem)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        itemDAO.deleteById(id);
    }
}
