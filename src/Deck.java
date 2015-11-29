
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

}

