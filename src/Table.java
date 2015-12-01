import java.util.LinkedList;
import java.util.List;

class gameHasStartedException extends Exception  {
	private static final long serialVersionUID = 1L;

	gameHasStartedException() {
		super();
	}

	gameHasStartedException(String msg) {
		super(msg);
	}
}

class sizeofSetException extends Exception  {
	private static final long serialVersionUID = 1L;

	sizeofSetException() {
		super();
	}

	sizeofSetException(String msg) {
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

public class Table extends TableAppliance {
	
	private Deck TableDeck;
	private List<Player> Players;
	private List<Integer> Credits;
	private List<Cards> PlayersCards;
	private int smallBlind;
	private int bigBlind;	
	private Limit lim;
	private int fixed;
	private int maxRaise;
	private int gameSize;
	private int dealerButton;
	private int initCredit;
	private int bank;
	Boolean gameStarted;
	Boolean blockNewPlayers;
		
		//ustawienie parametrów gry; (nie)wywołanie przygotowania gry
		Table(int gameSize, int initCredit, Limit lim, int smallBlind, int bigBlind, int fixed,int maxRaise) {
			super();
			gameStarted=false;
			blockNewPlayers=false;
			this.initCredit=initCredit;
			this.gameSize=gameSize;
			this.smallBlind=smallBlind;
			this.bigBlind=bigBlind;	
			this.lim=lim;
			this.bank=0;
			
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
		protected void newDeck() {
			TableDeck.newDeck();
		}
		//tworzy pusta talię oraz pustą liczbę graczy; gdy zgłoszona jest odpowiednia liczba graczy to uruchamia maszynę stanów
		private void startGame() throws NoofPlayersException{
			
			if(countPlayers()<2){
				throw new NoofPlayersException("Min 2 players are needed to start the game!");
			}
			
			gameStarted=true;
			newDeck();
			Players = new LinkedList<Player>();
						
			//inicjalizuje stan początkowy
			setState(startGameState);
			Auto();
			
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
		
		private void setCredit(Integer index,Integer value){
			this.Credits.set(index, value);
		}

		protected void deal(Integer sizeofSet) throws sizeofSetException {
			if (sizeofSet < 1) {
				throw new sizeofSetException("Trzeba rozdać przynajmnniej jedną kartę!");
			}

			if (sizeofSet > countDeck() / countPlayers()) {
				throw new sizeofSetException("Za mało kart w puli aby wykonać to rozdanie!");
			}
			shuffleDeck();
			Integer cardstodeal = countPlayers() * sizeofSet;

			for (Integer i = 0; i < cardstodeal; i++) {
				givePlayerACard(i % countPlayers(),TableDeck.giveCard(0));
			}

		}
		
		protected void givePlayerACard(Integer p, Card c){
			PlayersCards.get(p).addCard(c);
		}

		private Player getPlayer(Integer i) {
			return Players.get(i);
		}

		/*
		 * public List<Player> getPlayers() { return Players; }
		 */

		private Cards getDeck() {
			return TableDeck;
		}
		
		public int getSmallBlind(){
			return this.smallBlind;
		}
		
		public int getBigBlind(){
			return this.bigBlind;
		}
		
		public Limit getLimit(){
			return this.lim;
		}
		
		public int getMaxRaise(){
			return this.maxRaise;
		}
		
		public int getFixed(){
			return this.fixed;
		}
		
		public int getGameSize(){
			return this.gameSize;
		}
		
		public int getCredits(Integer p){
			return this.Credits.get(p);
		}
		
		protected void setCredits(Integer p,Integer value){
			 this.Credits.set(p,value);
		}
		
		
		
		//trzeba to zabezpieczyć (weryfikacja użytkownika)!
		public Cards getPlayerCards(Integer p){
			return this.PlayersCards.get(p);
		}
		
		protected void setPlayerCards(Integer p, Cards cards){
			this.PlayersCards.set(p, cards);
		}
		
		protected void initializeCredits(){
			for(Integer i=0;i<countPlayers();i++){
				setCredit(i,initCredit);
			}
		}
		
		protected void setdealerButton(Integer p){
			dealerButton=p;
		}
		
		public int getdealerButton(){
			return this.dealerButton;
		}
		
		protected void setBank(Integer b){
			this.bank=b;
		}
		
		public int getBank(){
			return bank;
		}
		
		
		
		
}
