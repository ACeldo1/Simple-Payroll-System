package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//added 
import java.sql.Connection; 

/*
 * The @FXML functions will all have 2 features. One to search for the info in the database and retrieve it,
 *  and then depending on the function, it will add, modify, or delete etc. For example, the add function will not be able to delete anybody
 *  as we previously had everything together. Now, it will only be able to search and modify
 * */
public class drawerController extends LoginController implements Initializable {
	
	//note to self: when modifying this class in the future, only include options that will open new windows/stages, not scenes
	//for making new scenes in the hub of the application, create those in the MainController
	
	
	//another note: may have to change this to extending the main controller
	
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
    
    /*javafx data members and variables that will be used for the 
	 * add, modify and delete functions in the JFXDrawer*/
	Stage primaryStage;
	
	//these i may use in the future, who knows? :D
	LoginController loginControl;
	JDBCConnector connection;
	Boolean connectionStatus;
	
	// Variables that will allow access to each of the classes for the buttons, may add more, for managers
	SearchEditInfo searchClass;
	GrossPayInfoForm grossform;
	AddSearchForm addsearchform;
	EditBenefits editbenefits;
//	DeleteEmp deleteemp;
	
	// Variables that will allow access to each of the classes for the buttons, will add more, for employees
	EmployeeDeductions deductions;
	
	//only used for the logout, other instances of Scene should be local
	Scene scene = super.scene;
			
	Integer viewtype = getIsEmployee(); //0 for management, 1 for employees to see very limited info
	//will need to figure out how we get either a 0 or 1 from the login screen
	int makemenuType;

	protected static Integer empScene = -1; //will be used in the child EmployeePaneController to indicate which form/button action was pressed
	//top button on drawer = 0, bottom = 5
    
    protected Integer getEmpScene() {
    	return empScene;
    }
    
    protected void setEmpScene(Integer emp) {
    	empScene = emp;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		/*It doesn't need to include six button, we could just disable some, or add more depending on the total features we have available*/
		if(viewtype == 1) {
			addButton.setText("View Personal Info");
			modifyInfoButton.setText("Search for Employee");
			modifyBenefitsButton.setText("Another Feature");
			modifyPayButton.setText("View/Print Check");
			deleteButton.setText("View Deductions");
			closeButton.setText("Logout");			
		}	
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
			
			setEmpScene(0);
			System.out.println("empScene value: " + getEmpScene());
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
			
			setEmpScene(1);
			System.out.println("empScene value: " + getEmpScene());
		}
		else
			System.out.println("Issue in modifyInfoForm method");
		
    	
	} //end of info form
    
    
    //still need to be modified, if anything is to be added, otherwise remove it
    @FXML
    void modifyBenefitsForm(ActionEvent event) {
    	
    	System.out.println("viewtype value: " + viewtype);

	    if(viewtype == 0) {
    		editbenefits = new EditBenefits();
	    	Stage stage = new Stage();
	    	
	    	try {
				editbenefits.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("run modifyBenefitsForm");
	    }
	    
	    else if (viewtype == 1) {
			setEmpScene(2);
			System.out.println("empScene value: " + getEmpScene());
		}
		else
			System.out.println("Issue in modifyBenefitsForm method");
//		else if (viewtype == 1) {
//			setEmpScene(2);
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
			setEmpScene(3);
			System.out.println("empScene value: " + getEmpScene());
		}
		else
			System.out.println("Issue in modifyPayForm method");
	
    }
    
    //will be responsible for deleting an employee, not sure if this still needs to be done
    @FXML
    void deleteForm(ActionEvent event) {

      	if(viewtype == 0) {
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
      	}
		else if (viewtype == 1) {
			
			setEmpScene(4);
			System.out.println("View Deductions entered");
			System.out.println("empScene value: " + getEmpScene());
	
		}
		else
			System.out.println("Issue in deleteForm method");
	
    }

//    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
//    	 // you just create new control, all fxml tricks are encapsulated
//    	 InnerFxmlControl root = new InnerFxmlControl();
//    	 // and you can access all its' methods and fields including matched by @FXML tag:
//    	 root.cb.getItems().add("new item");
//    	 Scene cc = buttonStatusText.getScene();
//    	 cc.setRoot(root);
//    	}
    
    @FXML
    void logoutSession(ActionEvent event) {
    	
    	//closes the stage in the main controller
    	//will basically rerun the login fxml file so that the user must login again
    	//not diffcult, will be one of the last things to implement
    	
    	LoginController.isEmployee = -1;
    	LoginController.userId = -1;
	
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));			
			Scene newSession = new Scene(root);
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			
    		stage.setTitle("Login");
    		stage.setScene(newSession);
    		stage.show();
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
}
