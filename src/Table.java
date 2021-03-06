import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Table {
	// głowne pola
	private Deck TableDeck;
	private Cards CardsOnTable;
	private LinkedList<SystemPlayer> Players;
	private LinkedList<Integer> Credits;
	private LinkedList<Integer> Bets;
	private LinkedList<Cards> PlayersCards;
	private LinkedList<PlayerStatus> PlayersStatus;
	private int smallBlind;
	private int bigBlind;
	private Limit lim;
	private int fixed;// max ilośc podbic na 1 runde licytacji
	private int maxRaise; // max wartośc o jaką można podbić
	private int gameSize;
	private int dealerButton;
	private int initCredit;
	private int bank;
	private int highestBet;
	private int lastActivePlayer;
	private GameInfo gi;
	private InitParams ip;
	private boolean exitSignal;
	private Boolean gameStarted;
	// aktualny stan stołu
	private TableState TState;
	// stany maszyny stanów stołu
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
	public NewTurn NewTurnState;
	public stopGame stopGameState;

	// ustawienie parametrów gry;
	public Table(int gameSize, int initCredit, Limit lim, int smallBlind,
			int bigBlind, int fixed, int maxRaise) {
		this.exitSignal = false;
		this.gameStarted = false;

		this.Players = new LinkedList<SystemPlayer>();
		this.Credits = new LinkedList<Integer>();
		this.Bets = new LinkedList<Integer>();
		this.PlayersCards = new LinkedList<Cards>();
		this.PlayersStatus = new LinkedList<PlayerStatus>();
		this.TableDeck = new Deck();
		this.CardsOnTable = new Cards();

		this.ip = new InitParams(gameSize, initCredit, lim, smallBlind,
				bigBlind, fixed, maxRaise);
		this.gi = new GameInfo(ip, Players);
		initializeTableStates();;
		this.initCredit = initCredit;
		this.gameSize = gameSize;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.lim = lim;
		this.bank = 0;

		if (this.lim == Limit.fixed_limit) {
			this.fixed = fixed;
			this.maxRaise = maxRaise;
		} else {
			this.fixed = 0;
			this.maxRaise = 0;
		}
	}

	// tworzy nową talię
	public void newDeck() {
		this.TableDeck.newDeck();

	}

	// tworzy pusta talię oraz pustą liczbę graczy; gdy zgłoszona jest
	// odpowiednia liczba graczy to uruchamia maszynę stanów
	public void startGame() throws NumberOfPlayersException {

		if (countPlayers() < 2) {
			throw new NumberOfPlayersException(
					"Min 2 players are needed to start the game!");
		}

		gameStarted = true;
		// newDeck();
		// Players = new LinkedList<Player>();

		// inicjalizuje stan początkowy
		setState(startGameState);
		while (exitSignal == false) {
			System.out.println("Table state: " + this.TState);
			passGameInfo();
			Auto();
		}

	}

	public void passGameInfo() {
		try {
			int p = this.nextNonQuitPlayer(0);
			for (int i = 0; i < this.countNonQuitPlayers(); i++) {
				this.getgameInfo(p);
				this.getSystemPlayer(p).passGameInfo(gi);
				p = this.nextNonQuitPlayer(p);
			}
		} catch (RunOutOfPlayersException ex) {
			// ...
		}
	}

	public void addPlayer(Player g) throws GameHasStartedException,
			NumberOfPlayersException {
		if (gameStarted) {
			throw new GameHasStartedException(
					"You cannot join the game which has started!");
		}
		if (countPlayers() == 10) {
			throw new NumberOfPlayersException(
					"Maximum no of players is 10, sorry!");
		}
		Players.add(new SystemPlayer(g));
	}

	public int countPlayers() {
		return Players.size();
	}

	public int countDeck() {
		return TableDeck.sizeof();
	}

	public void shuffleDeck() {
		TableDeck.shuffle();
	}

	public void setCredit(Integer index, Integer value) {
		this.Credits.set(index, value);
	}

	public void deal(Integer sizeofSet)
			throws sizeofSetException, RunOutOfPlayersException {
		if (sizeofSet < 1) {
			throw new sizeofSetException(
					"Trzeba rozdać przynajmnniej jedną kartę!");
		}
		System.out.println("CNFP: " + countNonQuitPlayers() + " "
				+ countDeck());

		if (sizeofSet > (countDeck() / countNonQuitPlayers())) {
			throw new sizeofSetException(
					"Za mało kart w puli aby wykonać to rozdanie!");
		}
		shuffleDeck();

		Integer k = nextNonQuitPlayer(0);

		for (Integer i = 0; i < countNonQuitPlayers(); i++) {
			k = nextNonQuitPlayer(k);
			for (Integer j = 0; j < sizeofSet; j++) {
				Players.get(k).takeCard(TableDeck.giveCard(0));
			}

			// givePlayerACard(i %
			// countNonFoldedPlayers(),TableDeck.giveCard(0));
		}

	}
	public Integer countNonQuitPlayers() {
		Integer nfp = 0;
		for (Integer i = 0; i < countPlayers(); i++) {
			if (Players.get(i)
					.getPlayerStatus() != PlayerStatus.quit) {
				nfp++;
			}
		}
		return nfp;
	}

	public Integer countNonFoldedPlayers() {
		Integer nfp = 0;
		for (Integer i = 0; i < countPlayers(); i++) {
			if ((Players.get(i)
					.getPlayerStatus() != PlayerStatus.folded)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.quit)) {
				nfp++;
			}
		}
		return nfp;
	}

	public Integer countActivePlayers() {
		Integer ap = 0;
		for (Integer i = 0; i < countPlayers(); i++) {
			if ((Players.get(i)
					.getPlayerStatus() != PlayerStatus.folded)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.all_in)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.quit)) {
				ap++;
			}
		}
		return ap;
	}

	public Card takeCardFromDeck() {
		return this.TableDeck.giveCard(0);
	}
	/*
	 * public void givePlayerACard(Integer p, Card c){
	 * PlayersCards.get(p).addCard(c); this.getSystemPlayer(p).takeCard(c);
	 * }
	 */

	public SystemPlayer getSystemPlayer(Integer i) {
		return Players.get(i);
	}

	public Cards getDeck() {
		return TableDeck;
	}

	public int getSmallBlind() {
		return this.smallBlind;
	}

	public int getBigBlind() {
		return this.bigBlind;
	}

	public Limit getLimit() {
		return this.lim;
	}

	public int getMaxRaise() {
		return this.maxRaise;
	}

	public int getFixed() {
		return this.fixed;
	}

	public int getGameSize() {
		return this.gameSize;
	}

	public int getCredits(Integer p) {
		return this.getSystemPlayer(p).getPlayerCredits();
	}

	public void setCredits(Integer p, Integer value) {
		this.getSystemPlayer(p).setPlayerCredits(value);
	}

	public Cards getPlayerCards(Integer p) {
		return this.getSystemPlayer(p).getCards();
	}
	/*
	 * public List<Integer> getBets(){ return Bets; }
	 * 
	 * public void setBet(Integer p, Integer val){ Bets.set(p, val); }
	 * 
	 * public List<Boolean> getAllIn(){ return AllIn; }
	 * 
	 * public void setAllIn(Integer p, Boolean val){ AllIn.set(p, val); }
	 * 
	 * public List<Boolean> getActive(){ return Active; }
	 * 
	 * public void setActive(Integer p, Boolean val){ Active.set(p, val); }
	 * 
	 * public List<Boolean> getFolded(){ return Folded; }
	 * 
	 * public void setFolded(Integer p, Boolean val){ Folded.set(p, val); }
	 * 
	 * 
	 * public void setPlayerCards(Integer p, Cards cards){
	 * this.PlayersCards.set(p, cards); }
	 */

	public void initializeCredits() {
		for (Integer i = 0; i < countPlayers(); i++) {
			Players.get(i).setPlayerCredits(initCredit);
		}
	}

	public Card[] getTabofTableCards() {
		Card[] tableCards = new Card[5];
		// int size=this.CardsOnTable.sizeof();
		for (int i = 0; i < 5; i++) {
			tableCards[i] = this.CardsOnTable.getCard(i);
		}
		return tableCards;
	}

	public Card[] getTabofPlayerCards(int p) {
		Card[] playerCards = new Card[2];
		// int size=this.getSystemPlayer(p).getCards().sizeof();
		for (int i = 0; i < 2; i++) {
			playerCards[i] = this.getSystemPlayer(p).getCards()
					.getCard(i);
		}
		return playerCards;
	}

	public LinkedList<Integer> getPlayersBets() {
		this.fill();
		return this.Bets;
	}

	public void setdealerButton(Integer p) {
		dealerButton = p;
	}

	public int getdealerButton() {
		return this.dealerButton;
	}

	public void setBank(Integer b) {
		this.bank = b;
	}
	// czyści stół z kart oraz zabiera je graczom (nie czyści pozostałości
	// po talii)
	public void cleanCards() {
		for (int i = 0; i < countPlayers(); i++) {
			this.getSystemPlayer(i).cleanCards();
		}
		this.CardsOnTable.removeAll();
	}

	public void incrBank(Integer gain) {
		this.bank = this.bank + gain;
	}

	public int evalBank() {
		int value = 0;
		for (int i = 0; i < countPlayers(); i++) {
			value += this.getSystemPlayer(i).getPlayerBet();
		}
		this.bank = value;
		return value;
	}

	public int getBank() {
		int value = 0;
		for (int i = 0; i < countPlayers(); i++) {
			value += this.getSystemPlayer(i).getPlayerBet();
		}
		this.bank = value;
		return value;
	}

	public Cards getCardsOnTable() {
		return this.CardsOnTable;
	}

	public void setCardsOnTable(Cards c) {
		this.CardsOnTable = c;
	}
	public void addCardOnTable(Card c) {
		this.CardsOnTable.addCard(c);
	}

	void initializeTableStates() {
		startGameState = new startGame();
		collectBigBlindState = new CollectBigBlind();
		collectSmallBlindState = new CollectSmallBlind();
		FirstBidState = new FirstBid();
		FirstDealState = new FirstDeal();
		FlopState = new Flop();
		LastBidState = new LastBid();
		RiverState = new River();
		SecondBidState = new SecondBid();
		SumUpState = new SumUp();
		ThirdBidState = new ThirdBid();
		TurnState = new Turn();
		NewTurnState = new NewTurn();
		stopGameState = new stopGame();
	}

	public void setState(TableState state) {
		TState = state;
		// Auto();

	}

	public void ChangeActivePlayersStatusExcept(PlayerStatus status,
			Integer except) {

		for (Integer i = except + 1; i < countPlayers(); i++) {
			if ((Players.get(i)
					.getPlayerStatus() != PlayerStatus.folded)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.all_in)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.quit)) {
				Players.get(i).setPlayerStatus(status);

			}
		}

		for (Integer i = 0; i < except; i++) {
			if ((Players.get(i)
					.getPlayerStatus() != PlayerStatus.folded)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.all_in)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.quit)) {
				Players.get(i).setPlayerStatus(status);

			}
		}

	}

	public Integer nextNonFoldedPlayer(Integer curr)
			throws RunOutOfPlayersException {
		Integer next = -1;
		for (Integer i = curr + 1; i < countPlayers(); i++) {
			if ((Players.get(i)
					.getPlayerStatus() != PlayerStatus.folded)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.quit)) {
				next = i;
				break;
			}
		}
		if (next == -1) {
			for (Integer i = 0; i < curr; i++) {
				if ((Players.get(i)
						.getPlayerStatus() != PlayerStatus.folded)
						&& (Players.get(i)
								.getPlayerStatus() != PlayerStatus.quit)) {
					next = i;
					break;
				}
			}
		}
		if (next == -1) {
			throw new RunOutOfPlayersException(
					"W turze pozostało mniej niż 2 graczy.");
		}
		return next;

	}

	public Integer nextNonQuitPlayer(Integer curr)
			throws RunOutOfPlayersException {
		Integer next = -1;
		for (Integer i = curr + 1; i < countPlayers(); i++) {
			if (Players.get(i)
					.getPlayerStatus() != PlayerStatus.quit) {
				next = i;
				break;
			}
		}
		if (next == -1) {
			for (Integer i = 0; i < curr; i++) {
				if (Players.get(i)
						.getPlayerStatus() != PlayerStatus.quit) {
					next = i;
					break;
				}
			}
		}
		if (next == -1) {
			throw new RunOutOfPlayersException(
					"W grze pozostało mniej niż 2 graczy.");
		}
		return next;

	}

	public Integer nextActivePlayer(Integer curr)
			throws RunOutOfActivePlayersException {
		Integer next = -1;
		for (Integer i = curr + 1; i < countPlayers(); i++) {
			if ((Players.get(i)
					.getPlayerStatus() != PlayerStatus.folded)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.all_in)
					&& (Players.get(i)
							.getPlayerStatus() != PlayerStatus.quit)) {
				next = i;
				break;
			}
		}
		if (next == -1) {
			for (Integer i = 0; i < curr; i++) {
				if ((Players.get(i)
						.getPlayerStatus() != PlayerStatus.folded)
						&& (Players.get(i)
								.getPlayerStatus() != PlayerStatus.all_in)
						&& (Players.get(i)
								.getPlayerStatus() != PlayerStatus.quit)) {
					next = i;
					break;
				}
			}
		}
		if (next == -1) {
			throw new RunOutOfActivePlayersException(
					"W licytacji pozostało mniej niż 2 graczy.");
		}
		return next;

	}

	public TableState getState() {
		return TState;
	}

	public void Auto() {
		TState.Auto(this);
	}

	public void end() {
		this.exitSignal = true;
	}
	/*
	 * public Integer findHighestBet(){ Integer hb=this.Bets.get(0); for(int
	 * i=1;i<Bets.size();i++){ if(Bets.get(i)>hb){ hb=Bets.get(i); } }
	 * this.highestBet=hb; return hb; }
	 */
	public void setHighestBet(Integer hb) {
		this.highestBet = hb;
	}

	public Integer getHighestBet() {
		this.setHighestBet(this.getMaxBet());
		return this.highestBet;
	}

	public Integer getlastActivePlayer() {
		return this.lastActivePlayer;
	}

	public void setlastActivePlayer(Integer lap) {
		this.lastActivePlayer = lap;
	}
	/*
	 * public Integer nextActivePlayer(Integer currplayer){ Integer next=-1;
	 * for(int i=currplayer+1;i<this.countPlayers();i++){
	 * if(this.Active.get(i)==true){ next=i; break; } } return next; }
	 */

	public void fill() {

		this.PlayersStatus.clear();
		this.Credits.clear();
		this.Bets.clear();
		for (int i = 0; i < this.countPlayers(); i++) {
			this.PlayersStatus.add(this.getSystemPlayer(i)
					.getPlayerStatus());
			this.Credits.add(this.getSystemPlayer(i)
					.getPlayerCredits());
			this.Bets.add(this.getSystemPlayer(i).getPlayerBet());
		}
	}

	public GameInfo getgameInfo(Integer player) {
		fill();// fills bets,PlayersStatus,credits
		gi.pot = this.getBank();
		gi.tableState = this.TState;
		gi.cardsOnTable = this.CardsOnTable;
		gi.playersCredits = this.Credits;
		gi.playerCards = this.getPlayerCards(player);
		gi.lastActivePlayer = this.getlastActivePlayer();
		gi.gameStarted = this.gameStarted;
		gi.dealerButton = this.dealerButton;
		gi.numOfNonFoldedPlayers = this.countNonFoldedPlayers();
		gi.numOfNonFoldedNonAllInPlayers = this.countActivePlayers();
		gi.playersBets = this.Bets;
		gi.highestBet = this.getHighestBet();
		gi.playersNames = new LinkedList<String>();
		for (SystemPlayer p : this.Players) {
			gi.playersNames.add(p.getName());
		}
		gi.playersStates = this.PlayersStatus;
		gi.playerCredits = this.getSystemPlayer(player)
				.getPlayerCredits();
		gi.playerBets = this.getSystemPlayer(player).getPlayerBet();
		gi.playerStatus = this.getSystemPlayer(player)
				.getPlayerStatus();
		return gi;
	}

	public int getMaxBet() {
		int maxBet = 0;
		for (int i = 0; i < countPlayers(); i++) {
			if ((this.getSystemPlayer(i)
					.getPlayerStatus() == PlayerStatus.max_bet_bb)
					|| (this.getSystemPlayer(i)
							.getPlayerStatus() == PlayerStatus.max_bet_nbb)) {
				maxBet = this.getSystemPlayer(i).getPlayerBet();
				break;
			}

		}
		return maxBet;
	}

	public Boolean BetsAreEqual() {
		boolean betsequal = true;
		for (int i = 0; i < countPlayers(); i++) {
			if (this.getSystemPlayer(i)
					.getPlayerStatus() == PlayerStatus.under_max_bet) {
				betsequal = false;
				break;
			}
		}
		return betsequal;
	}

	public void cleanBets() {
		for (int i = 0; i < countPlayers(); i++) {
			this.getSystemPlayer(i).setPlayerBet(0);
		}
	}
	/*
	 * public void initializeNonFoldedPlayers(){ for(int
	 * i=0;i<countPlayers();i++){
	 * if(this.getSystemPlayer(i).getPlayerStatus()!=PlayerStatus.folded){
	 * this.getSystemPlayer(i).setPlayerStatus(PlayerStatus.init); } } }
	 */
	public void initializePlayers() {
		for (int i = 0; i < countPlayers(); i++) {
			if (this.getSystemPlayer(i)
					.getPlayerStatus() != PlayerStatus.quit) {
				this.getSystemPlayer(i).setPlayerStatus(
						PlayerStatus.init);
			}
		}
	}

}
