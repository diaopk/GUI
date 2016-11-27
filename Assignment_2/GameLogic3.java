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

public class GameLogic3 {
	private XOUltimateBoard mainBoard;
	private int currentPlayer;
	private int mainWinner; // The game winner
	private IntegerProperty[][] subWinner; // Array the holds winners of XOboards
	private int[][] playable; // Array that arranges which cell can be playable.
	private boolean[][] boardFull; // Array that holds which board is full
	
	/* constants define */
	private final int YES = 0;
	private final int NO = 1;
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	
	GameLogic3(XOUltimateBoard ob) {
		mainBoard = ob;
		mainWinner = EMPTY;
		playable = new int[3][3];
		boardFull = new boolean[3][3];
		subWinner = new IntegerProperty[3][3];
		currentPlayer = XPIECE;
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				subWinner[i][j] = new SimpleIntegerProperty(EMPTY);
				playable[i][j] = YES;
				boardFull[i][j] = false;
			}
	}
	
	/* Method to check the given board that is won by which player */
	public void checkSubWinner(int i, int j) {
		
		/* 8 ways to win a small board for EACH piece */
		/* X piece */
		/* 2 ways at the index of [0][0] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].getType() == XPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
		} 
		
		/* 4 ways at the index of [1][1] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].getType() == XPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
		}
		
		/* 2 ways at the index of [2][2] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].getType() == XPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().getType() == XPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left().getType() == XPIECE)
				subWinner[i][j].set(XPIECE);
		}
		
		/* O piece */
		/* 2 ways at the index of [0][0] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].getType() == OPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right() != null && 
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].right().right().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[0][0].bottom().bottom().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
		} 
		
		/* 4 ways at the index of [1][1] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].getType() == OPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].top().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].bottom().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].right().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].left().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftTop().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightBottom().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].rightTop().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[1][1].leftBottom().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
		}
		
		/* 2 ways at the index of [2][2] */
		if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2] != null && 
				mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].getType() == OPIECE) {
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].top().top().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
			
			if (mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().getType() == OPIECE &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left() != null &&
					mainBoard.getXOBoard()[i][j].getXOPiece()[2][2].left().left().getType() == OPIECE)
				subWinner[i][j].set(OPIECE);
		}
		
		/* If this board has a winner, set playable and boardFull */
		if (subWinner[i][j].get() != EMPTY)
			boardFull[i][j] = true;
		else 
			boardFull[i][j] = false;
	}
	
	/* Method to check the game winner */
	/* this method will be called when a value of boardFull changes */
	public void checkWinner() {
		
		/* Similarly there are 8 ways to win the game for EACH player */
		/* X piece */
		/* 2 ways at the index of [0][0] */
		if (subWinner[0][0].get() != EMPTY) {
			if (subWinner[0][0].get() == XPIECE) {
				if (subWinner[0][1].get() == XPIECE && subWinner[0][2].get() == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[1][0].get() == XPIECE && subWinner[2][0].get() == XPIECE)
					mainWinner = XPIECE;
				
			} else { // OPIECE
				if (subWinner[0][1].get() == OPIECE && subWinner[0][2].get() == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[1][0].get() == OPIECE && subWinner[2][0].get() == OPIECE)
					mainWinner = OPIECE;
			}
			
		}
		
		/* 4 ways here */
		if (subWinner[1][1].get() != EMPTY) {
			if (subWinner[1][1].get() == XPIECE) {
				if (subWinner[0][1].get() == XPIECE && subWinner[2][1].get() == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[1][0].get() == XPIECE && subWinner[1][2].get() == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[0][0].get() == XPIECE && subWinner[2][2].get() == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[0][2].get() == XPIECE && subWinner[2][0].get() == XPIECE)
					mainWinner = XPIECE;
				
			} else { //OPIECE
				if (subWinner[0][1].get() == OPIECE && subWinner[2][1].get() == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[1][0].get() == OPIECE && subWinner[1][2].get() == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[0][0].get() == OPIECE && subWinner[2][2].get() == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[0][2].get() == OPIECE && subWinner[2][0].get() == OPIECE)
					mainWinner = OPIECE;
				
			}
		} 
		
		/* 2 ways here */
		if (subWinner[2][2].get() != EMPTY) {
			if (subWinner[2][2].get() == XPIECE) {
				if (subWinner[1][2].get() == XPIECE && subWinner[0][2].get() == XPIECE)
					mainWinner = XPIECE;
				else if (subWinner[2][1].get() == XPIECE && subWinner[2][0].get() == XPIECE)
					mainWinner = XPIECE;
				
			} else { //OPIECE
				if (subWinner[1][2].get() == OPIECE && subWinner[0][2].get() == OPIECE)
					mainWinner = OPIECE;
				else if (subWinner[2][1].get() == OPIECE && subWinner[2][0].get() == OPIECE)
					mainWinner = OPIECE;
				
			}
		}
	}
	
	/* Method to return the mainWinner */
	public int getMainWinner() { return mainWinner; }
	
	/* Method to set the mainWinner */
	public void setMainWinner(int player) { mainWinner = player; }
	
	/* Method to return subWinner value */
	public int getSubWinnerValue(int i, int j) { return subWinner[i][j].get(); }
	
	/* Method to return the array of subWinner */
	public IntegerProperty[][] getSubWinner() { return subWinner; }
	
	/* Method to return subWinner's Integer property */
	public IntegerProperty SubWinnerProperty(int i, int j) { return subWinner[i][j]; } 
	
	/* Method to set the subWinner */
	public void setSubWinner(int i, int j, int winner) { subWinner[i][j].set(winner); }
	
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
