public class SystemPlayer {
	private PlayerStatus status;
	private String name;
	private Integer Credits;
	private Player extPlayer;
	private Integer Bet;
	private Cards PlayersCards;

	public SystemPlayer(Player extPlayer) {
		this.extPlayer = extPlayer;
		name = extPlayer.name;
		status = PlayerStatus.init;
		PlayersCards = new Cards();
		Bet = 0;
		Credits = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPlayerStatus(PlayerStatus status) {
		this.status = status;
	}
	public void setPlayerCredits(Integer Credits) {
		this.Credits = Credits;
	}

	public void incrPlayerCredits(Integer gain) {
		this.Credits = this.Credits + gain;
	}

	public void allIn() {
		this.Bet = this.Bet + Credits;
		setPlayerCredits(0);
	}

	public void decrPlayerCredits(Integer loss)
			throws NotEnoughCreditsException {
		if (loss > this.Credits) {
			throw new NotEnoughCreditsException();
		}
		this.Credits = this.Credits - loss;
	}

	public void incrPlayerBet(Integer incrBet)
			throws NotEnoughCreditsException {
		decrPlayerCredits(incrBet);
		this.Bet = this.Bet + incrBet;
	}

	public void setPlayerBet(Integer Bet) {
		this.Bet = Bet;
	}

	public PlayerStatus getPlayerStatus() {
		return this.status;
	}
	public Integer getPlayerCredits() {
		return this.Credits;
	}
	public Integer getPlayerBet() {
		return this.Bet;
	}

	public void takeCard(Card c) {
		this.PlayersCards.addCard(c);
	}

	public Card showCard(Integer i) {
		return this.PlayersCards.getCard(i);
	}

	public Cards getCards() {
		return this.PlayersCards;
	}

	public void passGameInfo(GameInfo gi) {
		this.extPlayer.setGameInfo(gi);
	}

	public void cleanCards() {
		this.PlayersCards.removeAll();
	}

	public Integer countCards() {
		return this.PlayersCards.sizeof();
	}

	public void notify(String msg) {
		this.extPlayer.notify(msg);
	}

	public VAction makeAction(GameInfo gi) {
		return this.extPlayer.makeAction(gi);
	}

}
