package handson.example.springshopsearch.service;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;


    @Test
    public void getItems1() {
        Mockito.when(itemService.getItems("hoge", "all")).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, itemService.getItems("hoge", "all").size());
    }
}
