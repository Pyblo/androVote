package pl.pg.mif.androVote.WCFConnector.Exceptions;

public class BadLoginException extends Exception {

	private static final long serialVersionUID = 6529362787997138797L;
	public String message;;
	
	public BadLoginException() {
		message = "Zły login lub hasło!";
	}
}