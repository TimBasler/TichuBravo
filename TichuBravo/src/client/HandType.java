package client;

import java.util.ArrayList;
import java.util.Collections;

import common.Card;
import common.Card.Suit;

public enum HandType {
	HighCard, OnePair, Pairs, ThreeOfAKind, FullHouse, Straight, BombFourOfAKind, BombStraightFlush;

	// each pair is in an arrayList
	public static ArrayList<ArrayList<Card>> onePairList = new ArrayList<ArrayList<Card>>();

	// each pairs in a row is in an arrayList
	public static ArrayList<ArrayList<Card>> pairsInARow = new ArrayList<ArrayList<Card>>();

	// each ThreeOfAKind is in an arrayList
	public static ArrayList<ArrayList<Card>> threeOfAKindList = new ArrayList<ArrayList<Card>>();

	// each FullHouse is in an arrayList
	public static ArrayList<ArrayList<Card>> fullHouseList = new ArrayList<ArrayList<Card>>();

	// each straight is in an arrayList
	public static ArrayList<ArrayList<Card>> straightList = new ArrayList<ArrayList<Card>>();

	// each BombFourOfAKind is in an arrayList
	public static ArrayList<ArrayList<Card>> bombList = new ArrayList<ArrayList<Card>>();

	// each BombStraightFlush is in an arrayList
	public static ArrayList<ArrayList<Card>> BombStraightFlushList = new ArrayList<ArrayList<Card>>();

	// normal cards order: 2,3,4,5,6,7,8,9,10,J,Q,K,A
	// special cards order: Dog, MahJong, Phenix, Dragon

	public static boolean hasPhenix(ArrayList<Card> specialCards) {
		boolean found = false;
		for (int i = 0; i < specialCards.size() && !found; i++) {
			if (specialCards.get(i).getSpecialCard() == Card.SpecialCard.Phenix) {
				found = true;
			}
		}
		return found;
	}

	public static boolean hasDog(ArrayList<Card> specialCards) {
		boolean found = false;
		for (int i = 0; i < specialCards.size() && !found; i++) {
			if (specialCards.get(i).getSpecialCard() == Card.SpecialCard.Dog) {
				found = true;
			}
		}
		return found;
	}

	public static boolean hasMahJong(ArrayList<Card> specialCards) {
		boolean found = false;
		for (int i = 0; i < specialCards.size() && !found; i++) {
			if (specialCards.get(i).getSpecialCard() == Card.SpecialCard.MahJong) {
				found = true;
			}
		}
		return found;
	}

	public static boolean hasDragon(ArrayList<Card> specialCards) {
		boolean found = false;
		for (int i = 0; i < specialCards.size() && !found; i++) {
			if (specialCards.get(i).getSpecialCard() == Card.SpecialCard.Dragon) {
				found = true;
			}
		}
		return found;
	}

