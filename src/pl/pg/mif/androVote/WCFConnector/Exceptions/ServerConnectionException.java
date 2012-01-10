package pl.pg.mif.androVote.WCFConnector.Exceptions;

public class ServerConnectionException extends Exception {

	private static final long serialVersionUID = -4358669880778239777L;
	public String message;
	
	public ServerConnectionException()
	{
		message = "Błąd podczas połączenia z serwerem. Sprawdź swoje łącze oraz ustawienia adresu i portu serwera";
	}
}
