package client;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import common.Card;
import common.Card.Rank;
import common.Card.Suit;

public class HandTypeTest {

	Card m4 = new Card(Rank.Four, Suit.Machete);
	Card p4 = new Card(Rank.Four, Suit.Pagodas);
	Card j4 = new Card(Rank.Four, Suit.Jade);
	Card s6 = new Card(Rank.Six, Suit.Stars);
	Card j6 = new Card(Rank.Six, Suit.Jade);
	Card m7 = new Card(Rank.Seven, Suit.Machete);
	Card p7 = new Card(Rank.Seven, Suit.Pagodas);
	Card j7 = new Card(Rank.Seven, Suit.Jade);
	Card s8 = new Card(Rank.Eight, Suit.Stars);
	Card j8 = new Card(Rank.Eight, Suit.Jade);
	Card j9 = new Card(Rank.Nine, Suit.Jade);
	Card jt = new Card(Rank.Ten, Suit.Jade);
	Card jj = new Card(Rank.Jack, Suit.Jade);
	Card jk = new Card(Rank.King, Suit.Jade);
	Card mk = new Card(Rank.King, Suit.Machete);
	Card pk = new Card(Rank.King, Suit.Pagodas);
	Card sk = new Card(Rank.King, Suit.Stars);

	ArrayList<Card> list1 = new ArrayList<Card>();
	ArrayList<Card> list2 = new ArrayList<Card>();

	@Before
	public void makeList() {
		list1.add(s6);
		list1.add(j6);
		list1.add(s8);
		list1.add(j8);
		list1.add(m4);
		list1.add(p4);
		list1.add(j4);
		list1.add(m7);
		list1.add(p7);
		list1.add(j7);
		list1.add(j9);
		list1.add(jt);
		list1.add(jj);
		list1.add(jk);
		list1.add(mk);
		list1.add(pk);
		list1.add(sk);
		
		list2.add(s6);
		list2.add(j6);
		list2.add(p7);
		list2.add(j7);
		list2.add(s8);
		list2.add(j8);
		list2.add(j9);
		list2.add(jt);
		list2.add(jj);
		

		Collections.sort(list1);
		Collections.sort(list2);
		
		
	}

	// System.out.println(clonedCards.get(i).toString());
	// System.out.println(clonedCards.get(j).toString());
	
	@Test //ok
		public void findFullHouseTest() {
			HandType.findPair(list1);
			HandType.findThreeOfAKind(list1);
			HandType.findFullHouse();
			assertTrue(HandType.fullHouseList.size() == 14);
		}

	//@Test //ok aber ändern - nur eine Strasse speichern der gleichen Farbe
	public void findBombStraightFlushTest() {
		HandType.findBombStraightFlush2(list2);
		for (int i = 0; i < HandType.BombStraightFlushList.size(); i++) {
			for (int j = 0; j < HandType.BombStraightFlushList.get(i).size(); j++) {
				System.out.println(HandType.BombStraightFlushList.get(i).get(j).toString());
			}
		}
		System.out.println(HandType.BombStraightFlushList.size());
		assertTrue(HandType.BombStraightFlushList.size() == 3);
	}

	// @Test //ok
	public void findBombTest() {
		HandType.findBomb(list1);
		assertTrue(HandType.bombList.size() == 1);
	}

	// @Test //ok
	public void findStraightTest() {
		HandType.straightList.clear();
		HandType.findStraight(list1);
		for (int i = 0; i < HandType.straightList.size(); i++) {
			for (int j = 0; j < HandType.straightList.get(i).size(); j++) {
				System.out.println(HandType.straightList.get(i).get(j).toString());
			}
		}
		assertTrue(HandType.straightList.size() == 3);
	}

	// @Test //ok
	public void findThreeOfAKindTest() {
		HandType.threeOfAKindList.clear();
		HandType.findThreeOfAKind(list1);
		for (int i = 0; i < HandType.threeOfAKindList.size(); i++) {
			for (int j = 0; j < HandType.threeOfAKindList.get(i).size(); j++) {
				System.out.println(HandType.threeOfAKindList.get(i).get(j).toString());
			}
		}
		System.out.println(HandType.threeOfAKindList.size());
		assertTrue(HandType.threeOfAKindList.size() == 3);
	}

	// @Test //ok
	public void findPairsInARowTest() {
		HandType.onePairList.clear();
		HandType.pairsInARow.clear();
		ArrayList<Card> newList = new ArrayList<Card>();
		newList.add(s6);
		newList.add(m4);
		newList.add(j6);
		newList.add(m7);
		newList.add(s8);
		newList.add(j8);
		newList.add(p7);
		newList.add(p4);
		Collections.sort(newList);
		HandType.findPairsInARow(newList);
		for (int i = 0; i < HandType.pairsInARow.size(); i++) {
			System.out.println(HandType.pairsInARow.get(i).toString());
			for (int j = 0; j < HandType.pairsInARow.get(i).size(); j++) {
				// System.out.println(HandType.pairsInARow.get(i).get(j).toString());
			}
		}
		assertTrue(HandType.pairsInARow.size() == 3);
	}

	// @Test //ok
	public void findPairTest() {
		HandType.onePairList.clear();
		ArrayList<Card> newList = new ArrayList<Card>();
		newList.add(s6);
		newList.add(m4);
		newList.add(j6);
		newList.add(s8);
		newList.add(j8);

		newList.add(p4);
		HandType.findPair(newList);
		for (int i = 0; i < HandType.onePairList.size(); i++) {
			for (int j = 0; j < HandType.onePairList.get(i).size(); j++) {
				System.out.println(HandType.onePairList.get(i).get(j).toString());
			}
		}
		assertTrue(HandType.onePairList.size() == 3);
	}
}
