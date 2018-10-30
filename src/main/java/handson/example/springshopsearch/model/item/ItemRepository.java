package handson.example.springshopsearch.model.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByNameContainsOrderByIdAsc(String keyword);

	List<Item> findByDescriptionContainsOrderByIdAsc(String keyword);

	List<Item> findByNameContainingOrDescriptionContaining(String keyword1, String keyword2);
}