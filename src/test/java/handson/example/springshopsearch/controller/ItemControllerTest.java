package handson.example.springshopsearch.controller;

import handson.example.springshopsearch.model.item.Item;
import handson.example.springshopsearch.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    ItemController itemController;

    @Mock
    ItemService itemService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void listItem() throws Exception {
        Mockito.when(itemService.getAllItems()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDetail() throws Exception {
        Mockito.when(itemService.getItem(1L)).thenReturn(new Item(1));

        mockMvc.perform(MockMvcRequestBuilders.get("/items/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getFrom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items/add"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
