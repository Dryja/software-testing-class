package pl.ee.recipe.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ee.recipe.model.Category;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void create() {
        categoryRepository.save(Category.builder().id(1L).description("test").build());
        var category = entityManager.find(Category.class, 1L);
        if(category == null) {
            throw new EntityNotFoundException();
        }
        assertEquals("test", category.getDescription());
        var categories = List.of(
          Category.builder().description("test2").build(),
          Category.builder().description("test3").build(),
          Category.builder().description("test3").build()
        );

        categoryRepository.saveAll(categories);
        var category2 = entityManager.find(Category.class, 4L);
        if(category2 == null) {
            throw new EntityNotFoundException();
        }
        assertEquals("test3" ,category2.getDescription());
    }

    @Test
    public void read() {

        var id1 = entityManager.persist(Category.builder().description("test").build()).getId();
        var id2 = entityManager.persist(Category.builder().description("test2").build()).getId();
        entityManager.persist(Category.builder().description("test3").build());
        assertTrue(categoryRepository.count() >= 3);

        var categories = categoryRepository.findAll();
        categories.forEach(category -> assertNotNull(category.getId()));

        var category = categoryRepository.findById(id1).orElseThrow(RuntimeException::new);
        assertEquals(id1, category.getId());
        assertEquals("test", category.getDescription());
        assertTrue(categoryRepository.existsById(id2));
    }

    @Test
    public void update() {
        var category = entityManager.persist(Category.builder().description("test").build());
        category.setDescription("test_updated");
        var afterUpdate = categoryRepository.save(category);
        assertEquals("test_updated", afterUpdate.getDescription());
        assertEquals(category.getId(), afterUpdate.getId());
    }

    @Test
    public void delete() {
        var id1 = entityManager.persist(Category.builder().description("test").build()).getId();
        var id2 = entityManager.persist(Category.builder().description("test").build()).getId();
        assertEquals(id1,entityManager.find(Category.class, id1).getId());
        assertEquals(id2,entityManager.find(Category.class, id2).getId());
        categoryRepository.deleteAll();
        assertNull(entityManager.find(Category.class, id1));
        assertNull(entityManager.find(Category.class, id2));

        var id3 = entityManager.persist(Category.builder().description("test").build()).getId();
        assertEquals(id3,entityManager.find(Category.class, id3).getId());
        categoryRepository.deleteById(id3);
        assertNull(entityManager.find(Category.class, id3));

        var category = entityManager.persist(Category.builder().description("test").build());
        var id4 = category.getId();
        assertEquals(id4,entityManager.find(Category.class, id4).getId());
        categoryRepository.delete(category);
        assertNull(entityManager.find(Category.class, id4));
    }
}