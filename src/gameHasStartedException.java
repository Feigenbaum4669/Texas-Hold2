
class gameHasStartedException extends Exception  {
	private static final long serialVersionUID = 1L;

	gameHasStartedException() {
		super();
	}

	gameHasStartedException(String msg) {
		super(msg);
	}
}
