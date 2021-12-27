package com.blueoptima.work.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import com.blue.optima.work.sample.service.NoteService;
import com.blueoptima.work.sample.entity.Note;
import com.blueoptima.work.sample.repository.INoteRepo;

@SpringBootTest
class NotesManagerApplicationTests {

	@Autowired
	INoteRepo noteRepo;

	@Autowired
	NoteService noteService;

	@Test
	public void testCreate() {

		// Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Testing Note");
		note.setContent("This is my note where I execute post test case");

		// Save note to the database
		noteService.createNote(note);

		// Testing
		assertThat(noteRepo.existsById(1L)).isTrue();
	}

	@Test
	public void testCreateNullTitle() {
		Note note = new Note();

		note.setId(1L);
		note.setContent("New content");

		assertThatExceptionOfType(javax.validation.ConstraintViolationException.class).isThrownBy(() -> {

			noteService.createNote(note);
		}).withMessageContaining("null");

	}

	@Test
	public void testCreateNullContent() {
		Note note = new Note();

		note.setId(1L);
		note.setTitle("New Title");

		assertThatExceptionOfType(javax.validation.ConstraintViolationException.class).isThrownBy(() -> {

			noteService.createNote(note);
		}).withMessageContaining("null");
	}

	@Test
	public void testCreateEmptyTitle() {
		Note note = new Note();

		note.setId(1L);
		note.setTitle("");
		note.setContent("New content");

		assertThatExceptionOfType(javax.validation.ConstraintViolationException.class).isThrownBy(() -> {

			noteService.createNote(note);
		}).withMessageContaining("empty");
	}

	@Test
	public void testCreateEmptyContent() {
		Note note = new Note();

		note.setId(1L);
		note.setTitle("New Title");
		note.setContent("");

		assertThatExceptionOfType(javax.validation.ConstraintViolationException.class).isThrownBy(() -> {

			noteService.createNote(note);
		}).withMessageContaining("empty");
	}

