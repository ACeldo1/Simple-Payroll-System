package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//added 
import java.sql.Connection; 

/*
 * The @FXML functions will all have 2 features. One to search for the info in the database and retrieve it,
 *  and then depending on the function, it will add, modify, or delete etc. For example, the add function will not be able to delete anybody
 *  as we previously had everything together. Now, it will only be able to search and modify
 * */
public class DrawerEmpController extends LoginController implements Initializable {

	/*javafx data members and variables that will be used for the 
	 * add, modify and delete functions in the JFXDrawer*/
	Stage primaryStage;
	
	//these i may use in the future, who knows? :D
	LoginController loginControl;
	JDBCConnector connection;
	Boolean connectionStatus;
	
	// Variables that will allow access to each of the classes for the buttons, may add more
	SearchEditInfo searchClass;
	GrossPayInfoForm grossform;
	AddSearchForm addsearchform;
	
	Scene scene;
			
	Integer viewtype = getIsEmployee(); //0 for management, 1 for employees to see very limited info
	//will need to figure out how we get either a 0 or 1 from the login screen
	int makemenuType;
	
	@FXML
    private VBox drawer;

    @FXML
    private ImageView drawerImage;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton modifyInfoButton;
    
    @FXML
    private JFXButton modifyBenefitsButton;
    
    @FXML
    private JFXButton modifyPayButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton closeButton;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Below are the methods that will act upon clicking the buttons in the drawer
	 * viewtype == 0 is for the manager, 1 is for the employee, and others are errors
	 * I'm thinking about maybe just having the drawer completely disables, but then this wouldn't be nice
	 * So the other option was making another drawer for the employees, and that will have other options like maybe
	 * buttons that will allow the employee to change their info, and the other stuff mentioned in class like printing
	 * a check amd such, we'll see...
	 * */
	
	//responsible for adding an employee, using yao's add2 file sent on 11/22
	@FXML
    void addForm(ActionEvent event) throws IOException {

		System.out.println("viewtype value: " + viewtype);
		if(viewtype == 0) { 
			addsearchform = new AddSearchForm();
	    	Stage stage = new Stage();
	    	
	    	try {
				addsearchform.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("run addForm");
		}
		else if (viewtype == 1) {
			
		}
		else
			System.out.println("Issue in addForm method");
	
	}
			
	//done using yao's search and edit
    @FXML
    void modifyInfoForm(ActionEvent event) {

    	System.out.println("viewtype value: " + viewtype);
    	if(viewtype == 0) {
	    	searchClass = new SearchEditInfo();
	    	Stage stage = new Stage();
	    	
	    	try {
				searchClass.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("run modifyInfoForm");
    	}
		else if (viewtype == 1) {
			
		}
		else
			System.out.println("Issue in modifyInfoForm method");
		
    	
	} //end of info form
    
    
    //still need to be modified, if anything is to be added, otherwise remove it
    @FXML
    void modifyBenefitsForm(ActionEvent event) {
    	
//	    if(viewtype == 0) {
//    		searchClass = new SearchEditInfo();
//	    	Stage stage = new Stage();
//	    	
//	    	try {
//				searchClass.start(stage);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("run modifyBenefitsForm");
//	    }
//		else if (viewtype == 1) {
//			
//		}
//		else
//			System.out.println("Issue in modifyBenefitMethod method");
//			
    }
    
    // done using my GrossPayInfoForm class, updates (or adds) to employee pay info based on whether the employee exists or not
    @FXML
    void modifyPayForm(ActionEvent event) {
   
    	System.out.println("viewtype value: " + viewtype);
    	if(viewtype == 0) {
	    	grossform = new GrossPayInfoForm();
	    	Stage stage = new Stage();
	    	
	    	try {
				grossform.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("run modifyPayForm");
    	}
		else if (viewtype == 1) {
			
		}
		else
			System.out.println("Issue in modifyPayForm method");
	
    }
    
    //will be responsible for deleting an employee, not sure if this still needs to be done
    @FXML
    void deleteForm(ActionEvent event) {

//      	if(viewtype == 0) {
//	    	searchClass = new SearchEditInfo();
//	    	Stage stage = new Stage();
//	    	
//	    	try {
//				searchClass.start(stage);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("deleteform run");
//      	}
//		else if (viewtype == 1) {
//			
//		}
//		else
//			System.out.println("Issue in deleteForm method");
	
    }

    @FXML
    void logoutSession(ActionEvent event) {
    	
    	//closes the stage in the main controller
    	//will basically rerun the login fxml file so that the user must login again
    	//not diffcult, will be one of the last things to implement
    	
    }
	
}
