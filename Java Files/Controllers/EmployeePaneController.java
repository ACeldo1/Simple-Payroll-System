package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRippler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

//I'm attempting to make it so that the the contents of the EmployeePaneController are dependent on the
// actions of the drawerController
public class EmployeePaneController extends MainController implements Initializable{

	//baseAnchor holds everything together, do not modify it
	//for nice stuff, employeePane should be modified for cool effects and stuff
	//displayPane is the only FXML feature changing with the button, as it contains the employee content
	
	/* MORE NOTES*/
	//This controller depends on others, MainController loads it into the app, as of now drawerController doesn't do anything for it.
	
    @FXML
    private AnchorPane baseAnchor;

    @FXML
    private Pane employeePane;

    @FXML
    private Pane displayPane;
    
    
    Integer empScene = getEmpScene(); //from drawercontroller class
    Pane parent;
    
    //class variables that represent the different modules features the employee will have
    EmployeeView empview;
    SearchAnotherEmployee empSearch;
    //feature3 f3;
    PayCheck paycheck;
    EmployeeDeductions deduct;
    //logout feature is the last button;

    //the initialize method waits for FXML elements to use, whereas in a class constructor, it doesn't wait
    //this method is used when the main hub of the application opens up
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	
		JFXRippler rippler = new JFXRippler(employeePane);
		
		rippler.setMaskType(JFXRippler.RipplerMask.FIT);
//		rippler.setRipplerFill(Paint.valueOf("#add8e6"));
		rippler.setRipplerFill(Paint.valueOf("#aaaaaa"));
		baseAnchor.getChildren().add(rippler);
		
		
		JFXRippler rippler2 = new JFXRippler(displayPane);
		
		rippler2.setMaskType(JFXRippler.RipplerMask.RECT);
		rippler2.setRipplerFill(Paint.valueOf("#add8e6"));
		employeePane.getChildren().add(rippler2);
		
		System.out.println("Emp View type: " + empScene);
		

		System.out.println("Printing size of displayPane: " + displayPane.getChildren().size());

		//		System.out.println("Printing stuff from empPaneController: " + employeePane.getChildren().size());
		
		//Welcome page maybe?
		// With our memeachu here as well lol
		
		
//		Pane welcome = new Pane();
    	//welcome class will just be the logo and a quick message at either the top or the bottom
    	//can have a new class here that will load in the welcome class, for now, I'll just have it locally, in this method
    	displayPane.toFront();
    	 
    	//creating the image object
//        InputStream stream;
//		try {
//			stream = new FileInputStream(getClass().getResource("static/memeachu.png").toString());
//			stream = new FileInputStream("/static/memeachu.png");
	        
//	        Image image = new Image(new FileInputStream("application/static/memeachu.png"));  
	        Image image = new Image("application/static/memeachu.png");  

			//Setting the image view 
	        ImageView imageView = new ImageView(image); 
	        
	        //Setting the position of the image 
	        imageView.setX(600); 
	        imageView.setY(400); 
	        
	        //setting the fit height and width of the image view 
	        imageView.setFitHeight(150); 
	        imageView.setFitWidth(200); 
	        
	        //Setting the preserve ratio of the image view 
	        imageView.setPreserveRatio(true);  
	        
//	        welcome.getChildren().addAll(imageView);
	        
//	        displayPane.getChildren().removeAll();
	        employeePane.getChildren().add(imageView);
	        
//		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
    	System.out.println("Welcome page run");
		
		
//		deduct = new EmployeeDeductions();
////		deduct.start();
//		displayPane.toFront();
//		try {
//			deduct.start(parent);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("deleteform/view deductions run");
//	
//		displayPane.getChildren().add(parent);
		
		//this is the first button in the drawer (addEmployee), but it will be labeled differently and have a different function/feature
				// that depends on whether the user is a manager or an employee; same case with the other buttons
		if(empScene == 0) {

			Pane parent0 = new Pane();
			empview = new EmployeeView();
			displayPane.toFront();
			try {
				empview.start(parent0, LoginController.userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("view paycheck run");
			
			displayPane.getChildren().removeAll();
			displayPane.getChildren().add(parent0);
	
			System.out.println("Feature 1 is run");
		}
		else if(empScene == 1) {
			
			System.out.println("Beginning of empScene == 1 else if condition");
			Pane parent1 = new Pane();
			empSearch = new SearchAnotherEmployee();
			displayPane.toFront();
			try {
				empSearch.start(parent1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("search for coworker");
			
			displayPane.getChildren().removeAll();
			displayPane.getChildren().add(parent1);

			System.out.println("Feature 2 is run");
				
	    }
		else if(empScene == 2) {
		    		
	//		parent = new Pane();
			//f3 = new Feature3();
	//		feature3.start(); //look at the EmployeeDeductions class for more clarification, same for the other cases below
	//		displayPane.toFront();
	//		try {
	//			//feature3.start(parent);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		System.out.println("Feature3 run");
	//	
	//		displayPane.getChildren().add(parent);
	//		
			System.out.println("Feature 3 is run");
		}
		else if(empScene == 3) {
			 
			Pane parent3 = new Pane();
			paycheck = new PayCheck();
			displayPane.toFront();
			try {
				paycheck.start(parent3, LoginController.userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("view paycheck run");
			
			displayPane.getChildren().removeAll();
			displayPane.getChildren().add(parent3);
			
			//f4 = new Feature4();
	//		feature4.start(); //look at the EmployeeDeductions class for more clarification, same for the other cases below
	//		displayPane.toFront();
	//		try {
	//			//feature4.start(parent);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		System.out.println("Feature4 run");
	//	
	//		displayPane.getChildren().add(parent);
	//		
			System.out.println("Feature 4 is run");
		}
		else if(empScene == 4) {
			
			Pane parent4 = new Pane();
			deduct = new EmployeeDeductions();
			displayPane.toFront();
			try {
				deduct.start(parent4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("deleteform/view deductions run");
		
			displayPane.getChildren().removeAll();
			displayPane.getChildren().add(parent4);
			
		}
	    else {
	    	
	    	System.out.println("If you see this text, there may be an issue with the EmployeePaneController");
	    	
	    }
    }
}

