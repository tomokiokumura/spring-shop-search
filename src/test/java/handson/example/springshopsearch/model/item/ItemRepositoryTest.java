package handson.example.springshopsearch.model.item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
    }
}
