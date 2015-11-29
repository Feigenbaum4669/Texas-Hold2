


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

}

