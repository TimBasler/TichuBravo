package common;

//This class is written by Tim Basler
//the template of this class is the poker game written by Tim and Dominik
//in the course of Bradley Richards

public class Card implements Comparable<Card> {
	private SpecialCard specialCard;
	private Rank rank;
	private Suit suit;

	// Constructor for the "SpezialCards"
	public Card(SpecialCard specialCard) {
		this.specialCard = specialCard;
		this.rank = null;
		this.suit = null;
	}

	// Constructor for the "normal" Cards
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		this.specialCard = null;
	}

	public enum SpecialCard {
		Dog, MahJong, Phenix, Dragon;
	}

	// Had to change the Swords to Machete that i can create Cards
	public enum Suit {
		Stars, Machete, Jade, Pagodas;
	}

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

	/**
	 * Create a card from a 2-character String. First character is the rank (2-9, T,
	 * J, Q, K, A) Second character is the suit (C, D, H, S)
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

	public SpecialCard getSpecialCard() {
		return specialCard;
	}

	public String toString() {
		return rank.toString() + suit.toString();
	}

	@Override
	public int compareTo(Card otherCard) {
		if (this.specialCard == null) {
			if (rank.ordinal() == otherCard.rank.ordinal()) {
				return 0;
			} else {
				return (rank.ordinal() > otherCard.rank.ordinal()) ? 1 : -1;
			}
		}
		if (specialCard.ordinal() == otherCard.specialCard.ordinal()) {
			return 0;
		}
		return (specialCard.ordinal() > otherCard.specialCard.ordinal()) ? 1 : -1;
	}

	public boolean isEqual(Card other) {
		if (this.specialCard == other.specialCard && this.rank == other.rank && this.suit == other.suit) {
			return true;
		}
		return false;
	}

}
