package Assignment_2;

/* Implementation of a custom control for JavaFx */

import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CustomControl extends Control{

	private XOUltimateBoard board;
	private final int EMPTY = 0;
	private final int NO = 1;
	
	public CustomControl() {
		/* 
		 * The first line generates a new CustomControlSkin object
		 * and links it with the CustomControl.
		 */
		this.setSkin(new CustomControlSkin(this));
		board = new XOUltimateBoard();
		this.getChildren().add(board);		
				
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				board.placePiece(event.getX(), event.getY());
				
				
				/*
				int count = 0;
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++) {
						board.getGameLogic().checkFull(board.getGameLogic().getBoardFull(i, j), board.getXOBoard()[i][j]);
						
						if (board.getGameLogic().getBoardFull(i, j) == true) { // If this board is full
							board.getGameLogic().checkSubBoard(i, j);
							count++;
						}
					}
				
				if (count == 9) // If all boards are full
					board.getGameLogic().sumPoint();
				if (board.getGameLogic().getPoint(1) > board.getGameLogic().getPoint(2))
					board.getGameLogic().setMainWinner(1);
				else
					board.getGameLogic().setMainWinner(2);
				*/
			}
		});
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board.getLogic().SubWinnerProperty(i, j).addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						// TODO Auto-generated method stub
						board.getLogic().checkWinner();
						
						if (board.getLogic().getMainWinner() != EMPTY)
							for (int a = 0; a < 3; a++)
								for (int b = 0; b < 3; b++) {
									board.getLogic().setPlayable(a, b, NO);
									board.getLogic().setBoardFull(a, b, true);
								}				
					}
					
				});
		
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE)
					board.reset();
			}
		});
		
	}
	
	@Override
	public void resize(double width, double height) {
		super.resize(width, height);
		board.resize(width, height);
		
	}
	
}
