package handson.example.springshopsearch.service;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.model.item.ItemRepository;
import handson.example.springshopsearch.utils.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;


    @Test
    public void getItems_ALL() {
        Mockito.when(itemRepository.findByDescriptionContainsOrNameContainsOrderByIdAsc("", "")).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, itemService.getItems("", "all").size());
    }

    @Test
    public void getItems_DESCRIPTION() {
        Mockito.when(itemRepository.findByDescriptionContainsOrderByIdAsc("")).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, itemService.getItems("", "description").size());
    }

    @Test
    public void getItems_NAME() {
        Mockito.when(itemRepository.findByNameContainsOrderByIdAsc("")).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, itemService.getItems("", "name").size());
    }

    @Test
    public void getItem() {
        Mockito.when(itemRepository.getOne(0L)).thenReturn(null);
        Mockito.when(itemRepository.getOne(1L)).thenReturn(new Item(1));

        Assert.assertEquals(new Long(-1), itemService.getItem(0L).getId());
        Assert.assertEquals(new Long(1), itemService.getItem(1L).getId());
    }

    @Test
    public void contraction() {
        List<Item> itemList = Arrays.asList(
                new Item(0L, "", 0, "aaaaaaaaaa"),
                new Item(0L, "", 0, "aa"));
        Assert.assertEquals("aaa...", itemService.contractDescription(itemList, 3).get(0).getDescription());
        Assert.assertEquals("aa", itemService.contractDescription(itemList, 3).get(1).getDescription());
    }
}
