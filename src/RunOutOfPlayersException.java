

	public class RunOutOfPlayersException extends Exception  {
		private static final long serialVersionUID = 1L;

		RunOutOfPlayersException() {
			super();
		}

		RunOutOfPlayersException(String msg) {
			super(msg);
		}
	}

