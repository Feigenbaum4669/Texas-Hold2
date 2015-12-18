import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck extends Cards {

	private List<Card> CardStack;

	Deck() {
		CardStack = new LinkedList<Card>();
	}

	public void newDeck() {
		removeAll();
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values())
				CardStack.add(new Card(s, r));
		}
	}

	public void shuffle() {
		Collections.shuffle(CardStack);
	}

	public void addCard(Card c) {
		CardStack.add(c);
	}

	public void setCards(List<Card> cards) {
		CardStack = cards;
	}

	public void removeCard(Integer i) {
		CardStack.remove(getCard(i));

	}

	public Card getCard(Integer i) {
		return CardStack.get(i);
	}

	public List<Card> getCards() {
		return CardStack;
	}

	public Card giveCard(Integer i) {
		Card c = getCard(i);
		removeCard(i);
		return c;
	}

	public void removeAll() {
		CardStack.clear();
	}

	public Integer sizeof() {
		return CardStack.size();
	}

}
