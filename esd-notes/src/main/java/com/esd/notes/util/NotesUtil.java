package com.esd.notes.util;

public class NotesUtil {

	public static boolean isNumeric(String str) {
		
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
	
}
