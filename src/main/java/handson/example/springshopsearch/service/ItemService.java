package com.mosmos21.shop_search.service;

import com.mosmos21.shop_search.model.item.Item;
import com.mosmos21.shop_search.model.item.ItemRepository;
import com.mosmos21.shop_search.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
