package handson.example.springshopsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping
    public String listItem(Model model) {
        List<Item> list = Arrays.asList(
                new Item(1L, "商品1", 100, "説明1"),
                new Item(2L, "商品2", 200, "説明2"),
                new Item(3L, "商品3", 300, "説明3")
        );
        model.addAttribute("items", list);
        return "item_list";
    }
}
