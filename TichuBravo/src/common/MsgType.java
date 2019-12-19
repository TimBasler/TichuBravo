package common;

/**
 * @author Tim+Dominik
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
 * pass = next player
 * winnerOfTheRound = ID from winner of the round
 * teamChange = player will be changed to the other team
 * dog = next player in the same team
 * noCards = the player is finish
 * fromTeamOne = msg from team one
 * toFastestFinisher = player who finished first
 * announcementEvaluation = evaluate small and grand tichu
 * 
 */
public enum MsgType {


	name, msg, turn, game, clientId, card, cards, whoHasMahJong, player,
	currentPlayerID, pass, winnerOfTheRound, pointsTeamOne, pointsTeamTwo, winnerLabelTeamOne,winnerLabelTeamTwo, teamChange, dog, noCards, 
	fromTeamOne, fromTeamTwo, playerIDOne, playerIDTwo, playerIDThree, playerIDFour,
	toFastestFinisher,currentPlayerName, announcementEvaluation;



}
