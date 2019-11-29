package common;

/**
 * @author Dominik
 *
 */

/**
 * name = name of player; 
 * msg = chat message;
 * turn = played cards; 
 * game = game related messages;
 * whoHasMahJong = ID from the player with MahJong card
 * player = playerID + isTeamOne send to the server
 * cards = dealt cards from the deck
 * 
 */
public enum MsgType {
	name, msg, turn, game, clientId, card, cards, whoHasMahJong, player, currentPlayerID, pass, winnerOfTheRound,pointsTeamOne,pointsTeamTwo;

}
