package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping("/add")
    public String getForm() {
        return Constants.Templates.ITEM_ADD_FORM;
    }
}
