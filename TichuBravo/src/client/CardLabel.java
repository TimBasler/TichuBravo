package client;

import common.Card;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Tim
 *
 */
public class CardLabel extends Button {
	protected String fileName;
	protected CardLabel cardLabel;
	
	public CardLabel() {
		super();
	}
	
	public CardLabel makeCardLabel(Card card) {
		if(card!=null) {
			String fileName=cardToFileName(card);
			Image image = new Image(CardLabel.class.getResourceAsStream("..//images"+fileName));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(120);
			imageView.setFitWidth(120);
			imageView.setPreserveRatio(true);
			this.cardLabel=new CardLabel();
			this.cardLabel.setGraphic(imageView);
		} else {
			this.cardLabel.setGraphic(null);
		}
		return this.cardLabel;
	}

	public void setCard(Card card) {
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
		String suit = card.getSuit().toString();
		String rank = card.getRank().toString();
		return suit + rank + ".jpg";
	}
	
	public String toString() {
		return this.fileName;
	}

}
