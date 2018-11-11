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
        var emptyCategories = categoryRepository.findAll();
        assertFalse(emptyCategories.iterator().hasNext());

        entityManager.persist(Category.builder().description("test").build());
        entityManager.persist(Category.builder().description("test2").build());
        entityManager.persist(Category.builder().description("test3").build());
        assertEquals(categoryRepository.count(), 3L);

        var category = categoryRepository.findById(1L).orElseThrow(RuntimeException::new);
        assertEquals(Long.valueOf(1L), category.getId());
        assertEquals("test", category.getDescription());
        var allCategories = categoryRepository.findAll();
        assertEquals(3,allCategories.spliterator().estimateSize());
        assertTrue(categoryRepository.existsById(2L));
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
        entityManager.persist(Category.builder().description("test").build());
        entityManager.persist(Category.builder().description("test").build());
        assertEquals(Long.valueOf(1L),entityManager.find(Category.class, 1L).getId());
        assertEquals(Long.valueOf(2L),entityManager.find(Category.class, 2L).getId());
        categoryRepository.deleteAll();
        assertNull(entityManager.find(Category.class, 1L));
        assertNull(entityManager.find(Category.class, 2L));

        entityManager.persist(Category.builder().description("test").build());
        assertEquals(Long.valueOf(3L),entityManager.find(Category.class, 3L).getId());
        categoryRepository.deleteById(3L);
        assertNull(entityManager.find(Category.class, 3L));

        var category = entityManager.persist(Category.builder().description("test").build());
        assertEquals(Long.valueOf(4L),entityManager.find(Category.class, 4L).getId());
        categoryRepository.delete(category);
        assertNull(entityManager.find(Category.class, 4L));
    }
}