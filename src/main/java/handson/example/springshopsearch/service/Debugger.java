package handson.example.springshopsearch.service;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class Debugger {

    @Autowired
    ItemRepository itemRepository;

    public int initItems() {
        itemRepository.deleteAll();
        List<Item> itemList = getItems();
        itemRepository.saveAll(itemList);
        return itemList.size();
    }

    public List<Item> getItems() {
        List<Item> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/data.csv"), "UTF-8"))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] s = line.split(",");
                list.add(new Item(Long.parseLong(s[0]), s[1], Integer.parseInt(s[2]), s[3]));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
