package com.pg.androVote.WCFConnector;

public class BadLoginException extends Exception {

	private static final long serialVersionUID = 1L;
	public String message;
	
	public BadLoginException() {
		message = "Z³y login lub has³o.";
	}
}