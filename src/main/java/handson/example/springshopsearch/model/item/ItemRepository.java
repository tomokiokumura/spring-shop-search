package handson.example.springshopsearch.model.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContainsOrderByIdAsc(String keyword);
    List<Item> findByDescriptionContainsOrderByIdAsc(String keyword);
    List<Item> findByDescriptionContainsOrNameContainsOrderByIdAsc(String key1, String key2);
}
