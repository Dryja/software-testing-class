package pl.ee.recipe.repository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ee.recipe.model.Note;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void create() {
        Note note = new Note();
        note.setRecipeNote("note txt");
        Note noteSaved = noteRepository.save(note);
        assertEquals(1, noteRepository.count());
    }

    @Test
    public void read() {
        Note note = new Note();
        note.setRecipeNote("note txt");
        Note noteSaved = noteRepository.save(note);

        Optional<Note> singleNoteOpt = noteRepository.findById(noteSaved.getId());
        Note singleNote = singleNoteOpt.orElseThrow(RuntimeException::new);
        assertEquals(singleNote.getRecipeNote(), "note txt");
    }

    @Test
    public void update() {
        Note note = new Note();
        note.setRecipeNote("tobeupdated");
        Note noteSaved = noteRepository.save(note);
        noteSaved.setRecipeNote("updated");
        noteRepository.save(noteSaved);

        Optional<Note> singleNoteOpt = noteRepository.findById(noteSaved.getId());
        Note singleNote = singleNoteOpt.orElseThrow(RuntimeException::new);
        assertEquals(singleNote.getRecipeNote(), "updated");
    }

    @Test
    public void delete() {
        Note note = new Note();
        note.setRecipeNote("simple note");
        noteRepository.save(note);

        assertEquals(1, noteRepository.count());

        noteRepository.delete(note);
        assertEquals(0, noteRepository.count());
    }
}