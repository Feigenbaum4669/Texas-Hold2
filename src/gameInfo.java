import java.util.List;
public class gameInfo {
	
	public TableState TState;
	public InitParams initpar;//parametry początkowe gry
	public List <PlayerStatus> PlayersStatus;
	public Cards CardsOnTable;
	public List<Integer> Credits;
	public Cards PlayerCards;
	public int bank;
	public boolean gameStarted;
	public int dealerButton;
	public int noofPlayers;
	public List<Integer> Bets;
	public Integer highestBet;
	public int noofNonFoldedPlayers;
	public int noofNonFoldedNonAllInPlayers;
	public int lastActivePlayer;
	public int ThisPlayerCredits;
	public int ThisPlayerBets;
	public PlayerStatus ThisPlayerStatus;
	
	
	gameInfo(InitParams initpar){
		this.initpar=initpar;
	}
	
	

}
