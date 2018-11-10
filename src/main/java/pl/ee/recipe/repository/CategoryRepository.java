package pl.ee.recipe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ee.recipe.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
