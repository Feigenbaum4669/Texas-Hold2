import java.util.LinkedList;
import java.util.List;

class gameHasStartedException extends Exception {
	private static final long serialVersionUID = 1L;

	gameHasStartedException() {
		super();
	}

	gameHasStartedException(String msg) {
		super(msg);
	}
}

class NoofPlayersException extends Exception {
	private static final long serialVersionUID = 1L;

	NoofPlayersException() {
		super();
	}

	NoofPlayersException(String msg) {
		super(msg);
	}
}

public class Table {
	
		private Deck TableDeck;
		private List<Player> Players;
		
		private int smallBlind;
		private int bigBlind;
		
		private Limit lim;
		private int fixed;
		private int maxRaise;
		private int gameSize;
		Boolean gameStarted;
		Boolean blockNewPlayers;
		
		//ustawienie parametrów gry; (nie)wywołanie przygotowania gry
		Table(int gameSize, Limit lim, int smallBlind, int bigBlind, int fixed,int maxRaise) {
			
			gameStarted=false;
			blockNewPlayers=false;
			this.gameSize=gameSize;
			
			this.smallBlind=smallBlind;
			this.bigBlind=bigBlind;
			
			this.lim=lim;
			
			if(this.lim==Limit.fixed_limit){
			this.fixed=fixed;
			this.maxRaise=maxRaise;
			}else
			{
				this.fixed=0;
				this.maxRaise=0;
			}		
			//startGame();
		

		}
		//tworzy nową talię
		private void newGame() {
			TableDeck.newDeck();
		}
		//tworzy pusta talię oraz pustą liczbę graczy; gdy zgłoszona jest odpowiednia liczba graczy to uruchamia maszynę stanów
		private void startGame() throws NoofPlayersException{
			
			if(countPlayers()<2){
				throw new NoofPlayersException("Min 2 players are needed to start the game!");
			}
			
			gameStarted=true;
			TableDeck = new Deck();
			Players = new LinkedList<Player>();
						
			//tworzy maszyne stanów
			TableContext TableStateMachine=new TableContext();
			//inicjaluzje maszynę stanów stanem początkowym
			TableStateMachine.setState(new TableState startGame);
			
		}
		

		public void addPlayer(Player g) throws gameHasStartedException, NoofPlayersException  {
			if(gameStarted){
				throw new gameHasStartedException("You cannot join the game which has started!");
			}
			if(countPlayers()==10){
				throw new NoofPlayersException("Maximum no of players is 10, sorry!");
			}
			Players.add(g);
		}

		/*public void addPlayers(Integer NoofPlayers) throws NoofPlayersException {
			if (NoofPlayers < 1) {
				throw new NoofPlayersException("There must be at least one player!");
			}
			for (Integer i = 0; i < NoofPlayers; i++) {
				addPlayer(new Player());
			}
		}
		*/

		public Integer countPlayers() {
			return Players.size();
		}

		private Integer countDeck() {
			return TableDeck.sizeof();
		}

		/*public Integer countAllPlayersCards() {
			Integer count = 0;
			for (Integer i = 0; i < countPlayers(); i++) {
				count += Players.get(i).countCards();
			}
			return count;
		}
		*/

		private void shuffleDeck() {
			TableDeck.shuffle();
		}

		/*private void deal(Integer sizeofSet) throws sizeofSetException {
			if (sizeofSet < 1) {
				throw new sizeofSetException("Trzeba rozdać przynajmnniej jedną kartę!");
			}

			if (sizeofSet > countDeck() / countPlayers()) {
				throw new sizeofSetException("Za mało kart w puli aby wykonać to rozdanie!");
			}
			shuffleDeck();
			Integer cardstodeal = countPlayers() * sizeofSet;

			for (Integer i = 0; i < cardstodeal; i++) {
				Players.get(i % countPlayers()).takeCard(TableDeck.giveCard(0));
			}

		}*/

		private Player getPlayer(Integer i) {
			return Players.get(i);
		}

		/*
		 * public List<Player> getPlayers() { return Players; }
		 */

		private Cards getDeck() {
			return TableDeck;
		}
		
		public int getSmall_Blind(){
			return this.smallBlind;
		}
		
		public int getBig_Blind(){
			return this.bigBlind;
		}
		
		public Limit getLimit(){
			return this.lim;
		}
		
		public int getMax_Raise(){
			return this.maxRaise;
		}
		
		public int getFixed(){
			return this.fixed;
		}
		
		public int getGameSize(){
			return this.gameSize;
		}
}
