package handson.example.springshopsearch.model.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContains(String keyword);
    List<Item> findByDescriptionContains(String keyword);
    List<Item> findByDescriptionContainsOrNameContains(String key1, String key2);
}
