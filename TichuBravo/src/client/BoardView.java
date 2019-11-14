package client;

import common.Card;
import common.Card.Rank;
import common.Card.Suit;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Tim
 *
 */
public class BoardView extends VBox {
	protected HBox topBox, middleBox, bottomBox,middleBoxForCards;
	protected Image backOfCardImage, backOfCardTurnedImage, backOfCardTurnedImage2, blackImage, backGroundImage;
	protected ImageView backOfCardImageView, backOfCardTurnedImageView, backOfCardTurnedImageView2, blackImageView,
			backGroundImageView;
	protected StackPane topStackPane, leftStackPane, rightStackPane,middleBoardStackPane;
	protected Label topLabel, leftLabel, rightLabel;
	protected CardLabel playerCardLabel;
	protected Card playerCard;

	public BoardView() {
		// topBox
		this.backOfCardImage = new Image(BoardView.class.getResourceAsStream("..//images//back.png"));
		this.backOfCardImageView = new ImageView(backOfCardImage);
		this.backOfCardImageView.setFitHeight(120);
		this.backOfCardImageView.setFitWidth(120);
		this.backOfCardImageView.setPreserveRatio(true);
		this.topLabel = new Label("14");
		this.topLabel.setId("topLabel");
		this.topStackPane = new StackPane(this.backOfCardImageView, this.topLabel);
		this.topBox = new HBox(topStackPane);
		this.topBox.setAlignment(Pos.CENTER);

		// middleBox
		this.backOfCardTurnedImage = new Image(BoardView.class.getResourceAsStream("..//images//backTurned.png"));
		this.backOfCardTurnedImageView = new ImageView(backOfCardTurnedImage);
		this.backOfCardTurnedImageView.setFitHeight(120);
		this.backOfCardTurnedImageView.setFitWidth(120);
		this.backOfCardTurnedImageView.setPreserveRatio(true);
		this.leftLabel = new Label("14");
		this.leftLabel.setId("leftLabel");
		this.leftStackPane = new StackPane(this.backOfCardTurnedImageView, this.leftLabel);
		
		this.middleBoxForCards=new HBox();
		this.middleBoxForCards.setSpacing(-20);
		
		this.backGroundImage = new Image(BoardView.class.getResourceAsStream("..//images/background.png"));
		this.backGroundImageView = new ImageView(backGroundImage);
		this.backGroundImageView.setFitHeight(650);
		this.backGroundImageView.setFitWidth(650);
		this.backGroundImageView.setPreserveRatio(true);
		this.middleBoardStackPane=new StackPane(this.backGroundImageView,this.middleBoxForCards);

		this.backOfCardTurnedImage2 = new Image(BoardView.class.getResourceAsStream("..//images//backTurned.png"));
		this.backOfCardTurnedImageView2 = new ImageView(backOfCardTurnedImage2);
		this.backOfCardTurnedImageView2.setFitHeight(120);
		this.backOfCardTurnedImageView2.setFitWidth(120);
		this.backOfCardTurnedImageView2.setPreserveRatio(true);
		this.rightLabel = new Label("14");
		this.rightLabel.setId("rightLabel");
		this.rightStackPane = new StackPane(this.backOfCardTurnedImageView2, this.rightLabel);

		this.middleBox = new HBox(this.leftStackPane, this.middleBoardStackPane, this.rightStackPane);
		this.middleBox.setAlignment(Pos.CENTER);

		this.bottomBox=new HBox();
		// bottomBox
		
		this.bottomBox=new HBox();
		for (int i = 0; i <= 13; i++) {
			this.playerCardLabel = new CardLabel();
			this.playerCard = new Card(Rank.Ace, Suit.Machete);
			this.playerCardLabel.setCard(playerCard);
			this.playerCardLabel.setId("cardButton");
			this.bottomBox.getChildren().add(this.playerCardLabel);
			this.bottomBox.setAlignment(Pos.CENTER);
			this.bottomBox.setSpacing(-20);
		}
		

		// Putting everything in the container
		this.getChildren().addAll(this.topBox, this.middleBox, this.bottomBox);
		this.setAlignment(Pos.CENTER);
	}
	
	public CardLabel getPlayerCardLabel() {
		return this.playerCardLabel;
	}

	public void getPlayerCardLabel(Event e) {
		// TODO Auto-generated method stub
		
	}

	

}
