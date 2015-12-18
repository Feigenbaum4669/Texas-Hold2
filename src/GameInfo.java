import java.util.LinkedList;
import java.util.List;
public class GameInfo {
	// state
	public TableState tableState;
	public boolean gameStarted;
	public InitParams initParams;// parametry początkowe gry

	// players
	public int numOfPlayers;
	public List<String> playersNames;
	public List<PlayerStatus> playersStates;
	public List<Integer> playersBets;
	public List<Integer> playersCredits;
	public int numOfNonFoldedPlayers;
	public int numOfNonFoldedNonAllInPlayers;
	public int lastActivePlayer;

	// this player
	public int playerCredits;
	public int playerBets;
	public PlayerStatus playerStatus;
	public Cards playerCards;

	// table
	public Cards cardsOnTable;
	public int pot;
	public Integer highestBet;
	public int dealerButton;

	public GameInfo(InitParams initpar, List<SystemPlayer> players) {
		this.initParams = initpar;
		playersNames = new LinkedList<String>();
		for (SystemPlayer player : players) {
			playersNames.add(player.getName());
		}
	}

	public String playersInfo() {
		String string = new String("=== players ==="
				+ "\n= numOfPlayers: " + numOfPlayers
				+ "\n= playersNames: " + playersNames
				+ "\n= playersStates: " + playersStates
				+ "\n= playersBets: " + playersBets
				+ "\n= playersCredits: " + playersCredits
				+ "\n= numOfNonFoldedPlayers: "
				+ numOfNonFoldedPlayers
				+ "\n= numOfNonFoldedNonAllInPlayers: "
				+ numOfNonFoldedNonAllInPlayers
				+ "\n= lastActivePlayer: " + lastActivePlayer
				+ "\n======");
		return string;
	}

	public String cardsInfo() {
		String string = new String("=== cards ===");
		int i = 0;
		for (Card c : playerCards.getCards()) {
			string += "\n= card " + i++ + ": " + c.getRank()
					+ " of " + c.getSuit() + "s";
		}
		return string;
	}

	public String cardsInfo(int player) {
		String string = new String("=== " + playersNames.get(player)
				+ "'s cards ===");
		int i = 0;
		for (Card c : playerCards.getCards()) {
			string += "\n= " + player + ".card" + i++ + ": "
					+ c.getRank() + " of " + c.getSuit()
					+ "s";
		}
		return string;
	}

	public String tableCardsInfo() {
		String string = new String("=== cards on table ===");
		int i = 0;
		for (Card c : cardsOnTable.getCards()) {
			string += "\n= table.card" + i++ + ": " + c.getRank()
					+ " of " + c.getSuit() + "s";
		}
		return string;
	}

	@Override
	public String toString() {
		String string = new String();
		string += "tableState: " + tableState;
		string += "\nplayerStatus: " + playerStatus;
		return string;
	}

}
