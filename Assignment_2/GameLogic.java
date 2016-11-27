package Assignment_2;

/*
 * This class sets a list of rules how a game to play 
 * and how a winner can be calculated.
 */
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.*;

public class GameLogic {
	private XOUltimateBoard mainBoard;
	private int mainWinner; // The game winner
	private boolean[][] boardFull; // Array that holds cell that is full
	private int[][] subWinner; // the cell winner
	private int[][] subXPoint, subOPoint; // Arrays that hold cell point in boards.
	
	/* Players' points to be calculated */
	private int XPoint;
	private int OPoint;
	
	/* Instants defined */
	private final int SUBPOINT = 1;
	private final int MAINPOINT = 3;
	/* List of players */
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	
	GameLogic(XOUltimateBoard main) {
		mainBoard = main;
		XPoint = 0;
		OPoint = 0;
		mainWinner = EMPTY;
		boardFull = new boolean[3][3];
		subWinner = new int[3][3];
		subXPoint = new int[3][3];
		subOPoint = new int[3][3];
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				boardFull[i][j] = false;
				subWinner[i][j] = EMPTY;
				subXPoint[i][j] = 0;
				subOPoint[i][j] = 0;
			}
		
		mainBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				int count = 0;
				
				// TODO Auto-generated method stub
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++) {
						checkFull(boardFull[i][j], mainBoard.getXOBoard()[i][j]);
						
						if (boardFull[i][j] == true) { // If this board is full
							checkSubBoard(i, j);
							count++;
						}
					}
				
				if (count == 9) // If all boards are full
					sumPoint();
				if (XPoint > OPoint)
					mainWinner = XPIECE;
				else
					mainWinner = OPIECE;
				
			}
		});
		/*
		mainBoard.pressedProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				int count = 0;
				
				// TODO Auto-generated method stub
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++) {
						checkFull(boardFull[i][j], mainBoard.getXOBoard()[i][j]);
						
						if (boardFull[i][j] == true) { // If this board is full
							checkSubBoard(i, j);
							count++;
						}
					}
				
				if (count == 9) // If all boards are full
					sumPoint();
				if (XPoint > OPoint)
					mainWinner = XPIECE;
				else
					mainWinner = OPIECE;
				
				
			}
			
		});*/
		
	}
	
	/* 
	 * private method can only be called by GameLogic class itself 
	 * and this method is to add points for pieces
	 * when they meet the condition.
	 */
	/*private void addPoint(int who) {
		if (who == XPIECE)
			XPoint =+ SUBPOINT;
		else if (who == OPIECE)
			OPoint =+ SUBPOINT;
		
	}*/
	
	/* 
	 * Method to check which player wins a given cell of XOBoard 
	 * and this method can only be called when the cell is full.
	 * if cell is not full and call this method, a exception will be thrown
	 * because the cell is not full, which means not all cells filled by XO pieces
	 */
	public void checkSubBoard(int a, int b) {
		
		/* 8 ways to win a XOBard for X piece */
		/* 4 ways here at index of [1][1] */
		if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].getType() == XPIECE) {
			if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].left().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].right().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].top().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].bottom().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].leftTop().getType() == XPIECE	&&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].rightBottom().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].rightTop().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].leftBottom().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
					
		/* two ways here at index of [0][0] */
		} else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].getType() == XPIECE) {
			if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].right().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].right().right().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].bottom().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].bottom().bottom().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
				
		/* two ways here at index of [2][2] */
		} else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].getType() == XPIECE) {
			if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].top().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].top().top().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].left().getType() == XPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].left().left().getType() == XPIECE)
				subXPoint[a][b] += SUBPOINT;
		}
		
		/* 8 ways to win a XOBard for O piece */
		/* 4 ways here at index of [1][1] */
		if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].getType() == OPIECE) {
			if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].left().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].right().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;

			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].top().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].bottom().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].leftTop().getType() == OPIECE	&&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].rightBottom().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].rightTop().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[1][1].leftBottom().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
					
		/* two ways here at index of [0][0]*/
		} else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].getType() == OPIECE) {
			if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].right().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].right().right().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].bottom().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[0][0].bottom().bottom().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
				
		/* two ways here at index of [2][2] */
		} else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].getType() == OPIECE) {
			if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].top().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].top().top().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
			
			else if (this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].left().getType() == OPIECE &&
					this.mainBoard.getXOBoard()[a][b].getXOPiece()[2][2].left().left().getType() == OPIECE)
				subOPoint[a][b] += SUBPOINT;
		}
		
		/* 
		 * To set the subWinner for a given index of cell 
		 * and add 3 points when pieces win a XOboard.
		 */
		if (subXPoint[a][b] > subOPoint[a][b]) {
			subWinner[a][b] = XPIECE;
			XPoint += MAINPOINT;
			
		} else if (subOPoint[a][b] > subXPoint[a][b]) {
			subWinner[a][b] = OPIECE;
			OPoint += MAINPOINT;
			
		} else // OPoint == XPoint
			subWinner[a][b] = EMPTY;
		
	}
	
	/* Method to check a given board is full ro not */
	public void checkFull(boolean cell, XOBoard board) {
		/* to count the number of cell is full in the given board */
		int count = 0;
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (board.getXOPiece()[i][j] != null)
					count++;
		
		if (count == 9)
			cell = true;
		else
			cell = false;
		
	}
	
	public void sumPoint() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				
				/* Include MAINPOINT */
				XPoint += subXPoint[i][j];
				OPoint += subOPoint[i][j];
			}
				
	}
	
	/* 
	 * Method to return the point of given player
	 * and return 0 by default
	 */
	public int getPoint(int selectedPlayer) {
		if (selectedPlayer == XPIECE)
			return XPoint;
		else if (selectedPlayer == OPIECE)
			return OPoint;
		else 
			return 0;
		
	}
	
	public int getSubPoint(int i, int j, int player) {
		if (player == XPIECE)
			return subXPoint[i][j];
		else
			return subOPoint[i][j];
		
	}
	
	/* Method to set the boolean value to boardFull */
	public boolean getBoardFull(int i, int j) { return boardFull[i][j]; }

	/* Method to set the subWinner for a given index */
	public void setSubWinner(int i, int j, int xopiece) { subWinner[i][j] = xopiece; }
	
	/* Method to return the subWinner of a given cell */
	public int getSubWinner(int i, int j) { return this.subWinner[i][j]; }
	
	/* Method to set the mainWinner */
	public void setMainWinner(int winner) { mainWinner = winner; }
	
	/* Method to return a mainWinner */
	public int getMainWinner() { return mainWinner; }
	
	/* Method to reset points */
	public void clearPoint() { XPoint = 0; OPoint = 0; }
	
}
