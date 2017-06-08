package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/* The main java file to show the application */

public class Abalone extends Application implements Constants{
	
	private VBox vb;
	private HBox hb;
	private Board board;
	private Timer t;
	private Button bt1;
	private Label lb1, lb2;
	private Circle c;
	private Scene scene;
	
	public void init() {
		vb = new VBox();
		hb = new HBox();
		bt1 = new Button("RESET GAME");
		lb1 = new Label("Current Player:");
		lb2 = new Label("Black Piece(s): 14");
		c = new Circle();
		t = new Timer(this, "Play Time");
		board = new Board(this);
		
		c.setRadius(9.0);
		c.setFill(Color.BLACK);
		c.setStroke(Color.WHITE);
		c.setStrokeWidth(0);
		c.setTranslateY(3.0);
		
		board.setStyle("-fx-padding: 30;");
		
		lb1.setId("label_1");
		lb2.setId("label_2");
		bt1.setId("button");
		c.setId("c");
		hb.setId("hb");
		vb.setId("vb");
		
		/* Cannot update any data on the bar, 
		 * it will make unexpected changes to the board and cells */
		//t.start();
		
		hb.getChildren().addAll(bt1, lb1, c, lb2, t);
		
		vb.getChildren().addAll(hb, board);
		
	}
	
	public void start(Stage primaryStage) {
		scene = new Scene(vb, WINDOW_SIZE, WINDOW_SIZE);
		/* Get the css style sheet */
		scene.getStylesheets().add(Abalone.class.getResource("Abalone.css").toExternalForm());
		
		primaryStage.setTitle("Abalone");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void stop(){}
	
	public static void main(String[] args) { launch(args); }
	
	public Circle barCircle() { return c; }
	
	public Label barLabel_1() { return lb1; }
	
	public Label barLabel_2() { return lb2; }
	
	public Button barButton() { return bt1; }
	
	public Board getBoard() { return board; }
	
	public Timer getTimer() { return t; }
	
	public HBox hb() { return hb; }
	
}
