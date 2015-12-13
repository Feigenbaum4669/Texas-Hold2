
public class RunOutOfActivePlayersException extends Exception  {
	private static final long serialVersionUID = 1L;

	RunOutOfActivePlayersException() {
		super();
	}

	RunOutOfActivePlayersException(String msg) {
		super(msg);
	}
}