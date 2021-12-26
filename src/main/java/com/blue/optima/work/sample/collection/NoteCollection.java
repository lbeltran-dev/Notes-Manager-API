package com.blue.optima.work.sample.collection;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoteCollection {

	@NotNull
	@Size(min = 2)
	private List<Long> noteIds;

	public List<Long> getNotesIds() {
		return noteIds;
	}

	public void setNotesIds(List<Long> notes) {
		this.noteIds = notes;
	}

}
