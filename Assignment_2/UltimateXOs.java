package Assignment_2;

import javafx.application.Application;
import javafx.scene.Scene;
/*
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
*/
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UltimateXOs extends Application{
	
	private StackPane spMain;//, spTop;
	/*private VBox vb;
	private Label XPlayer, OPlayer, XPoint, OPoint, haha;
	*/
	private CustomControl cc;
	/*private Menu manuGame, manuHelp;
	private MenuItem miGame, miHelp;
	private ToggleGroup tp;
	*/
	public void init() {
		//vb = new VBox();
		spMain = new StackPane();
		/*spTop = new StackPane();
		XPlayer = new Label("X Piece");
		OPlayer = new Label("O Piece");
		XPoint = new Label("0");
		OPoint = new Label("0");
		haha = new Label(" : ");*/
		cc = new CustomControl();
		
		//spTop.getChildren().addAll(XPlayer, XPoint, haha, OPoint, OPlayer);
		spMain.getChildren().add(cc);
		//vb.getChildren().addAll(spMain);
		
	}
	
	public void start(Stage primaryStage) {	
		primaryStage.setTitle("Ultimate Tic-Tac-Toe");
		primaryStage.setScene(new Scene(spMain, 800, 600));
		primaryStage.show();
		
	}
	
	public void stop() {}
	
	public static void main(String[] args) { launch(args); }

}
