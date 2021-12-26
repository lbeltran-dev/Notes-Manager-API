package com.blueoptima.work.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blueoptima.work.sample.entity.Note;
import com.blueoptima.work.sample.repository.INoteRepo;

@SpringBootTest
class NotesManagerApplicationTests {
	
	@Autowired
	INoteRepo noteRepo;
	
	@Test
	public void testCreate () {
		
		//Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Testing Note");
		note.setContent("This is my note where I execute post test case");

		//Save note to the database
		noteRepo.save(note);
				
		//Testing
		assertThat(noteRepo.existsById(1L)).isTrue();
	}
	
	@Test
	public void testGetAll () {
		
		//Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my note where I execute my get all notes test case");
		
		//Save first note to the database
		noteRepo.save(note);
		
		//Create second note
		note.setId(2L);
		note.setTitle("Testing Second Note");
		note.setContent("This is my second note where I execute my get all notes test case");
		
		//Save second note to the database
		noteRepo.save(note);
		
		//Testing
		List<Note> list = noteRepo.findAll();
		assertThat(list).size().isEqualTo(2);
	}
	
	@Test
	public void testUpdate () {
		
		//Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my note where I execute my update test case");
		
		//Save note to the database
		noteRepo.save(note);
		
		//Create another note with same id and updated data
		Note updatedNote = new Note();
		updatedNote.setId(1L);
		updatedNote.setTitle("Updated my Testing Note");
		updatedNote.setContent("This is my note where I update the testing note");
		
		//Update the note in the database
		noteRepo.save(updatedNote);
				
		//Testing
		assertThat(noteRepo.findById(1L).get().getUpdatedAt()).isNotEqualTo(noteRepo.findById(1L).get().getCreatedAt());
		
	}
	
	@Test
	public void testDelete () {
		
		//Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Testing Note");
		note.setContent("This is my note where I execute my delete note by id test case");
		
		//Save the note in the database
		noteRepo.save(note);
				
		//Delete note in the database
		noteRepo.delete(noteRepo.findById(note.getId()).get());
				
		//Testing
		assertThat(noteRepo.existsById(note.getId())).isFalse();
	}
	
	@Test
	public void testGetById () {
		
		//Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Testing Note");
		note.setContent("This is my note where I execute my get note by id test case");
		
		//Save note in the database
		noteRepo.save(note);
				
		//Testing
		assertThat(noteRepo.existsById(note.getId())).isTrue();
	}
	
	@Test
	public void testDeleteAll () {
		
		//Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my delete all notes test case");
		
		//Save first note in the database
		noteRepo.save(note);
		
		//Create second note
		note.setId(2L);
		note.setTitle("Second Testing Note");
		note.setContent("This is my second note where I execute my delete all notes test case");
		
		//Save second note in the database
		noteRepo.save(note);
		
		//Create third note
		note.setId(3L);
		note.setTitle("Third Testing Note");
		note.setContent("This is my third note where I execute my delete all notes test case");
		
		//Save third note in the database
		noteRepo.save(note);
				
		//Delete all notes in the database
		noteRepo.deleteAll();
		
		
		//Testing
		assertThat(noteRepo.count()).isEqualTo(0);
	}
	
	@Test
	public void testUpdateMultiple () {
		
		//Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");
		
		//Save first note in the database
		noteRepo.save(note);
		
		//Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");
		
		//Save second note in the database
		noteRepo.save(secondNote);
		
		//Update first note
		note.setTitle("First Updated Testing Note");
		note.setContent("This is my first updated note where I execute my update multiple notes test case");
		noteRepo.save(note);
		
		//Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		noteRepo.save(secondNote);
		
		//Testing
		SoftAssertions updateBundle = new SoftAssertions();
		updateBundle.assertThat(noteRepo.findById(1L).get().getUpdatedAt()).isNotEqualTo(noteRepo.findById(1L).get().getCreatedAt());
		updateBundle.assertThat(noteRepo.findById(2L).get().getUpdatedAt()).isNotEqualTo(noteRepo.findById(2L).get().getCreatedAt());
		updateBundle.assertAll();
		
	}

}
