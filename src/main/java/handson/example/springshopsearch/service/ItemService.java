package handson.example.springshopsearch.service;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;
import handson.example.springshopsearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItems(String keyword, String type) {
        switch (type) {
            case Constants.SearchType.ALL:
                return itemRepository.findByDescriptionContainsOrNameContains(keyword, keyword);
            case Constants.SearchType.DESCRIPTION:
                return itemRepository.findByDescriptionContains(keyword);
            case Constants.SearchType.NAME:
                return itemRepository.findByNameContains(keyword);
        }
        return new ArrayList<>();
    }

    public Item getItem(Long id) {
        Optional<Item> opt = itemRepository.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        }
        Item item = new Item();
        item.setId(-1L);
        return item;
    }

    public List<Item> contractDescription(List<Item> list, int length) {
        return list.stream()
                .map(i -> {
                    String d = i.getDescription();
                    i.setDescription(length < d.length() ? d.substring(0, length) + "..." : d);
                    return i;
                })
                .collect(Collectors.toList());
    }
    public void save(Item item) {
        itemRepository.save(item);
    }
}
