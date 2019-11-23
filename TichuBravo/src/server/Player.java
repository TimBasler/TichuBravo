package server;

/**
 * @author Dominik
 *
 */
public class Player {
	private int ID;
	private boolean isTeamOne;
	
	public Player(int ID, boolean isTeamOne) {
		this.ID = ID;
		this.isTeamOne = isTeamOne;
	}

	public int getID() {
		return ID;
	}

	public boolean isTeamOne() {
		return isTeamOne;
	}

	@Override
	public String toString() {
		return "Player [ID=" + ID + ", isTeamOne=" + isTeamOne + "]";
	}

}
