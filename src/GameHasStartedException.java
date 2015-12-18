
class GameHasStartedException extends Exception  {
	private static final long serialVersionUID = 1L;

	GameHasStartedException() {
		super();
	}

	GameHasStartedException(String msg) {
		super(msg);
	}
}
