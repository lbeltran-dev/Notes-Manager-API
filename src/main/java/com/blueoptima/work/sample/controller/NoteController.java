package com.blueoptima.work.sample.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blue.optima.work.sample.service.NoteService;
import com.blueoptima.work.sample.EntityNotFoundException;
import com.blueoptima.work.sample.entity.Note;

@Validated
@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/api/notes")
	public Note saveNote(@RequestBody @Valid Note note) {
		return noteService.createNote(note);
	}

	@GetMapping("/api/notes")
	public List<Note> getAllNotes() {
		return noteService.getNoteCollection();
	}

	@GetMapping("/api/notes/{id}")
	public Note getSingleNote(@PathVariable("id") Long id) throws EntityNotFoundException {
		return noteService.getNote(id);
	}

	@PutMapping("api/notes/{id}")
	public Note updateNote(@PathVariable Long id, @RequestBody @Valid Note note) throws EntityNotFoundException {
		return noteService.updateNote(id, note);
	}

	@DeleteMapping("api/notes/{id}")
	public String deleteNote(@PathVariable Long id) throws EntityNotFoundException {
		return noteService.deleteNote(id);
	}

	@DeleteMapping("api/notes")
	public String deleteNotes() {
		return noteService.deleteNotes();
	}

	@PutMapping("api/bulkupdate/notes")
	public String updateNotes(
			 @RequestBody @NotEmpty(message = "List of notes cannot be empty") List<@Valid Note> notes)
			throws EntityNotFoundException {
		return noteService.updateNotes(notes);
	}
}
