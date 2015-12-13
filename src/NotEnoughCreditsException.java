
public class NotEnoughCreditsException extends Exception  {
	private static final long serialVersionUID = 1L;

	NotEnoughCreditsException() {
		super();
	}

	NotEnoughCreditsException(String msg) {
		super(msg);
	}
}