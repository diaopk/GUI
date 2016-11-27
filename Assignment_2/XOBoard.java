package Assignment_2;

/* An implementation of the XO board and the game logic */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class XOBoard extends Pane {

	private int[][] board; // Array that holds all pieces
	private XOPiece[][] rendersPiece; // Array that holds all the render pieces
	private Rectangle back; // Background of the board;
	private Line h1, h2, v1, v2; // Horizontal and vertical grid lines
	private double cell_width, cell_height; 
	
	/* var to store the index a piece just placed to */
	private int indexX;
	private int indexY;
	private Translate ch_one, ch_two, cw_one, cw_two;
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	private XOUltimateBoard object;
	
	/* Shape to represent a subWinner */
	private Line l1, l2;
	private Ellipse e;
	
	public XOBoard(XOUltimateBoard ob) {
		board = new int[3][3];
		rendersPiece = new XOPiece[3][3];
		object = ob;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
				rendersPiece[i][j] = null;
			}
		}
		
		back = new Rectangle();
		h1 = new Line(); h2 = new Line();
		v1 = new Line(); v2 = new Line();

		l1 = new Line(); l2 = new Line();
		e = new Ellipse();
		
		back.setFill(Color.WHITE);
		h1.setStroke(Color.BLACK);
		h2.setStroke(Color.BLACK);
		v1.setStroke(Color.BLACK);
		v2.setStroke(Color.BLACK);
		
		l1.setStroke(Color.BLACK);
		l2.setStroke(Color.BLACK);
		e.setStroke(Color.BLACK);
		e.setFill(null);
		
		/* 
		 * The horizontal lines only need the endx/y value modified
		 * the rest of values can be zero.
		 */
		h1.setStartX(0); h1.setStartY(0); h1.setEndY(0);
		h2.setStartX(0); h2.setStartY(0); h2.setEndY(0);
		v1.setStartX(0); v1.setStartY(0); v1.setEndX(0);
		v2.setStartX(0); v2.setStartY(0); v2.setEndX(0);
		
		l1.setStartX(0);
		l1.setStartY(0);
		l2.setStartY(0);
		l2.setEndX(0);
		
		/*
		 * Setup the translation of one cell height or width and two cell heights.
		 * 2-D translate constructor initialization
		 */
		ch_one = new Translate(0, 0);
		ch_two = new Translate(0, 0);
		h1.getTransforms().add(ch_one);
		h2.getTransforms().add(ch_two);
		
		cw_one = new Translate(0, 0);
		cw_two = new Translate(0, 0);
		v1.getTransforms().add(cw_one);
		v2.getTransforms().add(cw_two);
		
		/* Add the rectangle and lines to this group */
		this.getChildren().addAll(back, h1, h2, v1, v2);
		
	}
	
	@Override
	public void resize(double width, double height) {
		super.resize(width, height); // Make sure resize properly.
		
		cell_width = width / 3.0;
		cell_height = height / 3.0;
		
		/* resize the rectangle to take full size of the widget */
		back.setWidth(width);
		back.setHeight(height);
		
		/* Set a new y on the horizontal lines and translate them into place */
		ch_one.setY(cell_height);
		ch_two.setY(2 * cell_height);
		h1.setEndX(width);
		h2.setEndX(width);
		
		/* Set a new X on the vertical lines and translate them into place */
		cw_one.setX(cell_width);
		cw_two.setX(2 * cell_width);
		v1.setEndY(height);
		v2.setEndY(height);
		
		/* We need to reset the sizes and positions of all XOPIECE that were placed */
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != 0) {
					rendersPiece[i][j].relocate(i * cell_width, j * cell_height);
					rendersPiece[i][j].resize(cell_width, cell_height);
				}
			}
		}
		
	}
	
	/* method to remove XO pieces invoked by the reference 'object' */
	public void resetGame() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
				this.getChildren().remove(rendersPiece[i][j]);
				rendersPiece[i][j] = null;
			}
		
	}
	
	/* Method to return specified index of piece */
	public XOPiece[][] getXOPiece() { return rendersPiece; }
	
	/* Method to check the XO piece around the given node */
	/* The method has to check each single node position on the XO board 
	 * when a piece is placed on this given board */
	public void checkPiz(XOPiece[][] node) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				
				/* Checking the first row of pieces */
				if (i == 0) { // index of [0][0]
					if (j == 0 && node[0][0] != null) {
						if (board[0][1] != EMPTY) 
							node[0][0].setRight(node[0][1]);
						if (board[1][0] != EMPTY)
							node[0][0].setBottom(node[1][0]);
						if (board[1][1] != EMPTY)
							node[0][0].setRightBottom(node[1][1]);
						
						/* make default node */
						node[0][0].setLeftBottom(null);
						node[0][0].setLeft(null);
						node[0][0].setLeftTop(null);
						node[0][0].setTop(null);
						node[0][0].setRightTop(null);
					}
					
					if (j == 1 && node[0][1] != null) { // index of [0][1]
						if(board[0][0] != EMPTY)
							node[0][1].setLeft(node[0][0]);
						if (board[1][0] != EMPTY)
							node[0][1].setLeftBottom(node[1][0]);
						if (board[1][1] != EMPTY)
							node[0][1].setBottom(node[1][1]);
						if (board[1][2] != EMPTY)
							node[0][1].setRightBottom(node[1][2]);
						if (board[0][2] != EMPTY)
							node[0][1].setRight(node[0][2]);
						
						/* make default value */
						node[0][1].setLeftTop(null);
						node[0][1].setTop(null);
						node[0][1].setRightTop(null);
					}
					
					if (j == 2 && node[0][2] != null){ // index of [0][2]
						if (board[0][1] != EMPTY)
							node[0][2].setLeft(node[0][1]);
						if (board[1][1] != EMPTY)
							node[0][2].setLeftBottom(node[1][1]);
						if (board[1][2] != EMPTY)
							node[0][2].setBottom(node[1][2]);
						
						/* make default value */
						node[0][2].setLeftTop(null);
						node[0][2].setTop(null);
						node[0][2].setRightTop(null);
						node[0][2].setRight(null);
						node[0][2].setRightBottom(null);
					
					}
				} // End of the first row
				
				/* Checking the second row of pieces*/
				if (i == 1) { 
					if (j == 0 && node[1][0] != null) { // index of [1][0]
						if (board[0][0] != EMPTY)
							node[1][0].setTop(node[0][0]);
						if (board[0][1] != EMPTY)
							node[1][0].setRightTop(node[0][1]);
						if (board[1][1] != EMPTY)
							node[1][0].setRight(node[1][1]);
						if (board[2][1] != EMPTY)
							node[1][0].setRightBottom(node[2][1]);
						if (board[2][0] != EMPTY)
							node[1][0].setBottom(node[2][0]);
						
						/* make default value */
						node[1][0].setLeftBottom(null);
						node[1][0].setLeft(null);
						node[1][0].setLeftTop(null);
					} 
					
					if (j == 1 && node[1][1] != null) { // index of [1][1]
						if (board[1][0] != EMPTY)
							node[1][1].setLeft(node[1][0]);
						if (board[0][0] != EMPTY)
							node[1][1].setLeftTop(node[0][0]);
						if (board[0][1] != EMPTY)
							node[1][1].setTop(node[0][1]);
						if (board[0][2] != EMPTY)
							node[1][1].setRightTop(node[0][2]);
						if (board[1][2] != EMPTY)
							node[1][1].setRight(node[1][2]);
						if (board[2][2] != EMPTY)
							node[1][1].setRightBottom(node[2][2]);
						if (board[2][1] != EMPTY)
							node[1][1].setBottom(node[2][1]);
						if (board[2][0] != EMPTY)
							node[1][1].setLeftBottom(node[2][0]);
						
					} if (j == 2 && node[1][2] != null) { // index of [1][2]
						if (board[1][1] != EMPTY)
							node[1][2].setLeft(node[1][1]);
						if (board[0][1] != EMPTY)
							node[1][2].setLeftTop(node[0][1]);
						if (board[0][2] != EMPTY)
							node[1][2].setTop(node[0][2]);
						if (board[2][2] != EMPTY)
							node[1][2].setBottom(node[2][2]);
						if (board[2][1] != EMPTY)
							node[1][2].setLeftBottom(node[2][1]);
						
						/* Make default value */
						node[1][2].setRightTop(null);
						node[1][2].setRight(null);
						node[1][2].setRightBottom(null);
						
					}
				} // End of the second row
				/* Checking the third row of pieces */
				if (i == 2) {
					if (j == 0 && node[2][0] != null) { // index of [2][0]
						if (board[1][0] != EMPTY)
							node[2][0].setTop(node[1][0]);
						if (board[1][1] != EMPTY)
							node[2][0].setRightTop(node[1][1]);
						if (board[2][1] != EMPTY)
							node[2][0].setRight(node[2][1]);
						
						/* Make default value */
						node[2][0].setRightBottom(null);
						node[2][0].setBottom(null);
						node[2][0].setLeftBottom(null);
						node[2][0].setLeft(null);
						node[2][0].setLeftTop(null);
						
					} if (j == 1 && node[2][1] != null) { // Index of [2][1]
						if (board[2][0] != EMPTY)
							node[2][1].setLeft(node[2][0]);
						if (board[1][0] != EMPTY)
							node[2][1].setLeftTop(node[1][0]);
						if (board[1][1] != EMPTY)
							node[2][1].setTop(node[1][1]);
						if (board[1][2] != EMPTY)
							node[2][1].setRightTop(node[1][2]);
						if (board[2][2] != EMPTY)
							node[2][1].setRight(node[2][2]);
						
						/* Make default value */
						node[2][1].setRightBottom(null);
						node[2][1].setBottom(null);
						node[2][1].setLeftBottom(null);
						
					} if (j == 2 && node[2][2] != null) { // Index of [2][2]..the last cell
						if (board[2][1] != EMPTY)
							node[2][2].setLeft(node[2][1]);
						if (board[1][1] != EMPTY)
							node[2][2].setLeftTop(node[1][1]);
						if (board[1][2] != EMPTY)
							node[2][2].setTop(node[1][2]);
						
						/* Make default value */
						node[2][2].setRightTop(null);
						node[2][2].setRight(null);
						node[2][2].setRightBottom(null);
						node[2][2].setBottom(null);
						node[2][2].setLeftBottom(null);
						
					}
				} // End of the third row
			}
				
	}
	
	/* Method to return index of X */
	public int getIndexX() { return indexX; }
	
	/* Method to return index of Y */
	public int getIndexY() { return indexY; }
	
	public void placePiece(final double x, final double y) {
		/* The index of a XO to be displayed */
		indexX = (int) (x / cell_width);
		indexY = (int) (y / cell_height);
		
		/* If the position is empty th
		 * en place a piece and swap the players */
		if (board[indexX][indexY] == EMPTY && object.getLogic().getCurrentplayer() == XPIECE) {
			board[indexX][indexY] = XPIECE;
			rendersPiece[indexX][indexY] = new XOPiece(XPIECE);
			
			this.getChildren().add(rendersPiece[indexX][indexY]);
			rendersPiece[indexX][indexY].resize(cell_width, cell_height);
			rendersPiece[indexX][indexY].relocate(indexX * cell_width, indexY * cell_height);
			this.checkPiz(rendersPiece);
			this.object.getLogic().setCurrentplayer(OPIECE);
			
			/*if (rendersPiece[indexX][indexY].left() != null)
				System.out.println(rendersPiece[indexX][indexY].left().getType());
			if (rendersPiece[indexX][indexY].right() != null)
				System.out.println(rendersPiece[indexX][indexY].right().getType());
			if (rendersPiece[indexX][indexY].top() != null)
				System.out.println(rendersPiece[indexX][indexY].top().getType());
			if (rendersPiece[indexX][indexY].bottom() != null)
				System.out.println(rendersPiece[indexX][indexY].bottom().getType());
			if (rendersPiece[indexX][indexY].leftTop() != null)
				System.out.println("leftTop "+rendersPiece[indexX][indexY].leftTop().getType()+" index: " +indexX+" "+indexY);
			if (rendersPiece[indexX][indexY].rightTop() != null)
				System.out.println("rightTop " +rendersPiece[indexX][indexY].rightTop().getType()+" index: " +indexX+" "+indexY);
			if (rendersPiece[indexX][indexY].leftBottom() != null)
				System.out.println("leftBottom " +rendersPiece[indexX][indexY].leftBottom().getType()+" index: " +indexX+" "+indexY);
			if (rendersPiece[indexX][indexY].rightBottom() != null)
				System.out.println("rightBottom: "+rendersPiece[indexX][indexY].rightBottom().getType()+" index: " +indexX+" "+indexY);
			*/
		} else if (board[indexX][indexY] == EMPTY && object.getLogic().getCurrentplayer() == OPIECE) {
			board[indexX][indexY] = OPIECE;
			rendersPiece[indexX][indexY] = new XOPiece(OPIECE);

			this.getChildren().add(rendersPiece[indexX][indexY]);
			rendersPiece[indexX][indexY].resize(cell_width, cell_height);
			rendersPiece[indexX][indexY].relocate(indexX * cell_width, indexY * cell_height);
			this.checkPiz(rendersPiece);
			this.object.getLogic().setCurrentplayer(XPIECE);
			
			/*
			this.checkPiz1(rendersPiece);
			if (rendersPiece[indexX][indexY].left() != null)
				System.out.println(rendersPiece[indexX][indexY].left().getType());
			if (rendersPiece[indexX][indexY].right() != null)
				System.out.println(rendersPiece[indexX][indexY].right().getType());
			if (rendersPiece[indexX][indexY].top() != null)
				System.out.println(rendersPiece[indexX][indexY].top().getType());
			if (rendersPiece[indexX][indexY].bottom() != null)
				System.out.println(rendersPiece[indexX][indexY].bottom().getType());
			if (rendersPiece[indexX][indexY].leftTop() != null)
				System.out.println("lefttop "+rendersPiece[indexX][indexY].leftTop().getType()+" index: " +indexX+" "+indexY);
			if (rendersPiece[indexX][indexY].rightTop() != null)
				System.out.println("rightTop " +rendersPiece[indexX][indexY].rightTop().getType()+" index: " +indexX+" "+indexY);
			if (rendersPiece[indexX][indexY].leftBottom() != null)
				System.out.println("leftBottom " +rendersPiece[indexX][indexY].leftBottom().getType()+" index: " +indexX+" "+indexY);
			if (rendersPiece[indexX][indexY].rightBottom() != null)
				System.out.println("rightBottom: "+rendersPiece[indexX][indexY].rightBottom().getType()+" index: " +indexX+" "+indexY);
			*/
		}
	}
	
}
