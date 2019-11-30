package common;


//This class is written by Tim Basler
//the template of this class is the poker game written by Tim and Dominik
//in the course of Bradley Richards

/**
 * @author Tim
 *
 */
public class Card implements Comparable<Card> {

	public boolean isSpecial() {
		return isSpecial;
	}

	private SpecialCard specialCard;
	private Rank rank;
	private Suit suit;
	private boolean isSpecial;

	// Constructor for the "SpezialCards"
	public Card(SpecialCard specialCard) {
		if(specialCard == SpecialCard.MahJong || specialCard == SpecialCard.Dog) {
			this.rank = Rank.Two;
			this.suit = Suit.Stars;
		}
		if(specialCard == SpecialCard.Phenix || specialCard == SpecialCard.Dragon) {
			this.rank = Rank.Ace;
			this.suit = Suit.Stars;
		}
		this.specialCard = specialCard;
		isSpecial = true;
	}
	
	// Constructor for the "normal" Cards
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		this.specialCard = null;
		isSpecial = false;
	}

	public enum SpecialCard {
		Dog, MahJong, Phenix, Dragon;
	}


	// Had to change the Swords to Machete that i can create Cards
	public enum Suit {
		Stars, Machete, Jade, Pagodas;

		public String toString() {
			String suit = "";
			switch (this) {
			case Stars:
				suit = "S";
				break;
			case Machete:
				suit = "M";
				break;
			case Jade:
				suit = "J";
				break;
			case Pagodas:
				suit = "P";
				break;
			}
			return suit;
		}
	};

	


	public enum Rank {
		Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
		@Override
		public String toString() {
			String str = "A"; // Assume we have an ace, then cover all other cases
			// Get ordinal value, which ranges from 0 to 12
			int ordinal = this.ordinal();
			if (ordinal <= 7) {
				str = Integer.toString(ordinal + 2);
			} else if (ordinal == 8) { // Ten
				str = "T";
			} else if (ordinal == 9) { // Jack
				str = "J";
			} else if (ordinal == 10) { // Queen
				str = "Q";
			} else if (ordinal == 11) { // King
				str = "K";
			}
			return str;
		}
	};

	/**
	 * Create a card from a 2-character String. First character is the rank (2-9, T,
	 * J, Q, K, A) Second character is the suit (C, D, H, S)
	 */


	public static Card makeCard(String in) {
		Card card = null;
		if (in.length() > 2) {

			switch (in) {
			case "Phenix":
				card = new Card(Card.SpecialCard.Phenix);
				break;
			case "Dog":
				card = new Card(Card.SpecialCard.Dog);
				break;
			case "MahJong":
				card = new Card(Card.SpecialCard.MahJong);
				break;
			case "Dragon":
				card = new Card(Card.SpecialCard.Dragon);
			}
		} else {

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

			card = new Card(rank, suit);
		}

		return card;
	}
	
	public int getRankOrdinal() {
		if (!isSpecial) return this.rank.ordinal();
		if (this.specialCard == SpecialCard.MahJong || this.specialCard == SpecialCard.Dog) return this.rank.ordinal()-1;
		if (this.specialCard == SpecialCard.Phenix) return this.rank.ordinal()+1;
		if (this.specialCard == SpecialCard.Dragon) return this.rank.ordinal()+2;
		return 0;
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
		if (isSpecial) {
			return this.specialCard.toString();
		}
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
