package Assignment_2;

/* 
 * This class is to calculate how a player can win 
 * and handle every single information about game logic
 * on the BACK of the mainBoard, which is XOUltimateBoard
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.Bindings;
import javafx.beans.Observable;

public class GameLogic2 {
	private XOUltimateBoard mainBoard;
	private int currentPlayer;
	private int mainWinner; // The game winner
	private int[][] subWinner; // Array the holds winners of XOboards
	private int[][] playable; // Array that arranges which cell can be playable.
	private boolean[][] boardFull; // Array that holds which board is full
	private IntegerProperty[][] subWinnerProperty;
	
	/* constants define */
	private final int YES = 0;
	private final int NO = 1;
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	
	GameLogic2(XOUltimateBoard ob) {
		mainBoard = ob;
		mainWinner = EMPTY;
		subWinner = new int[3][3];
		playable = new int[3][3];
		boardFull = new boolean[3][3];
		currentPlayer = XPIECE;
		subWinnerProperty = new SimpleIntegerProperty[3][3];
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				subWinner[i][j] = EMPTY;
				playable[i][j] = YES;
				boardFull[i][j] = false;
				//subWinnerProperty[i][j].set(subWinner[i][j]);
			}
		
	}
	
	/* Method to check the given board that is won by which player */
	public int checkSubWinner(int i, int j) {
		
		/* 8 ways to win a small board for EACH piece */
		/* X piece */
		/* 2 ways at the index of [0][0] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].getType() == XPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right().getType() == XPIECE) {
				subWinner[i][j] = XPIECE; subWinnerProperty[i][j].set(XPIECE); }
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
		} 
		
		/* 4 ways at the index of [1][1] */
		else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].getType() == XPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
		}
		
		/* 2 ways at the index of [2][2] */
		else if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].getType() == XPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left().getType() == XPIECE)
				subWinner[i][j] = XPIECE;
		}
		
		/* O piece */
		/* 2 ways at the index of [0][0] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].getType() == OPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
		} 
		
		/* 4 ways at the index of [1][1] */
		else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].getType() == OPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
		}
		
		/* 2 ways at the index of [2][2] */
		else if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].getType() == OPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
			
			else if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left().getType() == OPIECE)
				subWinner[i][j] = OPIECE;
		}
		
		/* If this board has a winner, set playable and boardFull */
		if (subWinner[i][j] != EMPTY)
			boardFull[i][j] = true;
		else
			boardFull[i][j] = false;
		
		/* 
		 * Finally return the winner of this board
		 * if it doesn't have a winner, return EMPTY by default.
		 */
		return subWinner[i][j];
		
	}
	
	/* Method to check the game winner */
	/* this method will be called when a value of boardFull changes */
	public int checkWinner() {
		
		/* Similarly there are 8 ways to win the game for EACH player */
		/* X piece */
		/* 2 ways at the index of [0][0] */
		if (subWinner[0][0] != EMPTY) {
			if (subWinner[0][0] == XPIECE) {
				if (subWinner[0][1] == XPIECE && subWinner[0][2] == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[1][0] == XPIECE && subWinner[2][0] == XPIECE)
					mainWinner = XPIECE;
				
			} else { // OPIECE
				if (subWinner[0][1] == OPIECE && subWinner[0][2] == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[1][0] == OPIECE && subWinner[2][0] == OPIECE)
					mainWinner = OPIECE;
			}
			
		}
		/* 4 ways here */
		else if (subWinner[1][1] != EMPTY) {
			if (subWinner[1][1] == XPIECE) {
				if (subWinner[0][1] == XPIECE && subWinner[2][1] == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[1][0] == XPIECE && subWinner[1][2] == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[0][0] == XPIECE && subWinner[2][2] == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[0][2] == XPIECE && subWinner[2][0] == XPIECE)
					mainWinner = XPIECE;
				
			} else { //OPIECE
				if (subWinner[0][1] == OPIECE && subWinner[2][1] == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[1][0] == OPIECE && subWinner[1][2] == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[0][0] == OPIECE && subWinner[2][2] == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[0][2] == OPIECE && subWinner[2][0] == OPIECE)
					mainWinner = OPIECE;
				
			}
			
		/* 2 ways here */
		} else if (subWinner[2][2] != EMPTY) {
			if (subWinner[2][2] == XPIECE) {
				if (subWinner[1][2] == XPIECE && subWinner[0][2] == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[2][1] == XPIECE && subWinner[2][0] == XPIECE)
					mainWinner = XPIECE;
				
			} else { //OPIECE
				if (subWinner[1][2] == OPIECE && subWinner[0][2] == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[2][1] == OPIECE && subWinner[2][0] == OPIECE)
					mainWinner = OPIECE;
				
			}
		}
		
		return mainWinner;
	}
	
	/* Method to return the mainWinner */
	public int getMainWinner() { return mainWinner; }
	
	/* Method to return subWinner */
	public int[][] getSubWinner() { return subWinner; }
	
	public int subWinnerProperty(int i, int j) { return subWinner[i][j]; }
	
	public IntegerProperty getSubProperty(int i, int j) { return subWinnerProperty[i][j]; }
	
	/* Method to set the subWinner */
	//public void setSubWinner(int i, int j, int winner) { subWinner[i][j].set(winner); }
	
	/* Method to return playable boards */
	public int[][] getPlayable() { return playable; }
	
	/* Method to set the playable board */
	public void setPlayable(int i, int j, int value) { playable[i][j] = value; }
	
	/* Method to return boardFull */
	public boolean[][] getBoardFull() { return boardFull; }
	
	/* Method to set the boardFull */
	public void setBoardFull(int i, int j, boolean value) { boardFull[i][j] = value; }
	
	/* Method to return current player */
	public int getCurrentplayer() { return currentPlayer; }
	
	/* Method to set the current player */
	public void setCurrentplayer(int player) { currentPlayer = player; }
	
	
}
