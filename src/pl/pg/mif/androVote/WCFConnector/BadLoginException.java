package pl.pg.mif.androVote.WCFConnector;

public class BadLoginException extends Exception {

	private static final long serialVersionUID = 1L;
	public String message;
	
	public BadLoginException() {
		message = "Zły login lub hasło!";
	}
}