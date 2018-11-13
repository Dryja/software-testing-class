package pl.ee.recipe.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ee.recipe.model.UnitOfMeasure;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void create() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(3L);
        unitOfMeasure.setDescription("pint");

        UnitOfMeasure unitOfMeasureSaved =  unitOfMeasureRepository.save(unitOfMeasure);

        assertEquals(Long.valueOf(3L), unitOfMeasureSaved.getId());
        assertEquals("pint", unitOfMeasureSaved.getDescription());
        assertEquals(3, unitOfMeasureRepository.count());
        assertTrue(unitOfMeasureRepository.existsById(3L));
    }

    @Test
    public void read() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findById(1L);
        UnitOfMeasure unitOfMeasure = unitOfMeasureOptional.orElseThrow(RuntimeException::new);

        assertEquals(Long.valueOf(1L), unitOfMeasure.getId());
        assertEquals("cup", unitOfMeasure.getDescription());
    }

    @Test
    public void update() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasure.setDescription("pint");

        UnitOfMeasure unitOfMeasureUpdated = unitOfMeasureRepository.save(unitOfMeasure);

        assertEquals(Long.valueOf(1L), unitOfMeasureUpdated.getId());
        assertEquals("pint", unitOfMeasureUpdated.getDescription());
        assertEquals(2, unitOfMeasureRepository.count());
        assertTrue(unitOfMeasureRepository.existsById(1L));
    }

    @Test
    public void delete() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasure.setDescription("cup");

        assertEquals(2, unitOfMeasureRepository.count());
        assertTrue(unitOfMeasureRepository.existsById(1L));

        unitOfMeasureRepository.delete(unitOfMeasure);

        assertEquals(1, unitOfMeasureRepository.count());
        assertFalse(unitOfMeasureRepository.existsById(1L));
    }
}