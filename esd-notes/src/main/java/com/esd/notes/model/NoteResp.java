package com.esd.notes.model;

public class NoteResp {

	private String message;
	private NoteEntity note;
	
	public NoteResp(){};
	
	public NoteResp(String message, NoteEntity note) {
		this.message = message;
		this.note = note;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public NoteEntity getNote() {
		return note;
	}
	public void setNote(NoteEntity note) {
		this.note = note;
	}	
	
}
