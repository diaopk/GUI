package Assignment_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
 
/**
 *
 * @author JavaFXtuts.com
 */
public class Test extends Application {
    
	public void init(){}
	
    @Override
    public void start(Stage primaryStage) {
         HBox root = new HBox();
         //Set space or padding using setPadding() method
         root.setPadding(new Insets(20));
         
         //assiging a class to the button
         Button button=new Button("my button");
         //Adding a class to the button
         button.getStyleClass().add("btn");
         
         //assiging a class to the button1
         Button button1 =new Button("Button1");
         //set id to the button. 
         button1.setId("btn1");
         
        
    root.getChildren().addAll(button,button1);
    Scene scene = new Scene(root, 300, 250);
    //To add a external css file we do as
    String  style= getClass().getResource("New.css").toExternalForm();
    //now add the external css file to the scene
    scene.getStylesheets().add(style);
    
        primaryStage.setTitle("javafxtuts.com");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void stop() {}
 
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
