package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
//import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
//import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
//import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;


import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainController extends LoginController implements Initializable{

	Main main;

	Stage stage;
	Scene scene;
	
	int length = 1000, weight = 200;
	Integer viewtype = getIsEmployee(); //0 for management, 1 for employees to see very limited info
	//will need to figure out how we get either a 0 or 1 from the login screen
	
	//these variables will be used with scenes, look at how they are used here and at the the child Controllers, making a new stage/window is different
	protected static Integer empScene = -1; //used for the scenes when the employee is signed in
	protected static Integer managerScene = -1;//used for the scene when the manager is signed in
	
	/*For main application hub*/
	@FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane outerAnchorPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private StackPane centralStackPane;

    @FXML
    private Pane employeeInfoPane;
    
    @FXML
    private JFXTabPane tabPane;
    
    @FXML
    private AnchorPane employeesAnchorPane;
    
    @FXML
    private AnchorPane benefitsAnchorPane;

    @FXML
    private AnchorPane taxesAnchorPane;
    
    @FXML
    private AnchorPane grossAnchorPane;
    
    @FXML
    private AnchorPane netAnchorPane;
    
    @FXML
    private AnchorPane innerAnchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    
    protected Integer getEmpScene() {
    	return empScene;
    }
    
    protected void setEmpScene(Integer emp) {
    	empScene = emp;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        
	System.out.println("Printing viewtype: " + viewtype);
		 /* managerview*/
		if(viewtype == 0) {	
			try {
				/*code that applies to the drawer on the left side of the app*/
	            VBox drawerBox = FXMLLoader.load(getClass().getResource("drawerContents.fxml"));
	            drawer.setSidePane(drawerBox);
	            //drawer.setSidePane(sidePane);
	            for(Node node : drawerBox.getChildren()) {
	            	if (node.getAccessibleText() != null) {
	            		node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
	            			
	            			//ignore this part, I have the forms/controls directly on the drawer controller
	            			switch (node.getAccessibleText()) {
	            			
		            			case "Add_Button":
		            				//new window for adding employee/info
		            				break;
		            			case "Modify_Info_Button":
		            		    	
		            				System.out.println("printing from maincontroller");
		            				break;
		            			case "Modify_Benefits_Button":
		            				//new window for modifying employee benefits
		            				break;
		            			case "Modify_Pay_Button":
			            			//new window for modifying gross pay info
			            			break;
		            			case "Delete_Button":
		            				//new window for deleting employee/info
		            				break;
		            			case "Close_Button":
		            				empScene = -1;
		            				managerScene = -1;
		            				//System.exit(0);
	            			
	            			}	
	            		});
	            	}
	            }
	    
	        } catch (IOException ex) {
	            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
	        }
			
	        drawer.setOnDrawerClosed((event) -> drawer.toBack()); //On close bring to the back 
			drawer.setOnDrawerOpened((event) -> drawer.toFront());//On open bring to the front
	
	        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
			burgerTask.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) ->{
				
				burgerTask.setRate(burgerTask.getRate()*-1);
				burgerTask.play();
			
				if (drawer.isOpened()) {
					drawer.close();
				}
				else {
					drawer.open();
				}
			});
			
			//disables the employeeInfo, since it is above the treetable views in the fxml file
			employeeInfoPane.setVisible(false);
			employeeInfoPane.setDisable(true);
			//putting the tree table views in the front
			tabPane.toFront();
			
			/*Gets the 5 tree table views for the main app*/
			try {
				
				FXMLLoader loaderEmp = new FXMLLoader(getClass().getResource("employeeTreeTable.fxml"));
				FlowPane paneEmp = loaderEmp.load();
				employeesAnchorPane.getChildren().setAll(paneEmp);
				
				FXMLLoader loaderBen = new FXMLLoader(getClass().getResource("benefitsTreeTable.fxml"));
				FlowPane paneBen = loaderBen.load();
				benefitsAnchorPane.getChildren().setAll(paneBen);
				
				FXMLLoader loaderTax = new FXMLLoader(getClass().getResource("taxesTreeTable.fxml"));
				FlowPane paneTax = loaderTax.load();
				taxesAnchorPane.getChildren().setAll(paneTax);
				
				FXMLLoader loaderGross = new FXMLLoader(getClass().getResource("grossTreeTable.fxml"));
				FlowPane paneGross = loaderGross.load();
				grossAnchorPane.getChildren().setAll(paneGross);
				
				FXMLLoader loaderNet = new FXMLLoader(getClass().getResource("netTreeTable.fxml"));
				FlowPane paneNet = loaderNet.load();
				netAnchorPane.getChildren().setAll(paneNet);
				
			} catch (IOException e) {
	
				e.printStackTrace();
				/*This Logger code is like an expanded version of e.printStackTrace, more in-depth*/
	//			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
			}	
		}
		/*Code that will display the employeeview of the app, not going to include the tree table view, but simply
		 * a stackpane or some pane fof sorts with a different set of buttons on the drawer created*/
		else if (viewtype == 1) {
			
			/*
			 * Come back to this part!!!!
			 * 
			 * */
			
			//don't want anything to do with the treetables anymore, all we need is a pane for the user info
//			tabpaneHbox.setDisable(true);
			tabPane.setDisable(true);
			employeesAnchorPane.setDisable(true);
			benefitsAnchorPane.setDisable(true);
			taxesAnchorPane.setDisable(true);
			grossAnchorPane.setDisable(true);
			netAnchorPane.setDisable(true);
			
//			tabpaneHbox.setVisible(false);
			tabPane.setVisible(false);
			employeesAnchorPane.setVisible(false);
			benefitsAnchorPane.setVisible(false);
			taxesAnchorPane.setVisible(false);
			grossAnchorPane.setVisible(false);
			netAnchorPane.setVisible(false);
			
			try {
				
	            VBox drawerBox = FXMLLoader.load(getClass().getResource("drawerContents.fxml"));
	            drawer.setSidePane(drawerBox);
	            //drawer.setSidePane(sidePane);
	            for(Node node : drawerBox.getChildren()) {
	            	if (node.getAccessibleText() != null) {
	            		node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
	            			
//		            			FXMLLoader loadEmpPane = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
//								Pane empPane;
		            			
		            			//I ignored this part for the manager view, but I ran into issues with the Employee Version because I was trying something different.
		            			//The manager view features all opened a new stage/window, but for the employee, it is embedded in the app
		            			switch (node.getAccessibleText()) {
		            			
			            			case "Add_Button":
			            				//new scene for adding employee/info
			            				FXMLLoader loaderEmpPane0 = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
										Pane paneEmp0;
										try {
											
											setEmpScene(0);
											paneEmp0 = loaderEmpPane0.load();
											employeeInfoPane.getChildren().setAll(paneEmp0);
				            				employeeInfoPane.setVisible(true);
				            				employeeInfoPane.setDisable(false);
				            				employeeInfoPane.toFront();
				            	
				            				
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
			            				break;
			            				
			            			case "Modify_Info_Button":
			            				//new scene for searching for another employee
			            				FXMLLoader loaderEmpPane1 = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
										Pane paneEmp1;
										try {
											System.out.println("Beginning of maincontroller case for empsearch");
											setEmpScene(1);
											paneEmp1 = loaderEmpPane1.load();
											employeeInfoPane.getChildren().setAll(paneEmp1);
				            				employeeInfoPane.setVisible(true);
				            				employeeInfoPane.setDisable(false);
				            				employeeInfoPane.toFront();
											System.out.println("End of maincontroller case for empsearch");
				            				
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
			            				break;
			            				
			            			case "Modify_Benefits_Button":
			            				//new window for modifying employee benefits
			            				break;
			            				
			            			case "Modify_Pay_Button":
			            				//for a pay check
			            				FXMLLoader loaderEmpPane3 = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
										Pane paneEmp3;
										try {
											setEmpScene(3);
											paneEmp3 = loaderEmpPane3.load();
											employeeInfoPane.getChildren().setAll(paneEmp3);
				            				employeeInfoPane.setVisible(true);
				            				employeeInfoPane.setDisable(false);
				            				employeeInfoPane.toFront();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
			            				
			            				break;
			            				
			            			case "Delete_Button":
			            				//for viewing deduction info
			            				FXMLLoader loaderEmpPane4 = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
										Pane paneEmp4;
										try {
											setEmpScene(4);
											paneEmp4 = loaderEmpPane4.load();
											employeeInfoPane.getChildren().setAll(paneEmp4);
				            				employeeInfoPane.setVisible(true);
				            				employeeInfoPane.setDisable(false);
				            				employeeInfoPane.toFront();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
			            				
			            				break;
			            			case "Close_Button":
	//		            				FXMLLoader reloadApp = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
	//		            				Stage stage = new Stage();
			            				empScene = -1;
			            				managerScene = -1;
			            				
			            				break;
			            				
		            			}
	            			
		            	});
	            	}
	            }
	    
	        } catch (IOException ex) {
	            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
	        }
			
	        drawer.setOnDrawerClosed((event) -> drawer.toBack()); //On close bring to the back 
			drawer.setOnDrawerOpened((event) -> drawer.toFront());//On open bring to the front
	
	        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
			burgerTask.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) ->{
				
				burgerTask.setRate(burgerTask.getRate()*-1);
				burgerTask.play();
			
				if (drawer.isOpened()) {
					drawer.close();
				}
				else {
					drawer.open();
				}
			});
			
			//this loads the FXML for the employee View, so this is just a one-time thing to load 
			
			/*Gets the employeePane view for the main app*/
			//Make like a welcome page or something lol
			
			//not dynamic  
//			if(drawerController.empScene != -1) {
//		    
//				FXMLLoader loadEmpPane = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
//				Pane empPane;
//	            	
//	            try {
//					empPane = loadEmpPane.load();
//					employeeInfoPane.getChildren().setAll(empPane);
//		  			employeeInfoPane.setVisible(true);
//		  			employeeInfoPane.setDisable(false);
//					employeeInfoPane.toFront();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//            }
//			else if(drawerController.empScene == -1) {
//				FXMLLoader loadEmpPane = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
//				Pane empPane;
//	            try {
//					empPane = loadEmpPane.load();
//					employeeInfoPane.getChildren().setAll(empPane);
//					employeeInfoPane.setVisible(true);
//		  			employeeInfoPane.setDisable(false);
//		  			employeeInfoPane.toFront();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//			else
//				System.out.println("There may be an issue with the MainController");
			
			
			//will run once
			try {
				
				FXMLLoader loaderEmpPane = new FXMLLoader(getClass().getResource("EmployeePane.fxml"));
				Pane paneEmp = loaderEmpPane.load();
				employeeInfoPane.getChildren().setAll(paneEmp);
				employeeInfoPane.setVisible(true);
				employeeInfoPane.setDisable(false);
				employeeInfoPane.toFront();
			
				
			} catch (IOException ex) {
	
				ex.printStackTrace();
				/*This Logger code is like an expanded version of e.printStackTrace, more in-depth*/
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
			}	
			
			
		}
		else
			System.out.println("We may have an error in the main controller/login controller");
		
	}
	
	
	
}
