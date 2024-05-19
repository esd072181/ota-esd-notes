package com.esd.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esd.notes.model.NoteEntity;
import com.esd.notes.model.NoteResp;
import com.esd.notes.service.NotesService;
import com.esd.notes.util.NotesUtil;


@RestController
@RequestMapping("/esd")
public class NotesController {
	
	@Autowired
	private NotesService notesService;

	/**
	 * POST /notes: Create a new note.
	 * @param note
	 * @return
	 */
	@PostMapping(value = "/notes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  createNote(@RequestBody NoteEntity note) {
		
		try {
					
			// Validate inputs.
			
			if (note.getId() == null) {
				NoteResp noteRes = new NoteResp("Note id is required.", note);
				return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	
			}
			
			if (!NotesUtil.isNumeric(String.valueOf(note.getId()))) {
				NoteResp noteRes = new NoteResp("Note id should be a numeric value.", note);
				return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	
			}
			
			if (note.getTitle() == null || note.getTitle().trim().length() == 0) {
				NoteResp noteRes = new NoteResp("Note title is required.", note);
				return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	
			}
			
			if (note.getBody() == null || note.getBody().trim().length() == 0) {
				NoteResp noteRes = new NoteResp("Note body is required.", note);
				return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	
			}
			
			// Check if note already exists.
			NoteEntity noteFromRepo = notesService.getNoteById(note.getId());
			
			if (noteFromRepo == null) {
				
				NoteEntity newNote = notesService.createNote(note);
				
				if (newNote != null) {
					NoteResp noteRes = new NoteResp("Note successfully created.", newNote);
					return new ResponseEntity<>(noteRes, HttpStatus.OK);	
				}
				else {
					NoteResp noteRes = new NoteResp("Failed in creating a note.", newNote);
					return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
				}	
				
			}
			else {
				NoteResp noteRes = new NoteResp("Note alredy exists.", noteFromRepo);
				return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	

			}	
				
		} 
		catch(Exception e) {
			NoteResp noteRes = new NoteResp("Error in creating a note.", note);
			return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
		}
		
	}
	
	/**
	 * GET /notes: Retrieve all notes.
	 * @return
	 */
	@GetMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  retrieveAllNotes() {
		
		try {

			List<NoteEntity> notes = notesService.getAllNotes();
			
			if (!notes.isEmpty()) {
				return new ResponseEntity<>(notes, HttpStatus.OK);
			}
			else {
				NoteResp noteRes = new NoteResp("Notes not found.", null);
				return new ResponseEntity<>(noteRes, HttpStatus.NOT_FOUND);	
			}
		}
		catch(Exception e) {
			NoteResp noteRes = new NoteResp("Error in retrieve all notes.", null);
			return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
		}
			
	}
	
	/**
	 * GET /notes/:id: Retrieve a specific note by ID.
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/notes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveNoteById(@PathVariable Integer id) {
		
		try {
			NoteEntity note = notesService.getNoteById(id);
			
			if (note != null) {
				return new ResponseEntity<>(note, HttpStatus.OK);
			}
			else {
				NoteResp noteRes = new NoteResp("Note not found.", null);
				return new ResponseEntity<>(noteRes, HttpStatus.NOT_FOUND);	

			}	
		}
		catch(Exception e) {
			NoteResp noteRes = new NoteResp("Error in retrieving a specific note by ID.", null);
			return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
		}
	}
	
	/**
	 * PUT /notes/:id: Update a specific note.
	 * @param id
	 * @param note
	 * @return
	 */
	@PutMapping(value = "/notes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  updateNoteById(@PathVariable Integer id, @RequestBody NoteEntity note) {
		
		try {
						
			// Check if note exists.
			NoteEntity noteFromRepo = notesService.getNoteById(id);
			
			if (noteFromRepo != null) {
				
				// Validate inputs.
				
				if (note.getTitle() == null || note.getTitle().trim().length() == 0) {
					NoteResp noteRes = new NoteResp("Note title is required.", null);
					return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	
				}
				
				if (note.getBody() == null || note.getBody().trim().length() == 0) {
					NoteResp noteRes = new NoteResp("Note body is required.", null);
					return new ResponseEntity<>(noteRes, HttpStatus.FORBIDDEN);	
				}
				
				noteFromRepo.setTitle(note.getTitle());
				noteFromRepo.setBody(note.getBody());
				
				boolean isUpdated = notesService.updateNote(noteFromRepo);
				
				if (isUpdated) {
					NoteResp noteRes = new NoteResp("Note successfully updated.", noteFromRepo);
					return new ResponseEntity<>(noteRes, HttpStatus.OK);	
				}
				else {
					NoteResp noteRes = new NoteResp("Failed in updating the note.", null);
					return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
				}
				
			}
			else {
				NoteResp noteRes = new NoteResp("Note not found.", null);
				return new ResponseEntity<>(noteRes, HttpStatus.NOT_FOUND);	

			}	
			
		} 
		catch(Exception e) {
			NoteResp noteRes = new NoteResp("Error in updating the note.", null);
			return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
		}
		
	}
	
	/**
	 * DELETE /notes/:id: Delete a specific note.
	 * @param id
	 * @param note
	 * @return
	 */
	@DeleteMapping(value = "/notes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  deleteNoteById(@PathVariable Integer id) {
		
		try {
			
			// Check if note exists.
			NoteEntity noteFromRepo = notesService.getNoteById(id);
			
			if (noteFromRepo != null) {
				
				boolean isDeleted = notesService.deleteNote(noteFromRepo);
				
				if (isDeleted) {
					NoteResp noteRes = new NoteResp("Note successfully deleted.", noteFromRepo);
					return new ResponseEntity<>(noteRes, HttpStatus.OK);	
				}
				else {
					NoteResp noteRes = new NoteResp("Failed in deleting the note.", null);
					return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
				}
				
			}
			else {
				NoteResp noteRes = new NoteResp("Note not found.", null);
				return new ResponseEntity<>(noteRes, HttpStatus.NOT_FOUND);	

			}	
			
		} 
		catch(Exception e) {
			NoteResp noteRes = new NoteResp("Error in deleting the note.", null);
			return new ResponseEntity<>(noteRes, HttpStatus.EXPECTATION_FAILED);	
		}
		
	}
}
