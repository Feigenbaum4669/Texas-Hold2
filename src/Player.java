import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

abstract public class Player {
	protected Cards PlayerCards;
	protected String name;
	protected PlayerStatus status;
	protected Integer Credits;
	protected Integer Bet;
	protected Integer hBet;
	protected Integer bank;
	protected Cards tableCards;
	
	abstract public void readAndSetName();
	abstract public VAction makeAction(gameInfo gi);
	abstract public void notify(String msg);

	public Player() {
		PlayerCards = new Cards();
		this.name = "Unnamed Player";
		log("Player created: \"" + this.name + "\".");
	}

	public void setName(String name) {
		log("Player \"" + this.name + "\" renamed to \"" + name
				+ "\".");
		this.name = name;
	}

	protected void setGameInfo(gameInfo gi) {
		this.PlayerCards = gi.PlayerCards;
		this.status = gi.ThisPlayerStatus;
		this.Credits = gi.ThisPlayerCredits;
		this.Bet = gi.ThisPlayerBets;
		this.hBet = gi.highestBet;
		this.bank = gi.bank;
		this.tableCards = gi.CardsOnTable;
	}
	
	protected void log(String message) {
		System.out.println(message);
	}

	public void setCard(Card c) {
		PlayerCards.addCard(c);
	}

	public Card getCard(Integer i) {
		return PlayerCards.getCard(i);
	}

	public Cards getCards() {
		return PlayerCards;
	}

	public Integer countCards() {
		return PlayerCards.sizeof();
	}
}
