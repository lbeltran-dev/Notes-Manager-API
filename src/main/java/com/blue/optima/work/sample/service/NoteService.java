package com.blue.optima.work.sample.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blueoptima.work.sample.EntityNotFoundException;
import com.blueoptima.work.sample.entity.Note;
import com.blueoptima.work.sample.repository.INoteRepo;


@Service
public class NoteService {

	@Autowired
	private INoteRepo noteRepo;

	public Note getNote(Long noteId) {
		Note note = noteRepo.findById(noteId).orElse(null);
		if (note == null) {
			throw new EntityNotFoundException(Note.class, "id", noteId.toString());
		}
		return note;
	}

	public Note createNote(Note note) {
		return noteRepo.save(note);
	}

	public List<Note> getNoteCollection() {
		return noteRepo.findAll();
	}
	
	public Note updateNote(Long noteId, Note tempNote) {
		Note note = noteRepo.findById(noteId).orElse(null);
		if(note == null) {
			throw new EntityNotFoundException(Note.class, "id", noteId.toString());
		}
		tempNote.setId(noteId);
		noteRepo.save(tempNote);
		return noteRepo.findById(noteId).get();
	}
	
	public String deleteNotes() {
		if(noteRepo.count() == 0) return "Database already empty";
		noteRepo.deleteAll();
		
		return "Notes deleted successfully";
	}
	
	public String deleteNote(Long noteId) {
		Note note = noteRepo.findById(noteId).orElse(null);
		if (note == null) {
			throw new EntityNotFoundException(Note.class, "id", noteId.toString());
		}
		
		noteRepo.delete(note);
		return "Note deleted successfully";
	}
	
	public String updateNotes(List<Note> notes) {
		for(Note note: notes) {
			Note tempNote = noteRepo.findById(note.getId()).orElse(null);
			if(tempNote == null) {
				throw new EntityNotFoundException(Note.class, "id", note.getId().toString());
			}
			
			noteRepo.save(note);
		}
		
		return "Notes updated successfully";
	}
}
