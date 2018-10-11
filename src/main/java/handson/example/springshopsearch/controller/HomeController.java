package com.mosmos21.shop_search.controller;

import com.mosmos21.shop_search.model.item.Item;
import com.mosmos21.shop_search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/")
public class HomeController {

    @Autowired
    ItemService itemService;

    @GetMapping
    public String index(Model model) {
        List<Item> list = itemService.getItems();
        model.addAttribute("items", list);
        return "index";
    }

    @PostMapping
    public String Search(Model model, @RequestBody Map<String, String> params) {
        List<Item> list = itemService.getItems(params.get("itemName"));
        model.addAttribute("items", list);
        return "index";
    }
}
