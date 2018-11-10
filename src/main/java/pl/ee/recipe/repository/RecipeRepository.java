package pl.ee.recipe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ee.recipe.model.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
