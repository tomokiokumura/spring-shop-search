package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    public String index(
            Model model,
            @RequestParam(name = "keyword", required = false) Optional<String> keyword) {
        List<Item> list = keyword.isPresent()
                ? itemRepository.findByNameContainsOrderByIdAsc(keyword.get())
                : itemRepository.findAll();
        model.addAttribute("items", list);
        return "index";
    }

    @GetMapping("about")
    public String getAbout() {
        return "about";
    }
}
