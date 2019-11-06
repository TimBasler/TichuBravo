package client;

import java.util.ArrayList;
import java.util.Collections;

import common.Card;
import common.Card.Suit;

public enum HandType {
	HighCard, OnePair, Pairs, ThreeOfAKind, FullHouse, Straight, BombFourOfAKind, BombStraightFlush;

	// 1.Array = HighCard, 2.Array = OnePair, 3.Array = Pairs, etc...
	public static ArrayList<ArrayList<Card>> handTypeList = new ArrayList<ArrayList<Card>>(8);
	// 1.Array = 2Pairs in a row, 2.Array = 3Pairs in a row, etc...
	public static ArrayList<ArrayList<Card>> pairsInARow = new ArrayList<ArrayList<Card>>(6);

//	Dog, Mah Jong, 2,3,4,5,6,7,8,9,10,J,Q,K,A,Phenix,Dragon

	public static HandType evaluateHand(ArrayList<Card> cards) {
		HandType currentEval = HighCard;

		if (isOnePair(cards))
			currentEval = OnePair;
		if (isTwoPair(cards))
			currentEval = TwoPair;
		if (isThreeOfAKind(cards))
			currentEval = ThreeOfAKind;
		if (isStraight(cards))
			currentEval = Straight;
		if (isFlush(cards))
			currentEval = Flush;
		if (isFullHouse(cards))
			currentEval = FullHouse;
		if (isFourOfAKind(cards))
			currentEval = FourOfAKind;
		if (isStraightFlush(cards))
			currentEval = StraightFlush;

		return currentEval;
	}

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

