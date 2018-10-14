package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.service.ItemService;
import handson.example.springshopsearch.utils.Constants;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/items")
@Log
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping
    public String listItem(Model model) {
        List<Item> list = itemService.getAllItems();
        model.addAttribute("items", itemService.contractDescription(list, 10));
        return Constants.Templates.ITEM_LIST;
    }

    @GetMapping("{id:[0-9]+}")
    public String getDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("item", itemService.getItem(id));
        return Constants.Templates.ITEM_DETAIL;
    }

    @GetMapping("/add")
    public String getForm() {
        return Constants.Templates.ITEM_ADD_FORM;
    }

    @PostMapping("/add")
    public String registerItem(Item item) {
        log.info("Register new item. " + item);
        itemService.save(item);
        return "redirect:/items";
    }
}
