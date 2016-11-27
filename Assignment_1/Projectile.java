package Assignment_1;
/*
 * Assignment
 * student name: Junli Liang
 * student num: 2902322
 * 
 * This application allows the user to calculate range, max height and flight time for a projectile 
 * following the entering of mass (currently not used), angle of launch, and initial speed.
 * There is error checking to ensure that suitable values are entered. 
 */

/* Imports */
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.text.DecimalFormat;
import javafx.stage.Stage;

/* class definition */
public class Projectile extends Application {

	/* init method */
	public void init() {

		/* Layout */
		gp = new GridPane();
		hb_initial_speed = new HBox();
		
		/* Projectile property - ComboBox */
		projectile_type_label = new Label("Projectile Type");
		projectile_type_combobox = new ComboBox<String>();
		
		projectile_type_combobox.getItems().addAll("Adult Human", "Piano");
		
		// Set default value for combobox
		projectile_type_combobox.setValue("Adult Human");
		
		/* Projectile property - Mass */
		mass_label = new Label("Mass (kgs)");
		mass_textField = new TextField();
		mass_textField.setText("80");
		mass_textField.setEditable(false);
		
		/* Projectile property - Angle */
		angle_label = new Label("Angle (degrees)");
		angle_textField = new TextField();
		angle_slider = new Slider(0, 90, 45);
		
		// Set properties for angle_slider
		angle_slider.setShowTickLabels(true);
		angle_slider.setShowTickMarks(true);
		angle_slider.setMajorTickUnit(15);
		
		// Set default value for angle_textField
		angle_textField.setText("45.0");
		
		/* Initialize Speed ToggleGroup */
		initial_speed_label = new Label("Inital Speed(m/s)");
		initial_speed_textField = new TextField();
		initial_speed_slow = new RadioButton("slow");
		initial_speed_medium = new RadioButton("medium");
		initial_speed_fast = new RadioButton("fast");
		initial_speed_toggleGroup = new ToggleGroup();
		
		initial_speed_textField.setEditable(false);
		initial_speed_slow.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_medium.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_fast.setToggleGroup(initial_speed_toggleGroup);
		
		//use the .setUserData command of the radio button to store speeds
		initial_speed_slow.setUserData(10);
		initial_speed_medium.setUserData(50);
		initial_speed_fast.setUserData(100);
		
		// Set default value for initial speed
		initial_speed_medium.setSelected(true);
		initial_speed_textField.setText("50.0");
		
		/* Projectile property - Range, Height and Time */
		range_label = new Label("Range(m)");
		height_label = new Label("Max Height(m)");
		time_label = new Label("Time(s)");
		range_textField = new TextField();
		height_textField = new TextField();
		time_textField = new TextField();
		
		// Prevent the following TextFields from being editable: angle,intial speed range, height, and time
		range_textField.setEditable(false);
		height_textField.setEditable(false);
		time_textField.setEditable(false);
		
		/* projectile property - two action buttons */
		fire_button = new Button("Fire");
		erase_button = new Button("Erase");
		
		fire_button.getStyleClass().add("btn");
		erase_button.getStyleClass().add("btn");
		
		// Layout controls as per the diagram, feel free to improve the UI. 
		// How many rows and columns do you want - work this out on paper first 
		// My version has 7 rows, you can look at the JavaFX API to see how to get controls to span more than one column

		// Method call (not declaration!)  to initialize the controls based on the projectile type.
		hb_initial_speed.getChildren().addAll(initial_speed_slow, initial_speed_medium, initial_speed_fast);
		
		gp.addRow(0, projectile_type_label, projectile_type_combobox);
		gp.addRow(1, mass_label, mass_textField);
		gp.addRow(2, angle_label, angle_textField, angle_slider);
		gp.addRow(3, initial_speed_label,initial_speed_textField, hb_initial_speed);
		gp.addRow(4, range_label, range_textField);
		gp.addRow(5, height_label, height_textField);
		gp.addRow(6, time_label, time_textField);
		gp.addRow(7, fire_button, erase_button);
		
		gp.setMinSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		/* Set a scene */
		scene = new Scene(gp, 540,300);
		scene.getStylesheets().add(Projectile.class.getResource("projectileCss.css").toExternalForm());		
		
		/* The list of Listener and handler goes here */
		/* Listener for projectile_type_combobox */
		this.projectile_type_combobox.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue){
				/* If one of projectile_type_combobox is selected then */
				if (projectile_type_combobox.getSelectionModel().isSelected(0)) {
					// Initialize the values
					mass_textField.setText("80");
					angle_slider.setMax(90.0);
					angle_slider.setValue(45);
					angle_slider.setMajorTickUnit(15);
					initial_speed_fast.setSelected(true);
					
					// clear the result fields
					range_textField.clear();
					height_textField.clear();
					time_textField.clear();
					
				} else {
					// Initialize the values
					mass_textField.setText("400");
					angle_slider.setMax(40);
					angle_slider.setValue(20);
					angle_slider.setMajorTickUnit(10);
					initial_speed_slow.setSelected(true);
					
					// clear the result fields
					range_textField.clear();
					height_textField.clear();
					time_textField.clear();
					
				}
			}
		});
		
		/*  Listener for angle Slider to set angle TextTield and the angle variable */ 
		angle_slider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue){
				angle_textField.setText(String.format("%.1f", newValue));
			
				// Make the dynamic calculation when at least one calculation has been processed
				if (!range_textField.getText().isEmpty() &&
						!height_textField.getText().isEmpty() &&
						!time_textField.getText().isEmpty())
					fire();
				
			}
		});
		
		/* Listener for angle TextField to set angle Slider and angle variable */
		angle_textField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue){
				//df = new DecimalFormat(newValue);
				/*
				 * USER INPUT LIMITATION
				 * If angle_textField's text is Empty then set angle_slider's value to the oldValue
				 * elseif (Double)angle_textField's value is greater than 90.0 setText("90.0") to angle_textField
				 * else set the newValue to angle_slider
				 */
				if (angle_textField.getText().isEmpty())
					angle_slider.setValue(Double.parseDouble(oldValue));
				
				else {
					
					if (projectile_type_combobox.getSelectionModel().isSelected(0)) {
					
						if (Double.parseDouble(angle_textField.getText()) > 90.0) {
							angle_textField.setText("90.0");
							angle_slider.setValue(Double.parseDouble(oldValue));
						
						} else
							angle_slider.setValue(Double.parseDouble(newValue));
					} else {
						
						if (Double.parseDouble(angle_textField.getText()) > 40.0) {
							angle_textField.setText("40.0");
							angle_slider.setValue(Double.parseDouble(oldValue));
						
						} else
							angle_slider.setValue(Double.parseDouble(newValue));
					
					}
				}
			}
		});

		/* Listener for inital_speed ToggleGroup to set initital_speed TextField */
		this.initial_speed_toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {
				initial_speed_textField.setText(String.format("%d", new_toggle.getUserData()));
				
				// Make the dynamic calculation when at least one calculation has been processed
				if (!range_textField.getText().isEmpty() &&
						!height_textField.getText().isEmpty() &&
						!time_textField.getText().isEmpty())
					fire();
				
			}
		});

		/* Listener to call the fire() method when the fire button is pressed */
		fire_button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				//if (fire_button.isPressed())
					fire();
			}
		});
		
		/* Listener to initialize control values if the erase button is pressed */
		erase_button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				initializeControlValues();
			}
		});

	}

	/* Overridden start method */
	public void start(Stage primaryStage) {
		// set a title on the window, set a scene, size, and show the window
		primaryStage.setTitle("Projectile");
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}

	/* Overridden stop method add functionality to this if you wish. */
	public void stop() {

	}

	/* Method to harvest values from controls, perform calculation and display the results */
	private void fire(){
		/* 
		 * Capture the values from the text fields outputting number errors where relevant 
		 * If any textField of textField is not filled in then
		 * invoke method initializeControlValues()
		 */ 
		if (mass_textField.getText().isEmpty() ||
				angle_textField.getText().isEmpty() ||
				initial_speed_textField.getText().isEmpty())
			initializeControlValues();
			
		else {
			/* don't forget to convert your angle input to radians for use with Math.sin() */
			/* I am not sure which three of them is correct for assignment to variable from textField.
			 * mass = (double) mass_textField.getUserData();
			 * angle = (double) angle_textField.getUserData();
			 * initial_speed = (double) initial_speed_textField.getUserData();
			 */
			double radians;
			
			//mass = Double.parseDouble(mass_textField.getText());
			angle = Double.parseDouble(angle_textField.getText());
			initial_speed = Double.parseDouble(initial_speed_textField.getText());
			radians = Math.toRadians(angle);
			
			// calculate the range of the projectile
			range = initial_speed * initial_speed * Math.sin(2 * radians) / gravitational_accelleration;
	
			// calculate the flight time of the projectile
			time = 2 * initial_speed * Math.sin(radians) / gravitational_accelleration;
	
			// calculate the max height of the projectile
			height = initial_speed * initial_speed * Math.sin(radians) * Math.sin(radians) / 2 * gravitational_accelleration;
	
			// display the results in the relevant TextFields
			range_textField.setText(String.format("%.2f", range));
			height_textField.setText(String.format("%.2f", height));
			time_textField.setText(String.format("%.2f", time));
		}
	}

	/* Method to initialize the controls based on the selection of the Projectile type */ 
	private void initializeControlValues(){
		/* 
		 * (case 1)If no textField is filled in
		 * then do nothing
		 * (case 2)elseif any textField of mass_textField, angle_textField or initial_speed_textField is filled in
		 * but the calculation has not been processed then
		 * clear the entered data from above textFields
		 * (case 3)else(calculation has been processed)
		 * return default values to each textField with predefined values
		 */
		
		/* case 1 */
		if (mass_textField.getText().isEmpty() &&
				angle_textField.getText().isEmpty() &&
				initial_speed_textField.getText().isEmpty())
			// Do nothing
			;
		
		/* case 2 */
		else if ((!mass_textField.getText().isEmpty() ||
				!angle_textField.getText().isEmpty() ||
				!initial_speed_textField.getText().isEmpty()) && 
				(range_textField.getText().isEmpty() ||
				height_textField.getText().isEmpty() ||
				time_textField.getText().isEmpty())) {
			// clear the data
			mass_textField.clear();
			angle_textField.clear();
			initial_speed_textField.clear();
			//this.initial_speed_toggleGroup.getSelectedToggle().setSelected(false);
			
		}
		
		/* case 3 */
		else {
			//if the projectile type is Adult Human then 
			if (projectile_type_combobox.getSelectionModel().isSelected(0)){
				//initialize the mass to 80kg
				mass_textField.setText("80");
		
				//Set slider scale 0 to 90, set slider value to 45 and ticks to 15 units
				angle_slider.setMin(0);
				angle_slider.setMax(90);
				angle_slider.setMajorTickUnit(15);
				angle_slider.setValue(45);
				angle_textField.setText("45.0");
		
				range_textField.clear();
				height_textField.clear();
				time_textField.clear();
				
				// initialize the initial speed to fast
				this.initial_speed_fast.setSelected(true);
				//initial_speed_toggleGroup.getToggles().set(2, initial_speed_fast);
				//this.initial_speed_textField.setText((String) this.initial_speed_fast.getUserData());
				
			}
			
			else { 
				// Initialize the mass to 400kg
				mass_textField.setText("400");
			
				// Set slider scale 0 to 40, set slider value to 20 and ticks to 10 units
				angle_slider.setMin(0);
				angle_slider.setMax(40);
				angle_slider.setMajorTickUnit(10);
				angle_slider.setValue(20);
				angle_textField.setText("20.0");
			
				// Initialize the initial speed to slow
				this.initial_speed_slow.setSelected(true);
				//this.initial_speed_textField.setText((String) this.initial_speed_slow.getUserData());
			}
			// display ticks etc
			
	
			// clear the results fields and variables
			range_textField.clear();
			height_textField.clear();
			time_textField.clear();
			
			
		}
	}
	
	/* Entry point to our program */
	public static void main(String[] args) {
		launch(args);
	}

	/* 
	 * The following variables SHOULD be initialized where appropriate as declaring and 
	 * initializing separately is very verbose. 
	 */
	// Scene
	private Scene scene;
	
	//Layout
	private GridPane gp;
	private HBox hb_initial_speed;
	
	//Projectile Type
	private Label projectile_type_label;
	private ComboBox<String> projectile_type_combobox;

	// Mass
	private Label mass_label;
	private TextField mass_textField ;
	//private double mass;

	//Angle
	private Label angle_label ;
	private Slider angle_slider;
	private TextField angle_textField ;
	private double angle;
	//Formating the values in the duration box
	DecimalFormat df ;

	//Initial Speed 
	private Label initial_speed_label ;
	private ToggleGroup initial_speed_toggleGroup ;
	private RadioButton initial_speed_slow;
	private RadioButton initial_speed_medium;
	private RadioButton initial_speed_fast;
	private TextField initial_speed_textField;
	private double initial_speed;

	//Range
	private Label range_label;
	private TextField range_textField;
	private double range;

	//Height
	private Label height_label;
	private TextField height_textField;
	private double height;

	//Time
	private Label time_label;
	private TextField time_textField ;
	private double time; 

	//Gravity 
	private static final double gravitational_accelleration = 9.81; // m/s/s

	//Calculate
	private Button fire_button;
	private Button erase_button;
	
}
