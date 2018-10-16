package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.service.Debugger;
import handson.example.springshopsearch.service.ItemService;
import handson.example.springshopsearch.utils.Constants;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Log
public class HomeController {

    @Autowired
    ItemService itemService;

    @Autowired
    Debugger debugger;

    @GetMapping
    public String index(Model model, @RequestParam(name = "keyword", required = false) Optional<String> keyword) {
        List<Item> list = keyword.isPresent() ? itemService.getItems(keyword.get()) : itemService.getAllItems();
        model.addAttribute("isSearching", keyword.isPresent());
        model.addAttribute("count", list.size());
        model.addAttribute("items", list);
        if(keyword.isPresent()) {
            log.info("search keyword: " + keyword.get() + " result: " + list.size());
        }
        return Constants.Templates.INDEX;
    }

    @GetMapping("about")
    public String getAbout() {
        return Constants.Templates.ABOUT;
    }

    @GetMapping("init")
    public String init() {
        int count = debugger.initItems();
        log.info("商品リストを初期化しました 商品件数: " + count + "件");
        return "redirect:/";
    }
}
