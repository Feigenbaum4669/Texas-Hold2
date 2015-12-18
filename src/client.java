import java.io.IOException;

public class client {

	public static void main(String[] args) {
		int gameSize = 4;
		int initCredit = 200;
		Limit lim = Limit.no_limit;
		int smallBlind = 10;
		int bigBlind = 20;
		int fixed = 2;
		int maxRaise = 20;

		Player p0 = new Bot("bot1");
		// Player p1=new ServerSidePlayer("p1");
		// Player p2=new ServerSidePlayer("p2");
		Player p3 = null;
		try {
			p3 = new SocketPlayer();
		} catch (IOException e) {
			System.out.println("player not connected");
			System.out.println(e.toString());
		};

		Table tab = new Table(gameSize, initCredit, lim, smallBlind,
				bigBlind, fixed, maxRaise);
		try {
			tab.addPlayer(p0);
			// tab.addPlayer(p1);
			// tab.addPlayer(p2);
			tab.addPlayer(p3);
		} catch (GameHasStartedException e) {
			System.out.println(e.getMessage());
		} catch (NumberOfPlayersException e) {
			System.out.println(e.getMessage());
		};
		
		try {
			tab.startGame();
		} catch (NumberOfPlayersException e) {
			System.out.println(e.getMessage());
		};

	}

}
