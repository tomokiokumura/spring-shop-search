package handson.example.springshopsearch.service;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItems(String keyword) {
        return itemRepository.findByNameLike(keyword);
    }

    public void save(Item item) {
        itemRepository.save(item);
    }
}
