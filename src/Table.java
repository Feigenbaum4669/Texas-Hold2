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

public class Table  {
	
	private Deck TableDeck;
	private Cards CardsOnTable;
	private List<Player> Players;
	private List<Integer> Credits;
	private List<Integer> Bets;
	private List<Cards> PlayersCards;
	private List<Boolean> AllIn;
	private List<Boolean> Folded;
	private List<Boolean> Active;
	private int smallBlind;
	private int bigBlind;	
	private Limit lim;
	private int fixed;
	private int maxRaise;
	private int gameSize;
	private int dealerButton;
	private int initCredit;
	private int bank;
	private int highestBet;
	private int lastActivePlayer;
	private gameInfo gi;
	private InitParams ip;
	Boolean gameStarted;
	Boolean blockNewPlayers;
	
	private TableState TState;
	
	public startGame startGameState;
	public CollectBigBlind collectBigBlindState;
	public CollectSmallBlind collectSmallBlindState;
	public FirstBid FirstBidState;
	public FirstDeal FirstDealState;
	public Flop FlopState;
	public LastBid LastBidState;
	public River RiverState;
	public SecondBid SecondBidState;
	public SumUp SumUpState;
	public ThirdBid ThirdBidState;
	public Turn TurnState;
	public NewDeal NewDealState;
	public stopGame stopGameState;
		
		//ustawienie parametrów gry; (nie)wywołanie przygotowania gry
		Table(int gameSize, int initCredit, Limit lim, int smallBlind, int bigBlind, int fixed,int maxRaise) {
			ip=new InitParams(gameSize,initCredit,lim,smallBlind,bigBlind,fixed,maxRaise);
			gi=new gameInfo(ip);
			initializeTableStates();
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
		public void newDeck() {
			TableDeck.newDeck();
		}
		//tworzy pusta talię oraz pustą liczbę graczy; gdy zgłoszona jest odpowiednia liczba graczy to uruchamia maszynę stanów
		public void startGame() throws NoofPlayersException{
			
			if(countPlayers()<2){
				throw new NoofPlayersException("Min 2 players are needed to start the game!");
			}
			
			gameStarted=true;
			newDeck();
			//Players = new LinkedList<Player>();
						
			//inicjalizuje stan początkowy
			setState(startGameState);
			//Auto();
			
		}
		

		public void addPlayer(Player g) throws gameHasStartedException, NoofPlayersException  {
			if(gameStarted){
				throw new gameHasStartedException("You cannot join the game which has started!");
			}
			if(countPlayers()==10){
				throw new NoofPlayersException("Maximum no of players is 10, sorry!");
			}
			Players.add(g);
			Active.add(true);
			AllIn.add(false);
			Folded.add(false);
		}

		public Integer countPlayers() {
			return Players.size();
		}

		public Integer countDeck() {
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

		public void shuffleDeck() {
			TableDeck.shuffle();
		}
		
		public void setCredit(Integer index,Integer value){
			this.Credits.set(index, value);
		}

		public void deal(Integer sizeofSet) throws sizeofSetException {
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
		
		public Card takeCardFromDeck(){
			return this.TableDeck.giveCard(0);
		}
		
		public void givePlayerACard(Integer p, Card c){
			PlayersCards.get(p).addCard(c);
		}

		public Player getPlayer(Integer i) {
			return Players.get(i);
		}

		/*
		 * public List<Player> getPlayers() { return Players; }
		 */

		public Cards getDeck() {
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
		
		public void setCredits(Integer p,Integer value){
			 this.Credits.set(p,value);
		}
		
		public Cards getPlayerCards(Integer p){
			return this.PlayersCards.get(p);
		}
		
		public List<Integer> getBets(){
			return Bets;
		}
		
		public void setBet(Integer p, Integer val){
			Bets.set(p, val);
		}
		
		public List<Boolean> getAllIn(){
			return AllIn;
		}
		
		public void setAllIn(Integer p, Boolean val){
			AllIn.set(p, val);
		}
		
		public List<Boolean> getActive(){
			return Active;
		}
		
		public void setActive(Integer p, Boolean val){
			Active.set(p, val);
		}
		
		public List<Boolean> getFolded(){
			return Folded;
		}
		
		public void setFolded(Integer p, Boolean val){
			Folded.set(p, val);
		}
		
		
		public void setPlayerCards(Integer p, Cards cards){
			this.PlayersCards.set(p, cards);
		}
		
		public void initializeCredits(){
			for(Integer i=0;i<countPlayers();i++){
				setCredit(i,initCredit);
			}
		}
		
		public void setdealerButton(Integer p){
			dealerButton=p;
		}
		
		public int getdealerButton(){
			return this.dealerButton;
		}
		
		public void setBank(Integer b){
			this.bank=b;
		}
		
		public int getBank(){
			return bank;
		}
		
		public Cards getCardsOnTable(){
			return this.CardsOnTable;
		}
		
		public void setCardsOnTable(Cards c){
			this.CardsOnTable=c;
		}
		public void addCardOnTable(Card c){
			this.CardsOnTable.addCard(c);
		}
		
		void initializeTableStates(){
			startGameState=new startGame();
			 collectBigBlindState=new CollectBigBlind();
			 collectSmallBlindState=new CollectSmallBlind();
			 FirstBidState=new FirstBid();
			 FirstDealState=new FirstDeal();
			FlopState=new Flop();
			 LastBidState=new LastBid();
			RiverState=new River();
			SecondBidState=new SecondBid();
			 SumUpState=new SumUp();
			 ThirdBidState=new ThirdBid();
			 TurnState=new Turn();
			 NewDealState=new NewDeal();
			 stopGameState=new stopGame();
		}
		
		public void setState(TableState state ){
			TState=state;
			Auto();
			
		}
		
		public TableState getState(){
			return TState;
		}
		
		public void Auto(){
			TState.Auto(this);
		}
		
		public void end(){
			//...
		}
		
		public Integer findHighestBet(){
		Integer hb=this.Bets.get(0);
		for(int i=1;i<Bets.size();i++){
			if(Bets.get(i)>hb){
				hb=Bets.get(i);
			}
		}
		this.highestBet=hb;
		return hb;
		}
		
		public void setHighestBet(Integer hb){
			this.highestBet=hb;
		}
		
		public Integer getHighestBet(){
			return this.highestBet;
		}
		
		public Integer getlastActivePlayer(){
			return this.lastActivePlayer;
		}
		
		public void setlastActivePlayer(Integer lap){
		this.lastActivePlayer=lap;
		}
		
		public Integer nextActivePlayer(Integer currplayer){
			Integer next=-1;
			for(int i=currplayer+1;i<this.countPlayers();i++){
				if(this.Active.get(i)==true){
					next=i;
					break;
				}
			}
			return next;
		}
		
		public Integer nextPlayer(Integer currplayer,Integer incr){
			return (currplayer+incr) %countPlayers();
		}
			
			
		
		public gameInfo getgameInfo(Integer player){
			gi.bank=this.bank;
			gi.TState=this.TState;
			gi.CardsOnTable=this.CardsOnTable;
			gi.Credits=this.Credits;
			gi.PlayerCards=this.getPlayerCards(player);
			gi.bank=this.bank;
			gi.gameStarted=this.gameStarted;
			gi.dealerButton=this.dealerButton;
			gi.noofPlayers=this.countPlayers();
			gi.Bets=this.Bets;
			gi.highestBet=this.findHighestBet();
			gi.AllIn=this.AllIn;
			gi.Folded=this.Folded;
			gi.Active=this.Active;
			return this.gi;
		}
		
		
		
}