	/**
	 * search for pairs and add it to onePairList
	 * 
	 * @param cards
	 */
	public static void findPair(ArrayList<Card> cards) {
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		for (int i = 0; i < clonedCards.size() - 1; i++) {
			for (int j = i + 1; j < clonedCards.size(); j++) {
				if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
					ArrayList<Card> newList = new ArrayList<Card>();
					newList.add(clonedCards.get(i));
					newList.add(clonedCards.get(j));
					clonedCards.remove(i);
					clonedCards.remove(j-1);
					i = 0;
					j = 0;
					onePairList.add(newList);
				}
			}
		}
	}

	/**
	 * if 7 pairs in a row, safe it in "pairsInARow" on sixth place if 6 pairs in a
	 * row, safe it in "pairsInARow" on fifth place if 5 pairs in a row, safe it in
	 * "pairsInARow" on fourth place if 4 pairs in a row, safe it in "pairsInARow"
	 * on third place if 3 pairs in a row, safe it in "pairsInARow" on second place
	 * if 2 pairs in a row, safe it in "pairsInARow" on first place (index 0)
	 */
	public static void findPairsInARow(ArrayList<Card> cards) {
		//-------------------
		findPair(cards); // ändern!
		//------------------
		ArrayList<Card> c = new ArrayList<Card>();

		for (int i = 0; i < onePairList.size(); i++) {
			for (int j = 0; j < onePairList.get(i).size(); j++) {
				c.add(onePairList.get(i).get(j));
			}
		}
		
		// if 7 pairs in a row, safe it in "pairsInARow" on sixth place
		if (c.size() > 12 && c.get(0).getRank().ordinal() + 1 == c.get(2).getRank().ordinal()
				&& c.get(2).getRank().ordinal() + 1 == c.get(4).getRank().ordinal()
				&& c.get(4).getRank().ordinal() + 1 == c.get(6).getRank().ordinal()
				&& c.get(6).getRank().ordinal() + 1 == c.get(8).getRank().ordinal()
				&& c.get(8).getRank().ordinal() + 1 == c.get(10).getRank().ordinal()
				&& c.get(10).getRank().ordinal() + 1 == c.get(12).getRank().ordinal()) {
			ArrayList<Card> newList = new ArrayList<Card>();
			for (int i = 0; i < 14; i++) {
				newList.add(c.get(i));
			}
			pairsInARow.add(newList);
		}

		// if 6 pairs in a row, safe it in "pairsInARow" on fifth place
		for (int i = 0; i < 3; i = i + 2) {
			if (c.size() > (i + 10) && c.get(i).getRank().ordinal() + 1 == c.get(i + 2).getRank().ordinal()
					&& c.get(i + 2).getRank().ordinal() + 1 == c.get(i + 4).getRank().ordinal()
					&& c.get(i + 4).getRank().ordinal() + 1 == c.get(i + 6).getRank().ordinal()
					&& c.get(i + 6).getRank().ordinal() + 1 == c.get(i + 8).getRank().ordinal()
					&& c.get(i + 8).getRank().ordinal() + 1 == c.get(i + 10).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 12; j++) {
					newList.add(c.get(j));
				}
			}
		}

		// if 5 pairs in a row, safe it in "pairsInARow" on fourth place
		for (int i = 0; i < 5; i = i + 2) {
			if (c.size() > (i + 8) && c.get(i).getRank().ordinal() + 1 == c.get(i + 2).getRank().ordinal()
					&& c.get(i + 2).getRank().ordinal() + 1 == c.get(i + 4).getRank().ordinal()
					&& c.get(i + 4).getRank().ordinal() + 1 == c.get(i + 6).getRank().ordinal()
					&& c.get(i + 6).getRank().ordinal() + 1 == c.get(i + 8).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 10; j++) {
					newList.add(c.get(j));
				}
				pairsInARow.add(newList);
			}
		}

		// if 4 pairs in a row, safe it in "pairsInARow" on third place
		for (int i = 0; i < 7; i = i + 2) {
			if (c.size() > (i + 6) && c.get(i).getRank().ordinal() + 1 == c.get(i + 2).getRank().ordinal()
					&& c.get(i + 2).getRank().ordinal() + 1 == c.get(i + 4).getRank().ordinal()
					&& c.get(i + 4).getRank().ordinal() + 1 == c.get(i + 6).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 8; j++) {
					newList.add(c.get(j));
				}
				pairsInARow.add(newList);
			}
		}

		// if 3 pairs in a row, safe it in "pairsInARow" on second place
		for (int i = 0; i < 9; i = i + 2) {
			if (c.size() > (i + 4) && c.get(i).getRank().ordinal() +1 == (c.get(i + 2).getRank().ordinal())
					&& c.get(i + 2).getRank().ordinal()+1 == (c.get(i + 4).getRank().ordinal())) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 6; j++) {
					newList.add(c.get(j));
				}
				pairsInARow.add(newList);
			}
		}

		// if 2 pairs in a row, safe it in "pairsInARow" on first place
		for (int i = 0; i < 11; i = i + 2) {
			if (c.size() > (i + 2) && c.get(i).getRank().ordinal() + 1 == c.get(i + 2).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 4; j++) {
					newList.add(c.get(j));
				}
				pairsInARow.add(newList);
			}
		}

	} // end of method pairs

	

	/**
	 * search for three of a kind and add it to threeOfAKindList
	 * @param cards
	 */
	public static void findThreeOfAKind(ArrayList<Card> cards) {
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();
		System.out.println(clonedCards.toString());

		for (int i = 0; i < clonedCards.size() - 2; i++) {
			for (int j = i + 1; j < clonedCards.size() - 1; j++) {
				for (int k = j + 1; k < clonedCards.size(); k++) {
					if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()
							&& clonedCards.get(j).getRank() == clonedCards.get(k).getRank()) {
						ArrayList<Card> newList = new ArrayList<Card>();
						newList.add(clonedCards.get(i));
						newList.add(clonedCards.get(j));
						newList.add(clonedCards.get(k));
						clonedCards.remove(i);
						clonedCards.remove(j - 1);
						clonedCards.remove(k - 2);
						System.out.println(i);
						System.out.println(j);
						System.out.println(k);
						i = 0;
						j = 0;
						k = 0;
						System.out.println(newList.toString());
						threeOfAKindList.add(newList);
					}
				}
			}
		}
	}




	/**
	 * search for five or more cards in a row and add it to straightList
	 * @param cards
	 */
	public static void findStraight(ArrayList<Card> cards) {
		ArrayList<Card> sortList = (ArrayList<Card>) cards.clone();
		Collections.sort(sortList);
		// delete all cards with the same rank
				for (int i = 0; i < sortList.size() - 1; i++) {
					for (int j = i + 1; j < sortList.size(); j++) {
						if (sortList.get(i).getRank() == sortList.get(j).getRank()) {
							sortList.remove(j);
							i = 0;
							j = 0;
						}
					}
				}
		
		
		

		// 13 in a row
		for (int i = 0; i < 2; i++) {
			if (sortList.size() > (i + 12)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()
					&& sortList.get(i + 9).getRank().ordinal() + 1 == sortList.get(i + 10).getRank().ordinal()
					&& sortList.get(i + 10).getRank().ordinal() + 1 == sortList.get(i + 11).getRank().ordinal()
					&& sortList.get(i + 11).getRank().ordinal() + 1 == sortList.get(i + 12).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 13; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 12 in a row
		for (int i = 0; i < 3; i++) {
			if (sortList.size() > (i + 11)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()
					&& sortList.get(i + 9).getRank().ordinal() + 1 == sortList.get(i + 10).getRank().ordinal()
					&& sortList.get(i + 10).getRank().ordinal() + 1 == sortList.get(i + 11).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 12; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 11 in a row
		for (int i = 0; i < 4; i++) {
			if (sortList.size() > (i + 10)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()
					&& sortList.get(i + 9).getRank().ordinal() + 1 == sortList.get(i + 10).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 11; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 10 in a row
		for (int i = 0; i < 5; i++) {
			if (sortList.size() > (i + 9)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 10; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 9 in a row
		for (int i = 0; i < 6; i++) {
			if (sortList.size() > (i + 8)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 9; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 8 in a row
		for (int i = 0; i < 7; i++) {
			if (sortList.size() > (i + 7)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 8; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 7 in a row
		for (int i = 0; i < 8; i++) {
			if (sortList.size() > (i + 6)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 7; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 6 in a row
		for (int i = 0; i < 9; i++) {
			if (sortList.size() > (i + 5)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 6; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

		// 5 in a row
		for (int i = 0; i < 10; i++) {
			if (sortList.size() > (i + 4)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 5; j++) {
					newList.add(sortList.get(j));
				}
				straightList.add(newList);
			}
		}

	}

	public static void findBombStraightFlush3(ArrayList<Card> cards) {
		ArrayList<Card> sortList = (ArrayList<Card>) cards.clone();
		Collections.sort(sortList);
	
		// 13 in a row
		for (int i = 0; i < 2; i++) {
			if (sortList.size() > (i + 12)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()
					&& sortList.get(i + 9).getRank().ordinal() + 1 == sortList.get(i + 10).getRank().ordinal()
					&& sortList.get(i + 10).getRank().ordinal() + 1 == sortList.get(i + 11).getRank().ordinal()
					&& sortList.get(i + 11).getRank().ordinal() + 1 == sortList.get(i + 12).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 13; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 12 in a row
		for (int i = 0; i < 3; i++) {
			if (sortList.size() > (i + 11)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()
					&& sortList.get(i + 9).getRank().ordinal() + 1 == sortList.get(i + 10).getRank().ordinal()
					&& sortList.get(i + 10).getRank().ordinal() + 1 == sortList.get(i + 11).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 12; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 11 in a row
		for (int i = 0; i < 4; i++) {
			if (sortList.size() > (i + 10)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()
					&& sortList.get(i + 9).getRank().ordinal() + 1 == sortList.get(i + 10).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 11; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 10 in a row
		for (int i = 0; i < 5; i++) {
			if (sortList.size() > (i + 9)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()
					&& sortList.get(i + 8).getRank().ordinal() + 1 == sortList.get(i + 9).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 10; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 9 in a row
		for (int i = 0; i < 6; i++) {
			if (sortList.size() > (i + 8)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()
					&& sortList.get(i + 7).getRank().ordinal() + 1 == sortList.get(i + 8).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 9; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 8 in a row
		for (int i = 0; i < 7; i++) {
			if (sortList.size() > (i + 7)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()
					&& sortList.get(i + 6).getRank().ordinal() + 1 == sortList.get(i + 7).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 8; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 7 in a row
		for (int i = 0; i < 8; i++) {
			if (sortList.size() > (i + 6)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()
					&& sortList.get(i + 5).getRank().ordinal() + 1 == sortList.get(i + 6).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 7; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 6 in a row
		for (int i = 0; i < 9; i++) {
			if (sortList.size() > (i + 5)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()
					&& sortList.get(i + 4).getRank().ordinal() + 1 == sortList.get(i + 5).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 6; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

		// 5 in a row
		for (int i = 0; i < 10; i++) {
			if (sortList.size() > (i + 4)
					&& sortList.get(i + 0).getRank().ordinal() + 1 == sortList.get(i + 1).getRank().ordinal()
					&& sortList.get(i + 1).getRank().ordinal() + 1 == sortList.get(i + 2).getRank().ordinal()
					&& sortList.get(i + 2).getRank().ordinal() + 1 == sortList.get(i + 3).getRank().ordinal()
					&& sortList.get(i + 3).getRank().ordinal() + 1 == sortList.get(i + 4).getRank().ordinal()) {
				ArrayList<Card> newList = new ArrayList<Card>();
				for (int j = i; j < i + 5; j++) {
					newList.add(sortList.get(j));
				}
				BombStraightFlushList.add(newList);
			}
		}

	}

	
	/**
	 * if the threeOfAKind and onePair are not the same cards, then add it to fullHouseList
	 */
	public static void findFullHouse() {
		for (int i = 0; i < threeOfAKindList.size(); i++) {
			for (int a = 0; a < onePairList.size(); a++) {

				boolean different = true;
				for (int j = 0; j < threeOfAKindList.get(i).size(); j++) {
					for (int b = 0; b < onePairList.get(a).size(); b++) {
						if (threeOfAKindList.get(i).get(j).isEqual(onePairList.get(a).get(b))) {
							different = false;
						}
					}
				}
				if (different) {
					ArrayList<Card> newList = new ArrayList<Card>();
					for (int j = 0; j < threeOfAKindList.get(i).size(); j++) {
						newList.add(threeOfAKindList.get(i).get(j));
					}
					for (int b = 0; b < onePairList.get(a).size(); b++) {
						newList.add(onePairList.get(a).get(b));
					}
					fullHouseList.add(newList);
				}
			}
		}
	}

	/**
	 * search four of a kind
	 * @param cards
	 */
	public static void findBomb(ArrayList<Card> cards) {
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		for (int i = 0; i < clonedCards.size() - 3; i++) {
			for (int j = i + 1; j < clonedCards.size() - 2; j++) {
				for (int k = j + 1; k < clonedCards.size() - 1; k++) {
					for (int h = k + 1; h < clonedCards.size(); h++) {

						if ((clonedCards.get(i).getRank() == clonedCards.get(j).getRank())
								&& (clonedCards.get(j).getRank() == clonedCards.get(k).getRank())
								&& (clonedCards.get(k).getRank() == clonedCards.get(h).getRank())) {
							ArrayList<Card> newList = new ArrayList<Card>();
							newList.add(clonedCards.get(i));
							newList.add(clonedCards.get(j));
							newList.add(clonedCards.get(k));
							newList.add(clonedCards.get(h));
							bombList.add(newList);
						}

					}
				}
			}
		}

	}

	public static void findBombStraightFlush2(ArrayList<Card> cards) {
		ArrayList<Card> cc = (ArrayList<Card>) cards.clone();
		
		ArrayList<Card> JadeList = new ArrayList<Card>();
		ArrayList<Card> StarsList = new ArrayList<Card>();
		ArrayList<Card> MacheteList = new ArrayList<Card>();
		ArrayList<Card> PagodasList = new ArrayList<Card>();
		
		for (Card card : cc) {
			switch (card.getSuit()) {
			case Jade:
				JadeList.add(card);
				break;
			case Stars:
				StarsList.add(card);
				break;
			case Machete:
				MacheteList.add(card);
				break;
			case Pagodas:
				PagodasList.add(card);
				break;
			}
		}
		findBombStraightFlush3(JadeList);
		findBombStraightFlush3(StarsList);
		findBombStraightFlush3(MacheteList);
		findBombStraightFlush3(PagodasList);
		
	}
	
	/**
	 * if the straightList contains a Straight Flush, then add it to the BombStraightFlushList
	 */
	public static void findBombStraightFlush() {
		for (int i = 0; i < straightList.size(); i++) {
			int counter = 0;
			for (int j = 0; j < straightList.get(i).size() - 1; j++) {
				for (int k = j + 1; k < straightList.get(i).size(); k++) {
					if (straightList.get(i).get(j).getSuit() == straightList.get(i).get(k).getSuit()) {
						counter++;
					}
				}
			}
			if (counter + 1 == straightList.get(i).size()) {
				ArrayList<Card> clonedCards = (ArrayList<Card>) straightList.get(i).clone();
				BombStraightFlushList.add(clonedCards);
			}
		}
	}

}