	public static void findPair(ArrayList<Card> cards, ArrayList<Card> specialCards) {
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		for (int i = 0; i < clonedCards.size() - 1; i++) {
			for (int j = i + 1; j < clonedCards.size(); j++) {
				if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
					handTypeList.get(1).add(clonedCards.get(i));
					handTypeList.get(1).add(clonedCards.get(j));
					clonedCards.remove(i);
					clonedCards.remove(j + 1);
					i = 0;
					j = 1; // pruefen ob j = 0 sein muss;
				}
			}
		}
	}

	public static void Pairs() {
		ArrayList<Card> c = (ArrayList<Card>) handTypeList.get(1).clone();

		// if 7 pairs in a row, safe it in "pairsInARow" on sixth place
		if (c.get(12) != null && c.get(0).getRank().ordinal() == c.get(2).getRank().ordinal() + 1
				&& c.get(2).getRank().ordinal() == c.get(4).getRank().ordinal() + 1
				&& c.get(4).getRank().ordinal() == c.get(6).getRank().ordinal() + 1
				&& c.get(6).getRank().ordinal() == c.get(8).getRank().ordinal() + 1
				&& c.get(8).getRank().ordinal() == c.get(10).getRank().ordinal() + 1
				&& c.get(10).getRank().ordinal() == c.get(12).getRank().ordinal() + 1) {
			for (int i = 0; i < 14; i++) {
				pairsInARow.get(5).add(c.get(i));
			}
		}

		// if 6 pairs in a row, safe it in "pairsInARow" on fifth place
		for (int i = 0; i < 3; i = i + 2) {
			if (c.get(i + 10) != null && c.get(i).getRank().ordinal() == c.get(i + 2).getRank().ordinal() + 1
					&& c.get(i + 2).getRank().ordinal() == c.get(i + 4).getRank().ordinal() + 1
					&& c.get(i + 4).getRank().ordinal() == c.get(i + 6).getRank().ordinal() + 1
					&& c.get(i + 6).getRank().ordinal() == c.get(i + 8).getRank().ordinal() + 1
					&& c.get(i + 8).getRank().ordinal() == c.get(i + 10).getRank().ordinal() + 1) {
				for (int j = i; j < i + 12; j++) {
					pairsInARow.get(4).add(c.get(j));
				}
			}
		}

		// if 5 pairs in a row, safe it in "pairsInARow" on fourth place
		for (int i = 0; i < 5; i = i + 2) {
			if (c.get(i + 8) != null && c.get(i).getRank().ordinal() == c.get(i + 2).getRank().ordinal() + 1
					&& c.get(i + 2).getRank().ordinal() == c.get(i + 4).getRank().ordinal() + 1
					&& c.get(i + 4).getRank().ordinal() == c.get(i + 6).getRank().ordinal() + 1
					&& c.get(i + 6).getRank().ordinal() == c.get(i + 8).getRank().ordinal() + 1) {
				for (int j = i; j < i + 10; j++) {
					pairsInARow.get(3).add(c.get(j));
				}
			}
		}

		// if 4 pairs in a row, safe it in "pairsInARow" on third place
		for (int i = 0; i < 7; i = i + 2) {
			if (c.get(i + 6) != null && c.get(i).getRank().ordinal() == c.get(i + 2).getRank().ordinal() + 1
					&& c.get(i + 2).getRank().ordinal() == c.get(i + 4).getRank().ordinal() + 1
					&& c.get(i + 4).getRank().ordinal() == c.get(i + 6).getRank().ordinal() + 1) {
				for (int j = i; j < i + 8; j++) {
					pairsInARow.get(2).add(c.get(j));
				}
			}
		}

		// if 3 pairs in a row, safe it in "pairsInARow" on second place
		for (int i = 0; i < 9; i = i + 2) {
			if (c.get(i + 4) != null && c.get(i).getRank().ordinal() == c.get(i + 2).getRank().ordinal() + 1
					&& c.get(i + 2).getRank().ordinal() == c.get(i + 4).getRank().ordinal() + 1) {
				for (int j = i; j < i + 6; j++) {
					pairsInARow.get(1).add(c.get(j));
				}
			}
		}

		// if 2 pairs in a row, safe it in "pairsInARow" on first place
		for (int i = 0; i < 11; i = i + 2) {
			if (c.get(i + 2) != null && c.get(i).getRank().ordinal() == c.get(i + 2).getRank().ordinal() + 1) {
				for (int j = i; j < i + 4; j++) {
					pairsInARow.get(0).add(c.get(j));
				}
			}
		}

	} //end of method pairs

	// TwoPair
	private static int ordinal;

	public static boolean isTwoPair(ArrayList<Card> cards) {
		// Clone the cards, because we will be altering the list
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		// Find the first pair; if found, remove the cards from the list
		boolean firstPairFound = false;
		for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
			for (int j = i + 1; j < clonedCards.size() && !firstPairFound; j++) {
				if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
					ordinal = clonedCards.get(i).getRank().ordinal();
					firstPairFound = true;
					clonedCards.remove(j); // Remove the later card
					clonedCards.remove(i); // Before the earlier one
				}
			}
		}
		boolean secondPairIsOneBiggerThanFirstPair = false;
		boolean secondPairIsOneSmalerThanFirstPair = false;

		boolean foundSecond = false;
		for (int i = 0; i < clonedCards.size() - 1 && !foundSecond; i++) {
			for (int j = i + 1; j < clonedCards.size() && !foundSecond; j++) {

				if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank())
					foundSecond = true;
				int ordinalSecond = clonedCards.get(i).getRank().ordinal();
				ordinal++;
				if (ordinal == ordinalSecond) {
					secondPairIsOneSmalerThanFirstPair = true;
					secondPairIsOneBiggerThanFirstPair = true;
				} else {
					foundSecond = false;
				}
				ordinal--;
				ordinal--;
				if (ordinal == ordinalSecond) {
					secondPairIsOneBiggerThanFirstPair = true;
					secondPairIsOneSmalerThanFirstPair = true;
				} else {
					foundSecond = false;
				}
			}

		}
		return foundSecond;
	}

	public static boolean isThreeOfAKind(ArrayList<Card> cards) {
		boolean found = false;
		for (int i = 0; i < cards.size() - 2 && !found; i++) {
			for (int j = i + 1; j < cards.size() - 1 && !found; j++) {
				for (int k = j + 1; k < cards.size() && !found; k++) {

					if ((cards.get(i).getRank() == cards.get(j).getRank())
							&& (cards.get(i).getRank() == cards.get(k).getRank()))
						found = true;
				}
			}
		}
		return found;
	}

	public static boolean isStraight(ArrayList<Card> cards) {
		ArrayList<Card> sortList = cards;
		Collections.sort(sortList);
		// 5 in a row
		if (sortList.get(0).getRank().ordinal() + 1 == sortList.get(1).getRank().ordinal()
				&& sortList.get(1).getRank().ordinal() + 1 == sortList.get(2).getRank().ordinal()
				&& sortList.get(2).getRank().ordinal() + 1 == sortList.get(3).getRank().ordinal()
				&& sortList.get(3).getRank().ordinal() + 1 == sortList.get(4).getRank().ordinal()) {
			return true;
		} else {
			// Ace as first card Ace, 2, 3, 4, 5
			if (sortList.get(0).getRank().ordinal() == 0 && sortList.get(1).getRank().ordinal() == 1
					&& sortList.get(2).getRank().ordinal() == 2 && sortList.get(3).getRank().ordinal() == 3
					&& sortList.get(4).getRank().ordinal() == 12) {
				return true;
			} else
				return false;
		}
	}

	public static boolean isFlush(ArrayList<Card> cards) {
		int num = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getSuit() == Suit.Jade) {
				num++;
				if (num == 5)
					return true;
			}
		}
		num = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getSuit() == Suit.Pagodas) {
				num++;
				if (num == 5)
					return true;
			}
		}
		num = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getSuit() == Suit.Stars) {
				num++;
				if (num == 5)
					return true;
			}
		}
		num = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getSuit() == Suit.Machete) {
				num++;
				if (num == 5)
					return true;
			}
		}

		return false;
	}

	public static boolean isFullHouse(ArrayList<Card> cards) {
		// Clone the cards, because we will be altering the list
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		// Find the first pair; if found, remove the cards from the list
		boolean first = false;
		boolean second = false;
		// ThreeOfAKind search and remove
		for (int i = 0; i < clonedCards.size() - 2 && !first; i++) {
			for (int j = i + 1; j < clonedCards.size() - 1 && !first; j++) {
				for (int k = j + 1; k < clonedCards.size() && !first; k++) {
					if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()
							&& clonedCards.get(j).getRank() == clonedCards.get(k).getRank()) {
						first = true;
						clonedCards.remove(k);
						clonedCards.remove(j);
						clonedCards.remove(i);
					}
				}
			}
		}
		// the last two cards should be a pair
		if (clonedCards.get(0).getRank().ordinal() == clonedCards.get(1).getRank().ordinal()) {
			second = true;
		}
		// If a first threeOfAKInd was found, see if there is a second pair
		return first && second;
	}

	public static boolean isFourOfAKind(ArrayList<Card> cards) {
		boolean found = false;
		for (int i = 0; i < cards.size() - 2 && !found; i++) {
			for (int j = i + 1; j < cards.size() - 1 && !found; j++) {
				for (int k = j + 1; k < cards.size() && !found; k++) {
					for (int h = k + 1; h < cards.size() && !found; h++) {

						if ((cards.get(i).getRank() == cards.get(j).getRank())
								&& (cards.get(j).getRank() == cards.get(k).getRank())
								&& (cards.get(k).getRank() == cards.get(h).getRank()))
							found = true;
					}
				}
			}
		}
		return found;

	}

	public static boolean isStraightFlush(ArrayList<Card> cards) {
		return isFlush(cards) && isStraight(cards);
	}

}
