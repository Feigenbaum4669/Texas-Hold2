


public class Player {
	private Cards PlayerCards;

	Player() {
		PlayerCards = new Cards();
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
		VAction va= new VAction();
		va.action=Action.bet;
		va.value=10;
		return va;
		}
	public void notify(String msg){
		//...
	}
}
