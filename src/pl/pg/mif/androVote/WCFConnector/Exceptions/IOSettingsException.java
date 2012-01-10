package pl.pg.mif.androVote.WCFConnector.Exceptions;

public class IOSettingsException extends Exception {

	private static final long serialVersionUID = -5172242273940798841L;
	public String message;
	
	public IOSettingsException()
	{
		message = "Nie można załadować ustawień, błąd wejścia/wyjścia.";
	}
}
