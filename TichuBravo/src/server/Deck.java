package server;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Dominik
 *
 */
public class Deck {
	private final ArrayList<Card> cards = new ArrayList<>();

	public Deck() { // 52 normal cards + 4 special cards = 56 cards
		createDeck();
	}

	/**
	 * create all cards and shuffle them
	 */
	public void createDeck() {
		//delete all cards from the list
		cards.clear();
		// create normal cards
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
				Card card = new Card(suit, rank);
				cards.add(card);
			}
		}
		// create special cards
		for (Card.Special special : Card.Special.values()) {
			Card card = new Card(special);
			cards.add(card);
		}
		// shuffle all cards
		Collections.shuffle(cards);
	}
	
	/**
	 * return a card as long as there is a card on the list
	 * @return card
	 */
	public Card deal() {
        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
        return card;
    }

}
