package handson.example.springshopsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;
import handson.example.springshopsearch.model.user.UserRepository;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItems(String name) {
        return itemRepository.findByNameLike(name);
    }

    public List<Item> getItems(int priceMin, int priceMax) {
        return itemRepository.findByPriceBetween(priceMin, priceMax);
    }

    public List<Item> getItemByAuthorName(String author) {
        Long id = userRepository.findFirstByName(author).getId();
        return itemRepository.findByAuthorId(id);
    }
}
