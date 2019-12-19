package client;

import common.Card;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Loris+Tim
 *
 *
 */
public class CardLabel extends Button implements Comparable<CardLabel>{
	protected String fileName;
	protected Card card;
	
	public Card getCard() {
		return card;
	}

	public CardLabel(Card card) {
		super();
		this.card = card;
		if(card!=null) {
			String fileNameString=cardToFileName(card);
			System.out.println(fileNameString);
			Image image = new Image(CardLabel.class.getResourceAsStream("..//images//"+fileNameString));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(120);
			imageView.setFitWidth(120);
			imageView.setPreserveRatio(true);
			this.setGraphic(imageView);
		} else {
			this.setGraphic(null);
		}
	}

	public void setCard(Card card) {
		this.card = card;
		if (card != null) {
			this. fileName = cardToFileName(card);
			Image image = new Image(CardLabel.class.getResourceAsStream("..//images//"+fileName));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(120);
			imageView.setFitWidth(120);
			imageView.setPreserveRatio(true);
			this.setGraphic(imageView);
		} else {
			this.setGraphic(null);
		}
	}

	// TODO do it 4 spezialCards
	private static String cardToFileName(Card card) {
		if(!card.isSpecial()) {
			String suit = card.getSuit().toString();
			String rank = card.getRank().toString();
			return suit + rank + ".jpg";
		}else {
			return card.toString()+".jpg";
		}
	}
	
	public String toString() {
		return this.fileName;
	}

	@Override
	public int compareTo(CardLabel cardLabel) {
		return this.card.compareTo(cardLabel.getCard());
	}

}
