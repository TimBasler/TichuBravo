package common;


//the template of this class is the poker game written by Tim and Dominik
//in the course of Bradley Richards

/**
 * @author Tim
 *
 */
public class Card implements Comparable<Card> {

	public enum SpezialCard {
		Dog, MahJong, Dragon, Phenix;
		public String toString() {
			String spezialCard = "";
			switch (this) {
			case Dog:
				spezialCard = "Dog";
				break;
			case MahJong:
				spezialCard = "MahJong";
				break;
			case Dragon:
				spezialCard = "Dragon";
				break;
			case Phenix:
				spezialCard = "Phenix";
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
			if (ordinal <= 8) {
				str = Integer.toString(ordinal + 2);
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
		if(in.length()>2) {
			Card.SpezialCard spezialCard=null;
			switch (in){
			case  "Phenix":
				spezialCard=Card.SpezialCard.Phenix;
				break;
			case "Dog":
				spezialCard = Card.SpezialCard.Dog;
				break;
			case "MahJong":
				spezialCard= Card.SpezialCard.MahJong;
				break;
			case "dragon":
				spezialCard= Card.SpezialCard.Dragon;
			}
		}
		
		char s = in.charAt(0);
		Card.Suit suit = null;
		if (s == 'J')
			suit = Card.Suit.Jade;
		if (s == 'P')
			suit = Card.Suit.Pagodas;
		if (s == 'S')
			suit = Card.Suit.Stars;
		if (s == 'M')
			suit = Card.Suit.Machete;
		
		char r = in.charAt(1);
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
