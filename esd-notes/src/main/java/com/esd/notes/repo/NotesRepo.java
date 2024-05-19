package com.esd.notes.repo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.esd.notes.model.NoteEntity;

@Repository
public class NotesRepo {
	
	// In-memory storage only.
	private static Map<Integer, NoteEntity> notesMap = new LinkedHashMap<>();

	/**
	 *  Create a new note.
	 * @param note
	 * @return 
	 */
	public NoteEntity save(NoteEntity note) {
		
		try {
			// Put the new note in the map.
			notesMap.put(note.getId(), note);
			
			return note;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Retrieve all notes.
	 * @return
	 */
	public List<NoteEntity> findAllNotes() {
		
		List<NoteEntity> notes = new ArrayList<>();
		
		for (Map.Entry<Integer, NoteEntity> entry : notesMap.entrySet()) {
			notes.add(entry.getValue());            
        }
		
		return notes;
	}

	/**
	 * Retrieve a specific note by ID.
	 * @param id
	 * @return
	 */
	public NoteEntity findNoteById(Integer id) {
		return notesMap.get(id);
	}
	
	/**
	 * Update a specific note.
	 * @param note
	 * @return
	 */
	public boolean updateNote(NoteEntity note) {
		
		boolean txnStatus = false;
		
		try {
			NoteEntity noteRef = notesMap.get(note.getId());
			
			if (noteRef != null) {
				noteRef.setTitle(note.getTitle());
				noteRef.setBody(note.getBody());
				txnStatus = true;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return txnStatus;
	}
	
	/**
	 * Delete a specific note.
	 * @param note
	 * @return
	 */
	public boolean deleteNote(NoteEntity note) {
		
		boolean txnStatus = false;
		
		try {
			notesMap.remove(note.getId());
			txnStatus = true;	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return txnStatus;
	}

}
