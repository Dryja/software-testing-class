package pl.ee.recipe.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ee.recipe.model.Recipe;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    public void create() { 	
    	Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription("Dobry przepisik");
        recipe.setPrepTime(1);
        recipe.setCookTime(1);
        recipe.setServings(1);
        
        Recipe savedRecipe = recipeRepository.save(recipe);
        assertTrue(recipeRepository.existsById(1L));
        
        assertEquals(Long.valueOf(1L), savedRecipe.getId());
        assertEquals("Dobry przepisik", savedRecipe.getDescription());
        assertEquals(new Integer(1), savedRecipe.getPrepTime());   
        assertEquals(new Integer(1), savedRecipe.getCookTime());   
        assertEquals(new Integer(1), savedRecipe.getServings());   
    }

    @Test
    public void read() {
    	Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription("Dobry przepisik");
        recipe.setPrepTime(1);
        recipe.setCookTime(1);
        recipe.setServings(1);
        
        Recipe savedRecipe = recipeRepository.save(recipe);
        
        Optional<Recipe> singleRecipeOpt = recipeRepository.findById(savedRecipe.getId());
        Recipe singleRecipe = singleRecipeOpt.orElseThrow(RuntimeException::new);
        assertEquals("Dobry przepisik", singleRecipe.getDescription());
        assertEquals(new Integer(1), singleRecipe.getPrepTime());   
        assertEquals(new Integer(1), singleRecipe.getCookTime());   
        assertEquals(new Integer(1), singleRecipe.getServings()); 
    }

    @Test
    public void update() {
    	Recipe recipe = new Recipe();
        recipe.setId(3L);
        recipe.setDescription("Dobry przepisik");
        recipe.setPrepTime(1);
        recipe.setCookTime(1);
        recipe.setServings(1);
  
        Recipe savedRecipe = recipeRepository.save(recipe);
        savedRecipe.setPrepTime(2);
        savedRecipe.setCookTime(2);
        savedRecipe.setServings(2);
        savedRecipe.setDescription("Słaby przepisik");
        recipeRepository.save(savedRecipe);

        Optional<Recipe> singleRecipeOpt = recipeRepository.findById(savedRecipe.getId());
        Recipe singleRecipe = singleRecipeOpt.orElseThrow(RuntimeException::new);
        assertEquals("Słaby przepisik", singleRecipe.getDescription());
        assertEquals(new Integer(2), singleRecipe.getPrepTime());   
        assertEquals(new Integer(2), singleRecipe.getCookTime());   
        assertEquals(new Integer(2), singleRecipe.getServings()); 
    }

    @Test
    public void delete() {
    	BigDecimal amount = new BigDecimal(0.35);
    	Recipe recipe = new Recipe();
        recipeRepository.save(recipe);
        
        assertEquals(1, recipeRepository.count());

        recipeRepository.delete(recipe);
        assertEquals(0, recipeRepository.count());
    }
}