

public class Card {
	private final Suit suit;
	private final Rank rank;

	public Card(final Suit suit, final Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public Rank getRank() {
		return this.rank;
	}
}
