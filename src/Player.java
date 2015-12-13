import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;



public class Player {
	private Cards PlayerCards;
	private String name;
	private PlayerStatus status;
	private Integer Credits;
	private Integer Bet;
	private Integer hBet;
	private Integer bank;
	
	public static String getInput(String prompt) {
	    BufferedReader in = new BufferedReader(
	            new InputStreamReader(System.in));

	    System.out.print(prompt);
	    System.out.flush();

	    try {
	        return in.readLine();
	    } catch (IOException e) {
	        return "Error: " + e.getMessage();
	    }

	}
	
	Console console=System.console();

	public Player(String name) {
		PlayerCards = new Cards();
		this.name=name;
		System.out.println("Player created:"+this.name);
	}

	public void takeCard(Card c) {
		PlayerCards.addCard(c);
	}

	public Card showCard(Integer i) {
		return PlayerCards.getCard(i);
	}

	public Cards getCards() {
		return PlayerCards;
	}

	public Integer countCards() {
		return PlayerCards.sizeof();
	}
	
	public VAction makeAction(gameInfo gi){
		this.PlayerCards=gi.PlayerCards;
		this.status=gi.ThisPlayerStatus;
		this.Credits=gi.ThisPlayerCredits;
		this.Bet=gi.ThisPlayerBets;
		this.hBet=gi.highestBet;
		this.bank=gi.bank;
		VAction va= new VAction();
		System.out.println("Make decision "+this.name);
		System.out.println("INFO: status: "+this.status+" highestBet: "+hBet+" Bank: "+this.bank+" credits: "+this.Credits+" bet: "+this.Bet);
		System.out.println("command:");
		int i=Integer.parseInt(getInput(""));
		System.out.println("value:");
		int j=Integer.parseInt(getInput(""));
		switch(i){
		case 0:
		va.action=Action.check;
		break;
		case 1:
		va.action=Action.bet;
		break;
		case 2:
		va.action=Action.call;
		break;
		case 3:
		va.action=Action.raise;
		break;
		case 4:
		va.action=Action.fold;
		break;
		case 5:
		va.action=Action.all_in;
		break;
					}	
		va.value=j;
		return va;
		}
	public void notify(String msg){
		System.out.println("Notka: "+msg+"\n");
	}
}
