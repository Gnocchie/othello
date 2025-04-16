package de.lmu.bio.ifi;

import szte.mi.Move;

import java.util.List;

public interface Game {

	/**
	 * Make a move for the given player at the given position.
	 *
	 * @param playerOne true if player 1, else player 2.
	 * @param x the x coordinate of the move.
	 * @param y the y coordinate of the move.
	 * @return true if the move was valid, else false.
	 */
	public boolean makeMove(boolean playerOne, int x, int y);

	/**
	 * Check and return the status of the game, if there is a winner, a draw or still running.
	 *
	 * @return the current game status.
	 */
	public GameStatus gameStatus();

	/**
	 * Get all possible moves for the current player.
	 * Return null if it is not the turn of the given player.
	 * The list is empty if there are no possible moves.
	 *
	 * @return a list of all possible moves.
	 */
	public List<Move> getPossibleMoves(boolean playerOne);
}
