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

	@Override
	public void readAndSetName() {
		notify("Podaj swoje imię: ");
		setName(readFromSocket());
	}

	@Override
	public VAction makeAction(gameInfo gi) {
		setGameInfo(gi);

		VAction va = new VAction();
		notify("info ===");
		notify("* status: " + this.status);
		notify("* highestBet: " + hBet);
		notify("* Bank: " + this.bank);
		notify("* credits: " + this.Credits);
		notify("* bet: " + this.Bet);
		notify("========");
		int i = 0;
		for (Card c : PlayerCards.getCards()) {
			notify("* card " + i++ + ": " + c.getRank() + " of " + c.getSuit() + "s");
		}
		i = 0;
		for (Card c : tableCards.getCards()) {
			notify("* table card " + i++ + ": " + c.getRank() + " of " + c.getSuit() + "s");
		}
		notify("your action?");
		notify("0 - check; 1 - bet; 2 - call; 3 - raise; 4 - fold; 5 - all in; other - exit");
		Integer command = Integer.parseInt(readFromSocket());
		Integer value = 0;
		if (command == 1 || command == 3) {
			notify("how much do you want to bet?");
			value = Integer.parseInt(readFromSocket());
		}
		switch (command) {
			case 0 :
				va.action = Action.check;
				break;
			case 1 :
				va.action = Action.bet;
				break;
			case 2 :
				va.action = Action.call;
				break;
			case 3 :
				va.action = Action.raise;
				break;
			case 4 :
				va.action = Action.fold;
				break;
			case 5 :
				va.action = Action.all_in;
				break;
			case 6 :
				va.action = Action.quit;
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
