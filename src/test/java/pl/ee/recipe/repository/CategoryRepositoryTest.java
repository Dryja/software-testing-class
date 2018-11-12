package pl.ee.recipe.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ee.recipe.model.Category;

import java.util.ArrayList;
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
    Long id1 = categoryRepository.save(Category.builder().description("test").build()).getId();
    String categoryDesc = entityManager.find(Category.class, id1).getDescription();
    assertEquals("test", categoryDesc);
    List<Category> categories = new ArrayList<>();
    categories.add(Category.builder().description("test2").build());
    categories.add(Category.builder().description("test3").build());
    categories.add(Category.builder().description("test3").build());

    categoryRepository.saveAll(categories).forEach(category -> assertNotNull(category.getId()));
  }

  @Test
  public void read() {

    Long id1 = entityManager.persist(Category.builder().description("test").build()).getId();
    Long id2 = entityManager.persist(Category.builder().description("test2").build()).getId();
    entityManager.persist(Category.builder().description("test3").build());
    assertTrue(categoryRepository.count() >= 3);

    Iterable<Category> categories = categoryRepository.findAll();
    categories.forEach(category -> assertNotNull(category.getId()));

    Category category = categoryRepository.findById(id1).orElseThrow(RuntimeException::new);
    assertEquals(id1, category.getId());
    assertEquals("test", category.getDescription());
    assertTrue(categoryRepository.existsById(id2));
  }

  @Test
  public void update() {
    Category category = entityManager.persist(Category.builder().description("test").build());
    category.setDescription("test_updated");
    Category afterUpdate = categoryRepository.save(category);
    assertEquals("test_updated", afterUpdate.getDescription());
    assertEquals(category.getId(), afterUpdate.getId());
  }

  @Test
  public void delete() {
    Long id1 = entityManager.persist(Category.builder().description("test").build()).getId();
    Long id2 = entityManager.persist(Category.builder().description("test").build()).getId();
    assertEquals(id1, entityManager.find(Category.class, id1).getId());
    assertEquals(id2, entityManager.find(Category.class, id2).getId());
    categoryRepository.deleteAll();
    assertNull(entityManager.find(Category.class, id1));
    assertNull(entityManager.find(Category.class, id2));

    Long id3 = entityManager.persist(Category.builder().description("test").build()).getId();
    assertEquals(id3, entityManager.find(Category.class, id3).getId());
    categoryRepository.deleteById(id3);
    assertNull(entityManager.find(Category.class, id3));

    Category category = entityManager.persist(Category.builder().description("test").build());
    Long id4 = category.getId();
    assertEquals(id4, entityManager.find(Category.class, id4).getId());
    categoryRepository.delete(category);
    assertNull(entityManager.find(Category.class, id4));
  }
}