import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketPlayer extends Player {
	PrintWriter out;
	BufferedReader in;

	public SocketPlayer() throws IOException {
		// TODO Auto-generated constructor stub
		super();

		ServerSocket listener = new ServerSocket(33223);
		// try {
		Socket socket = null;
		log("Waiting for player to connect to socket");
		while (socket == null) {
			try {
				socket = listener.accept();
			} finally {// try {
				out = new PrintWriter(socket.getOutputStream(),
						true);
				out.println("hi");
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				System.out.println(readFromSocket());
			}
			readAndSetName();
			// } finally {
			// socket.close();
			// }
		}
		// } finally {
		// listener.close();
		// }
	}

	private String readFromSocket() {
		String input = null;
		try {
			input = in.readLine();
		} catch (IOException e) {
			// TODO disco?
		}
		if (input == null || input.equals(".")) {
			// disco
		}
		logReceived(input);
		return input;

	}
	
	protected void setGameInfo(GameInfo gi) {
		super.setGameInfo(gi);
		notify(gameInfo.playersInfo());
		notify(gameInfo.toString());
	}

	@Override
	public void readAndSetName() {
		notify("Podaj swoje imię: ");
		setName(readFromSocket());
	}

	@Override
	public VAction makeAction(GameInfo gi) {
		setGameInfo(gi);

		notify(gi.tableCardsInfo());
		notify(gi.cardsInfo());
		
		VAction va = new VAction();
		notify("your action?");
		notify("0 - check; 1 - bet; 2 - call; 3 - raise; 4 - fold; 5 - all in; other - exit");
		
		String command = readFromSocket();
		Integer value = 0;		
		while (va.action == null) switch (command) {
			case "0":
			case "check":
				va.action = Action.check;
				break;
			case "1":
			case "bet":
				va.action = Action.bet;
				notify("how much do you want to bet?");
				value = Integer.parseInt(readFromSocket());
				break;
			case "2":
			case "call":
				va.action = Action.call;
				break;
			case "3":
			case "raise":
				va.action = Action.raise;
				notify("how much do you want to bet?");
				value = Integer.parseInt(readFromSocket());
				break;
			case "4":
			case "fold":
				va.action = Action.fold;
				break;
			case "5":
			case "all in":
				va.action = Action.all_in;
				break;
			case "6":
			case "quit":
				va.action = Action.quit;
				break;
			default:
				notify("response not understood");
				break;
		}
		va.value = value;
		return va;
	}

	@Override
	public void notify(String msg) {
		out.println(msg);
		logSent(msg);
	}

	public void logSent(String msg) {
		log("Sent: " + msg);
	}

	public void logReceived(String msg) {
		log("Received: " + msg);
	}
}