	@Test
	public void testCreateExistingId() {

		Note note = new Note(1L, "New title", "New Content");
		noteService.createNote(note);

		Note secondNote = new Note(1L, "New Second Title", "New second content");
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {

			noteService.createNote(note);
		}).withMessageContaining("id");

	}

	@Test
	public void testGetAll() {

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my note where I execute my get all notes test case");

		// Save first note to the database
		noteService.createNote(note);

		// Create second note
		note.setId(2L);
		note.setTitle("Testing Second Note");
		note.setContent("This is my second note where I execute my get all notes test case");

		// Save second note to the database
		noteService.createNote(note);

		// Testing
		List<Note> list = noteRepo.findAll();
		assertThat(list).size().isEqualTo(2);
	}

	@Test
	public void testGetEmptyNotes() {

		assertThat(noteRepo.count() == 0).isTrue();
	}

	@Test
	public void testUpdate() {

		// Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my note where I execute my update test case");

		// Save note to the database
		noteService.createNote(note);

		// Create another note with same id and updated data
		Note updatedNote = new Note();
		updatedNote.setTitle("Updated my Testing Note");
		updatedNote.setContent("This is my note where I update the testing note");

		// Update the note in the database
		noteService.updateNote(1L, updatedNote);

		// Testing
		assertThat(noteRepo.findById(1L).get().getUpdatedAt()).isNotEqualTo(noteRepo.findById(1L).get().getCreatedAt());

	}

	@Test
	public void testUpdateNonExistentId() {

		Note note = new Note();
		note.setTitle("New title");
		note.setContent("New content");

		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {

			noteService.updateNote(5L, note);
		}).withMessageContaining("id");

	}

	@Test
	public void testUpdateNullTitle() {
		Note note = new Note(1L, "New Title", "New content");
		noteService.createNote(note);

		Note updatedNote = new Note();

		updatedNote.setContent("New updated content");

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {

			noteService.updateNote(1L, updatedNote);
		}).withMessageContaining("transaction");
	}

	@Test
	public void testUpdateEmptyTitle() {
		Note note = new Note(1L, "New Title", "New content");
		noteService.createNote(note);

		Note updatedNote = new Note();
		updatedNote.setTitle("");
		updatedNote.setContent("New updated content");

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {

			noteService.updateNote(1L, updatedNote);
		}).withMessageContaining("transaction");
	}

	@Test
	public void testUpdateNullContent() {
		Note note = new Note(1L, "New Title", "New content");
		noteService.createNote(note);

		Note updatedNote = new Note();

		updatedNote.setTitle("New Updated Title");

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {
			noteService.updateNote(1L, updatedNote);
		}).withMessageContaining("transaction");
	}

	@Test
	public void testUpdateEmptyContent() {
		Note note = new Note(1L, "New Title", "New content");
		noteService.createNote(note);

		Note updatedNote = new Note();
		updatedNote.setTitle("New Updated Title");
		updatedNote.setContent("");

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {
			noteService.updateNote(1L, updatedNote);
		}).withMessageContaining("transaction");
	}

	@Test
	public void testUpdateDifferentIds() {
		Note note = new Note(1L, "New Title", "New content");
		noteService.createNote(note);

		Note updatedNote = new Note();
		updatedNote.setId(2L);
		updatedNote.setTitle("New Updated Title");
		updatedNote.setContent("New updated content");

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			noteService.updateNote(1L, updatedNote);
		}).withMessageContaining("id");
	}

	@Test
	public void testDelete() {

		// Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Testing Note");
		note.setContent("This is my note where I execute my delete note by id test case");

		// Save the note in the database
		noteService.createNote(note);

		// Delete note in the database
		noteService.deleteNote(1L);

		// Testing
		assertThat(noteRepo.existsById(1L)).isFalse();
	}

	@Test
	public void testDeleteNonExistentId() {

		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			noteService.deleteNote(1L);
		}).withMessageContaining("id");
	}

	@Test
	public void testGetById() {

		// Create note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Testing Note");
		note.setContent("This is my note where I execute my get note by id test case");

		// Save note in the database
		noteService.createNote(note);

		// Testing
		assertThat(noteRepo.existsById(1L)).isTrue();
	}

	@Test
	public void testGetByNonExistentId() {
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			noteService.getNote(1L);
		}).withMessageContaining("id");
	}

	@Test
	public void testDeleteAll() {

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my delete all notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		note.setId(2L);
		note.setTitle("Second Testing Note");
		note.setContent("This is my second note where I execute my delete all notes test case");

		// Save second note in the database
		noteService.createNote(note);

		// Create third note
		note.setId(3L);
		note.setTitle("Third Testing Note");
		note.setContent("This is my third note where I execute my delete all notes test case");

		// Save third note in the database
		noteService.createNote(note);

		// Delete all notes in the database
		noteService.deleteNotes();

		// Testing
		assertThat(noteRepo.count()).isEqualTo(0);
	}

	@Test
	public void testUpdateMultiple() {

		List<Note> notes = new ArrayList<>();

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");

		// Save second note in the database
		noteService.createNote(secondNote);

		// Update first note
		note.setTitle("First Updated Testing Note");
		note.setContent("This is my first updated note where I execute my update multiple notes test case");
		notes.add(note);

		// Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		notes.add(secondNote);

		noteService.updateNotes(notes);

		// Testing
		SoftAssertions updateBundle = new SoftAssertions();
		updateBundle.assertThat(noteRepo.findById(1L).get().getUpdatedAt())
				.isNotEqualTo(noteRepo.findById(1L).get().getCreatedAt());
		updateBundle.assertThat(noteRepo.findById(2L).get().getUpdatedAt())
				.isNotEqualTo(noteRepo.findById(2L).get().getCreatedAt());
		updateBundle.assertAll();

	}

	@Test
	public void testUpdateMultipleEmptyList() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			noteService.updateNotes(new ArrayList<Note>());
		}).withMessageContaining("notes");
	}

	@Test
	public void testUpdateMultipleNullTitle() {
		List<Note> notes = new ArrayList<>();

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");

		// Save second note in the database
		noteService.createNote(secondNote);

		// Update first note
		Note updatedFirstNote = new Note();
		updatedFirstNote.setId(1L);
		updatedFirstNote.setContent("This is my first updated note where I execute my update multiple notes test case");
		notes.add(updatedFirstNote);

		// Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		notes.add(secondNote);

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {
			noteService.updateNotes(notes);
		}).withMessageContaining("transaction");

	}

	@Test
	public void testUpdateMultipleEmptyTitle() {
		List<Note> notes = new ArrayList<>();

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");

		// Save second note in the database
		noteService.createNote(secondNote);

		// Update first note
		Note updatedFirstNote = new Note();
		updatedFirstNote.setId(1L);
		updatedFirstNote.setTitle("");
		updatedFirstNote.setContent("This is my first updated note where I execute my update multiple notes test case");
		notes.add(updatedFirstNote);

		// Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		notes.add(secondNote);

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {
			noteService.updateNotes(notes);
		}).withMessageContaining("transaction");

	}

	@Test
	public void testUpdateMultipleNullContent() {
		List<Note> notes = new ArrayList<>();

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");

		// Save second note in the database
		noteService.createNote(secondNote);

		// Update first note
		Note updatedFirstNote = new Note();
		updatedFirstNote.setId(1L);
		updatedFirstNote.setTitle("This is my first updated note where I execute my update multiple notes test case");
		notes.add(updatedFirstNote);

		// Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		notes.add(secondNote);

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {
			noteService.updateNotes(notes);
		}).withMessageContaining("transaction");
	}

	@Test
	public void testUpdateMultipleEmptyContent() {
		List<Note> notes = new ArrayList<>();

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");

		// Save second note in the database
		noteService.createNote(secondNote);

		// Update first note
		Note updatedFirstNote = new Note();
		updatedFirstNote.setId(1L);
		updatedFirstNote.setContent("");
		updatedFirstNote.setTitle("This is my first updated note where I execute my update multiple notes test case");
		notes.add(updatedFirstNote);

		// Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		notes.add(secondNote);

		assertThatExceptionOfType(TransactionSystemException.class).isThrownBy(() -> {
			noteService.updateNotes(notes);
		}).withMessageContaining("transaction");
	}
	
	@Test
	public void testUpdateMultipleNonExistentId() {
		List<Note> notes = new ArrayList<>();

		// Create first note
		Note note = new Note();
		note.setId(1L);
		note.setTitle("First Testing Note");
		note.setContent("This is my first note where I execute my update multiple notes test case");

		// Save first note in the database
		noteService.createNote(note);

		// Create second note
		Note secondNote = new Note();
		secondNote.setId(2L);
		secondNote.setTitle("Second Testing Note");
		secondNote.setContent("This is my second note where I execute my update multiple notes test case");

		// Save second note in the database
		noteService.createNote(secondNote);

		// Update first note
		Note updatedFirstNote = new Note();
		updatedFirstNote.setId(5L);
		updatedFirstNote.setContent("This is my updated content");
		updatedFirstNote.setTitle("This is my first updated note where I execute my update multiple notes test case");
		notes.add(updatedFirstNote);

		// Update second note
		secondNote.setTitle("Second Updated Testing Note");
		secondNote.setContent("This is my second updated note where I execute my update multiple notes test case");
		notes.add(secondNote);

		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			noteService.updateNotes(notes);
		}).withMessageContaining("id");
	}
}
