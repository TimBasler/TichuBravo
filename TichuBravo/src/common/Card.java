package common;


//This class is written by Tim Basler
//the template of this class is the poker game written by Tim and Dominik
//in the course of Bradley Richards

public class Card implements Comparable<Card> {

	public enum SpezialCard {
		Dog, MahJong, Dragon, Phenix;
		public String toString() {
			String spezialCard = "";
			switch (this) {
			case Dog:
				spezialCard = "dog";
				break;
			case MahJong:
				spezialCard = "mahJong";
				break;
			case Dragon:
				spezialCard = "dragon";
				break;
			case Phenix:
				spezialCard = "phenix";
				break;
			}
			return spezialCard;
		}
	}

	//Had to change the Swords to Machete that i can create Cards 
	public enum Suit {
		Stars, Machete, Jade, Pagodas;
		public String toString() {
			String suit = "";
			switch (this) {
			case Stars:
				suit = "stars";
				break;
			case Machete:
				suit = "machete";
				break;
			case Jade:
				suit = "jade";
				break;
			case Pagodas:
				suit = "pagodas";
				break;
			}
			return suit;
		}
	};

	public enum Rank {
		Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
		@Override
		public String toString() {
			String str = "ace"; // Assume we have an ace, then cover all other cases
			// Get ordinal value, which ranges from 0 to 12
			int ordinal = this.ordinal();
			if (ordinal <= 8) {
				str = Integer.toString(ordinal + 2);
			} else if (ordinal == 9) { // Jack
				str = "jack";
			} else if (ordinal == 10) { // Queen
				str = "queen";
			} else if (ordinal == 11) { // King
				str = "king";
			}
			return str;
		}
	};

	// Constructor for the "SpezialCards"
	private SpezialCard spezialCard;

	public Card(SpezialCard spezialCard) {
		this.spezialCard = spezialCard;
	}

	// Constructor for the "normal" Cards
	private Rank rank;
	private Suit suit;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * Create a card from a 2-character String.
	 * First character is the rank (2-9, T, J, Q, K, A) 
	 * Second character is the suit (C, D, H, S)
	 */
	
	public Card makeCard(String in) {
		char r = in.charAt(0);
		Card.Rank rank = null;
		if (r <= '9')
			rank = Card.Rank.values()[r - '0' - 2];
		else if (r == 'T')
			rank = Card.Rank.Ten;
		else if (r == 'J')
			rank = Card.Rank.Jack;
		else if (r == 'Q')
			rank = Card.Rank.Queen;
		else if (r == 'K')
			rank = Card.Rank.King;
		else if (r == 'A')
			rank = Card.Rank.Ace;

		char s = in.charAt(1);
		Card.Suit suit = null;
		if (s == 'J')
			suit = Card.Suit.Jade;
		if (s == 'P')
			suit = Card.Suit.Pagodas;
		if (s == 'S')
			suit = Card.Suit.Stars;
		if (s == 'M')
			suit = Card.Suit.Machete;

		return new Card(rank, suit);
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public String toString() {
		return rank.toString() + suit.toString();
	}

	public int compareTo(Card otherCard) {
		if (rank.ordinal() == otherCard.rank.ordinal()) {
			return 0;
		} else {
			if (rank.ordinal() > otherCard.rank.ordinal()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

}
