package handson.example.springshopsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;

@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping
    public String listItem(Model model) {
        List<Item> list = itemRepository.findAll();
        model.addAttribute("items", list);
        return "list_item";
    }

    @GetMapping("add")
    public String getForm() {
        return "item_form";
    }

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/add")
    public String registerItem(Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("{id:[0-9]+}")
    public String getDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("item", itemRepository.getOne(id));
        return "detail";
    }
}