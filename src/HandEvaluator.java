import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class HandEvaluator {
	private Card[] tableCards;
	private Card[] holeCards;

	private Node currentNode;
	private Node tableCardsNode;
	private Node[] nodes = new Node[76154];

	public HandEvaluator() {
		File file = new File("tree.csv");
		readTree(file);
	}

	public HandEvaluator reset() {
		currentNode = new Node("-1,0,1,2,3,4,5,6,7,8,9,10,11,12,-1,-1");
		return this;
	}

	public HandEvaluator initWithTableCards(Card[] tableCards) {
		this.tableCards = tableCards;
		reset();

		for (Card c : tableCards) {
			parseCard(c);
		}
		tableCardsNode = currentNode;

		return this;
	}

	public HandEvaluator initWithHoleCards(Card[] holeCards) {
		if (currentNode == null) {
			return null;
		}
		this.holeCards = holeCards;
		currentNode = tableCardsNode;
		for (Card c : holeCards) {
			parseCard(c);
		}
		return this;
	}

	private Suit checkForFlush() {
		int[] counters = new int[Suit.values().length];

		try {
			for (Card c : tableCards) {
				++counters[c.getSuit().ordinal()];
			}

			for (Card c : holeCards) {
				++counters[c.getSuit().ordinal()];
			}
		} catch (NullPointerException e) {
			return null;
		}

		for (int i = 0; i < Suit.values().length; ++i) {
			if (counters[i] >= 5) {
				return Suit.values()[i];
			}
		}
		return null;
	}

	public int eval() {
		if (currentNode == null) {
			return -1;
		}
		Suit suit;
		if ((suit = checkForFlush()) == null) {
			return currentNode.getEquivClass();
		} else {
			Node node = new Node(
					"-1,0,1,2,3,4,5,6,7,8,9,10,11,12,-1,-1");
			int depth = 0;
			int indexToLookAt;
			int nextNodeNum;

			for (Card c : tableCards) {
				if (c.getSuit() != suit) {
					continue;
				}
				++depth;
				indexToLookAt = c.getRank().getIndex();
				nextNodeNum = node.getDest(indexToLookAt);
				node = nodes[nextNodeNum];
			}
			for (Card c : holeCards) {
				if (c.getSuit() != suit) {
					continue;
				}
				++depth;
				indexToLookAt = c.getRank().getIndex();
				nextNodeNum = node.getDest(indexToLookAt);
				node = nodes[nextNodeNum];
			}
			if (depth >= 5) {
				return node.getFlushEquivClass();
			} else {
				System.out.println(
						"error when determining if flush");
				return 0;
			}
		}
	}

	private int readTree(File file) {
		String line = null;
		int lines = 0;
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(file));

			while ((line = reader.readLine()) != null) {
				nodes[lines++] = new Node(line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		return lines;
	}

	private void parseCard(Card card) {
		int indexToLookAt = card.getRank().getIndex();
		int nextNodeNum = currentNode.getDest(indexToLookAt);
		currentNode = nodes[nextNodeNum];
	}
}