package handson.example.springshopsearch.model.item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void findByNameContains() {
        testEntityManager.persist(new Item(null, "name10", 100, "dummyy"));
        testEntityManager.persist(new Item(null, "name14", 300, "dummyyyyyyyy"));
        testEntityManager.persist(new Item(null, "name72", 500, "dummyy"));
        testEntityManager.persist(new Item(null, "hoge35", 700, "dummyyyyyy"));
        testEntityManager.persist(new Item(null, "hoge15", 700, "dummyyyy"));

        List<Item> itemList = itemRepository.findByNameContainsOrderByIdAsc("name");
        assertEquals(3, itemList.size());
        assertTrue(itemList.get(0).getId() < itemList.get(1).getId());
    }

    @Test
    public void findByDescriptionContains() {
        testEntityManager.persist(new Item(null, "dummy", 100, "desc2"));
        testEntityManager.persist(new Item(null, "dummy", 100, "desc1"));
        testEntityManager.persist(new Item(null, "dummy", 100, "1desc1"));
        testEntityManager.persist(new Item(null, "dummy", 100, "aaa"));
        testEntityManager.persist(new Item(null, "dummy", 100, "bbb"));

        List<Item> itemList = itemRepository.findByDescriptionContainsOrderByIdAsc("desc");
        assertEquals(3, itemList.size());
        assertTrue(itemList.get(0).getId() < itemList.get(1).getId());
    }

    @Test
    public void findByDescriptionContainsOrNameContains() {
        testEntityManager.persist(new Item(null, "dummy", 100, "desc2"));
        testEntityManager.persist(new Item(null, "dummy", 100, "desc1"));
        testEntityManager.persist(new Item(null, "dummy", 100, "1desc1"));
        testEntityManager.persist(new Item(null, "desc", 100, "aaa"));
        testEntityManager.persist(new Item(null, "adesca", 100, "bbb"));

        List<Item> itemList = itemRepository.findByDescriptionContainsOrNameContainsOrderByIdAsc("desc", "desc");
        assertEquals(5, itemList.size());
        assertTrue(itemList.get(0).getId() < itemList.get(1).getId());
    }
}
