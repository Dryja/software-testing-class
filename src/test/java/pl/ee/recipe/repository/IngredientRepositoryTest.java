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

import pl.ee.recipe.model.Ingredient;
import pl.ee.recipe.model.Note;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    public void create() { 	
    	BigDecimal amount = new BigDecimal(0.35);
        Ingredient ingr = new Ingredient();
        ingr.setId(1L);
        ingr.setDescription("Dorodny buraczek");
        ingr.setAmount(amount); //Przepis głównie oparty na dorodnych buraczkach
        
        Ingredient savedIngr = ingredientRepository.save(ingr);
        assertTrue(ingredientRepository.existsById(1L));
        
        assertEquals(Long.valueOf(1L), savedIngr.getId());
        assertEquals("Dorodny buraczek", savedIngr.getDescription());
        assertEquals(amount, savedIngr.getAmount());     
    }

    @Test
    public void read() {
    	BigDecimal amount = new BigDecimal(0.35);
    	Ingredient ingr = new Ingredient();
        ingr.setId(5L);
        ingr.setDescription("Dorodny buraczek");
        ingr.setAmount(amount); //Przepis głównie oparty na dorodnych buraczkach
        
        Ingredient savedIngr = ingredientRepository.save(ingr);
        
        Optional<Ingredient> singleIngrOpt = ingredientRepository.findById(savedIngr.getId());
        Ingredient singleIngr = singleIngrOpt.orElseThrow(RuntimeException::new);
        assertEquals(singleIngr.getDescription(), "Dorodny buraczek");
        assertEquals(singleIngr.getAmount(), amount);
        assertEquals(singleIngr.getId(), Long.valueOf(4L));
    }

    @Test
    public void update() {
    	BigDecimal amount = new BigDecimal(0.35);
    	BigDecimal amountUpdated = new BigDecimal(0.45);
    	Ingredient ingr = new Ingredient();
        ingr.setId(3L);
        ingr.setDescription("Dorodny buraczek");
        ingr.setAmount(amount);
        
        Ingredient savedIngr = ingredientRepository.save(ingr);
        
        savedIngr.setDescription("Niedorodny Buraczek");
        savedIngr.setAmount(amountUpdated);
        ingredientRepository.save(savedIngr);

        Optional<Ingredient> singleIngrOpt = ingredientRepository.findById(savedIngr.getId());
        Ingredient singleIngr = singleIngrOpt.orElseThrow(RuntimeException::new);
        assertEquals(singleIngr.getDescription(), "Niedorodny Buraczek");
        assertEquals(singleIngr.getAmount(), amountUpdated);
    }

    @Test
    public void delete() {
    	BigDecimal amount = new BigDecimal(0.35);
    	Ingredient ingr = new Ingredient();
        ingredientRepository.save(ingr);
        
        assertEquals(1, ingredientRepository.count());

        ingredientRepository.delete(ingr);
        assertEquals(0, ingredientRepository.count());
    }
}