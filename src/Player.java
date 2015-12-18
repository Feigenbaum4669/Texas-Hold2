abstract public class Player {
	protected Cards PlayerCards;
	protected String name;
	protected PlayerStatus status;
	protected Integer Credits;
	protected Integer bet;
	protected Integer hBet;
	protected Integer bank;
	protected Cards tableCards;
	
	protected GameInfo gameInfo;
	
	abstract public void readAndSetName();
	abstract public VAction makeAction(GameInfo gi);
	abstract public void notify(String msg);

	public Player() {
		PlayerCards = new Cards();
		this.name = "Unnamed Player";
		log("Player created: \"" + this.name + "\".");
	}
	
	public Player(String name) {
		PlayerCards = new Cards();
		this.name = name;
		log("Player created: \"" + this.name + "\".");
	}

	public void setName(String name) {
		log("Player \"" + this.name + "\" renamed to \"" + name
				+ "\".");
		this.name = name;
	}

	protected void setGameInfo(GameInfo gi) {
		this.gameInfo = gi;
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
