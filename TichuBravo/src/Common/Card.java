package Common;

//This class is written by Tim Basler
//the template of this class is the poker game written by Tim and Dominik
//in the course of Bradley Richards

public class Card implements Comparable<Card> {
	
	public enum Suit{Stars,Swords,Jade,Pagodas;
		public String toString() {
			String Suit="";
			switch(this) {
			case Stars:Suit="stars";break;
			case Swords:Suit="swords";break;
			case Jade:Suit="jade";break;
			case Pagodas:Suit="pagodas";break;
			}
			return Suit;
		}
	};
	
	public enum Rank{Dog,MahJong,Two, Three, Four, Five, Six, Seven, Eight, Nine,Ten,Jack,Queen,King,Ace,Dragon,Phenix;
		public String toString() {
			String str="";
			int ordinal=this.ordinal();
			
			if(ordinal==11) {
				str="jack";
			}else if(ordinal==12) {
				str="queen";
			}else if(ordinal==13) {
				str="king";
			}else if(ordinal==14) {
				str="ace";
			}else if(ordinal==15) {
				str="dragon";
			}else if(ordinal==16) {
				str="phenix";
			}else if(ordinal<=10) {
				str=Integer.toString(ordinal);
			}
			return str;
		}
	};
	
	private final Rank rank;
	private final Suit Suit;
	
	public Card(Rank rank,Suit Suit) {
		this.rank=rank;
		this.Suit=Suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return Suit;
	}
	
	public String toString() {
		return rank.toString()+Suit.toString();
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
