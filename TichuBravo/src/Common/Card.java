package Common;

//This class is written by Tim Basler
//the template of this class is the poker game written by Tim and Dominik
//in the course of Bradley Richards

public class Card implements Comparable<Card> {
	
	public enum SpezialCard{Dog,MahJong,Dragon,Phenix;
		public String toString() {
			String spezialCard="";
			switch(this) {
			case Dog:spezialCard="dog";break;
			case MahJong:spezialCard="mahJong";break;
			case Dragon:spezialCard="dragon";break;
			case Phenix:spezialCard="phenix";break;
			}
			return spezialCard;
		}
	}
	
	public enum Suit{Stars,Swords,Jade,Pagodas;
		public String toString() {
			String suit="";
			switch(this) {
			case Stars:suit="stars";break;
			case Swords:suit="swords";break;
			case Jade:suit="jade";break;
			case Pagodas:suit="pagodas";break;
			}
			return suit;
		}
	};
	
	 public enum Rank { Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
	        @Override
	        public String toString() {
	            String str = "ace";  // Assume we have an ace, then cover all other cases
	            // Get ordinal value, which ranges from 0 to 12
	            int ordinal = this.ordinal();
	            if (ordinal <= 8) {
	                str = Integer.toString(ordinal+2);
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
	
	 //Constructor for the "SpezialCards"
	private SpezialCard spezialCard;
	public Card(SpezialCard spezialCard) {
		this.spezialCard=spezialCard;
	}
	
	
	//Constructor for the "normal" Cards
	private  Rank rank;
	private  Suit suit;
	public Card(Rank rank,Suit suit) {
		this.rank=rank;
		this.suit=suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public String toString() {
		return rank.toString()+suit.toString();
	}
	
	public int compareTo(Card otherCard) {
		if(rank.ordinal()==otherCard.rank.ordinal()) {
			return 0;
		}else {
			if(rank.ordinal()>otherCard.rank.ordinal()) {
				return 1;
			}else {
				return -1;
			}
		}
	}
	

}
