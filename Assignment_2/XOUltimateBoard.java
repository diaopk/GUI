package Assignment_2;

/* An implementation of the rendering XOBoards on the TOP */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class XOUltimateBoard extends Pane {
	
	private XOBoard[][] renders; // Array that holds all the render position of XOBoards
	//private GameLogic logic1; // Another game logic based on my mind..the wrong one. then I made a new logic just down here
	private GameLogic3 logic;
	private double cell_width, cell_height;
	private Rectangle back;
	private Line l1, l2;
	private Ellipse e;
	
	/* Constants define */
	private final int YES = 0;
	private final int NO = 1;
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	
	public XOUltimateBoard() {
		renders = new XOBoard[3][3];
		back = new Rectangle();
		e = new Ellipse();
		l1 = new Line(); l2 = new Line();

		back.setFill(Color.BLACK);
		e.setStroke(Color.BLACK);
		e.setFill(null);
		l1.setStroke(Color.BLACK);
		l2.setStroke(Color.BLACK);
		
		l1.setStartX(0);
		l1.setStartY(0);
		l2.setStartY(0);
		l2.setEndX(0);
		
		logic = new GameLogic3(this);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				renders[i][j] = null;
			}
		}
		
		this.getChildren().addAll(l1, l2, e, back);
		
	}
	
	@Override
	public void resize(double width, double height) {
		super.resize(width, height); // Make sure resize properly.
		
		cell_width = width / 3.0;
		cell_height = height / 3.0;
		
		/* If no winner occur, display the layout */
		if (logic.getMainWinner() == EMPTY) {
		
			// Set the width and height for the back
			back.setWidth(width);
			back.setHeight(height);
			
			/* We need to reset the sizes and positions of all XOBoards that were placed */
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					//if (logic.getSubWinnerValue(i, j) == EMPTY) {
						if (renders[i][j] != null) {
							renders[i][j].relocate(i * cell_width, j * cell_height);
							renders[i][j].resize(cell_width-4, cell_height-4);
						}
						else if (logic.getPlayable()[i][j] == YES && renders[i][j] == null){
							renders[i][j] = new XOBoard(this);
							renders[i][j].relocate(i * cell_width, j * cell_height);
							renders[i][j].resize(cell_width-4, cell_height-4);
							this.getChildren().add(renders[i][j]);
						}
					
					//}
				}
			
		} else {
			
			/* 
			 * If someone win this game, then the piece of the winner
			 * will show up on the screen.
			 */
			for(int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					this.getChildren().remove(renders[i][j]);
					renders[i][j] = null;
				}
			
			if (logic.getMainWinner() == XPIECE) {
				l1.setEndX(width);
				l1.setEndY(height);
				l2.setStartX(width);
				l2.setEndY(height);
				
			} else {
				e.setCenterX(width / 2);
				e.setCenterY(height / 2);
				e.setRadiusX(width / 2);
				e.setRadiusY(height / 2);
			}

			back.setFill(null);
		}
		
		
	}
	
	/* Method to reset every single XO pieces in each cell and points */
	public void reset() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (renders[i][j] != null) {
					renders[i][j].resetGame();
					logic.setPlayable(i, j, YES);
					logic.setSubWinner(i, j, EMPTY);
					logic.setBoardFull(i, j, false);
				
				} else {
					renders[i][j] = new XOBoard(this);
					renders[i][j].relocate(i * cell_width, j * cell_height);
					renders[i][j].resize(cell_width-4, cell_height-4);
					this.getChildren().add(renders[i][j]);
					
					logic.setPlayable(i, j, YES);
					logic.setSubWinner(i, j, EMPTY);
					logic.setBoardFull(i, j, false);
				}
			}
		}
	
		logic.setMainWinner(EMPTY);
		back.setFill(Color.BLACK);
		this.getLogic().setCurrentplayer(XPIECE);
	}

	/* Method to return logic */
	public GameLogic3 getLogic() { return logic; }
	
	/* Method to return XOBoards */
	public XOBoard[][] getXOBoard() { return renders; }
	
	/* Method to calculate a index of cell of XOBoard to be place a piece */
	public void placePiece(final double x, final double y) {
		/* The index of a XOBoard */
		int indexX = (int) (x / cell_width);
		int indexY = (int) (y / cell_height);
		
		/* calculating the index of XO piece to be displayed in an selected XOBoard */
		double indXPiz = x - indexX * cell_width;
		double indYPiz = y - indexY * cell_height;
		
		/* 
		 * If the cell that a player is trying to place a piece
		 * is playable, allowing to place.
		 */
		if (logic.getPlayable()[indexX][indexY] == YES 
				&& logic.getBoardFull()[indexX][indexY] == false) {
			renders[indexX][indexY].placePiece(indXPiz,indYPiz);
			renders[indexX][indexY].resize(cell_width, cell_height);
			renders[indexX][indexY].relocate(indexX * cell_width, indexY * cell_height);
			this.logic.checkSubWinner(indexX, indexY);
			
			/* 
			 * Here is code based on the game rule
			 * to restrict how a player to place a piece.
			 */
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (i == renders[indexX][indexY].getIndexX() &&
					j == renders[indexX][indexY].getIndexY())
						logic.setPlayable(i, j, YES);
					else
						logic.setPlayable(i, j, NO);
				}
			
		}
		
		/* Analyse the occurrence of case of (boardFull-true, playable-YES) */
		/* 
		 * because we cannot place a piece on the board of (boardFull-true, playable-YES)
		 * reset other boards to playable-YES, So others boards are playable.
		 */
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				if (logic.getBoardFull()[i][j] == true &&
				logic.getPlayable()[i][j] == YES) {
					
					/* Reset all playable boards to true but the case */
					for (int a = 0; a < 3; a++)
						for (int b = 0; b < 3; b++)
							if (logic.getBoardFull()[a][b] == false &&
							logic.getPlayable()[a][b] == NO)
								logic.setPlayable(a, b, YES);
				}
		}
	}
	
}
