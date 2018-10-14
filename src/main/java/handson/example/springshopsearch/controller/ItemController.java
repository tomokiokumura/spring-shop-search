package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.service.ItemService;
import handson.example.springshopsearch.utils.Constants;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@Log
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/add")
    public String getForm() {
        return Constants.Templates.ITEM_ADD_FORM;
    }

    @PostMapping("/add")
    public String registerItem(Item item) {
        log.info("Register new item. " + item);
        itemService.save(item);
        return "redirect:/";
    }
}
