package handson.example.springshopsearch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
    ItemRepository itemRepository;

    @GetMapping
    public String index(
            Model model,
            @RequestParam(name = "keyword", required = false) Optional<String> keyword,
            @RequestParam(name = "searchtype", required = true) Optional<String> searchtype) {
        List<Item> list = new ArrayList<>();
        if (keyword.isPresent()) {
            switch (searchtype.get()) {
                case "name":
                    list = itemRepository.findByNameContainsOrderByIdAsc(keyword.get());
                    break;
                case "description":
                    list = itemRepository.findByDescriptionContainsOrderByIdAsc(keyword.get());
                    break;
                default:
                    list = itemRepository.findByNameContainingOrDescriptionContaining(keyword.get(), keyword.get());
                    break;
            }
        }
        else {
            list = itemRepository.findAll();
        }
        model.addAttribute("items", list);
        return "index";
    }

    @GetMapping("about")
    public String getAbout() {
        return "about";
    }
}
