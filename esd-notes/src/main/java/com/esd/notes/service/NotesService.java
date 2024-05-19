package com.esd.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.notes.model.NoteEntity;
import com.esd.notes.repo.NotesRepo;

@Service
public class NotesService {
	
	@Autowired
	private NotesRepo notesRepo;

	/**
	 * Create a new note.
	 * @param note
	 * @return
	 */
	public NoteEntity createNote(NoteEntity note) {
		return notesRepo.save(note);
	}
	
	/**
	 * Retrieve all notes.
	 * @return
	 */
	public List<NoteEntity> getAllNotes() {
		return notesRepo.findAllNotes();
	}
	
	/**
	 * Retrieve a specific note by ID.
	 * @param id
	 * @return
	 */
	public NoteEntity getNoteById(Integer id) {
		return notesRepo.findNoteById(id);
	}
	
	/**
	 * Update a specific note.
	 * @param note
	 * @return
	 */
	public boolean updateNote(NoteEntity note) {
		return notesRepo.updateNote(note);
	}
	
	/**
	 * Delete a specific note.
	 * @param note
	 * @return
	 */
	public boolean deleteNote(NoteEntity note) {
		return notesRepo.deleteNote(note);
	}
	
}
