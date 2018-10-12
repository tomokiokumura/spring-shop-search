package handson.example.springshopsearch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.service.ItemService;

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
