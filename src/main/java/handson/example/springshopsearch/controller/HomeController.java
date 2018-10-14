package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.service.ItemService;
import handson.example.springshopsearch.utils.Constants;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@Log
public class HomeController {

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public String index(Model model) {
        List<Item> list = itemService.getAllItems();
        model.addAttribute("count", list.size());
        model.addAttribute("items", list);
        return Constants.Templates.INDEX;
    }
}
