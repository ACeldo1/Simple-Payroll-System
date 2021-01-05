package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	Main mainAccess;
	Scene scene;
	Alert alert;

	ArrayList<String> SearchResult;
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
	protected Integer managerId;
	protected String managerPwd;
	
	protected Integer empId;
	protected String empPwd;
	
	//0 for Manager, 1 for Employee
	protected static Integer isEmployee = -1;
	//true if there are any values in the arraylist, false otherwise
	Boolean validity;
	protected static int userId = -1;//(can be ) used in all of the controllers that extends this controller
	
	@FXML
    private JFXTextField userid;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXCheckBox rememberMeCheck;

    @FXML
    private JFXButton loginManagerButton;

    @FXML
    private JFXButton loginEmployeeButton;

    @FXML
    private JFXButton forgotButton;
    
    @FXML
	protected AnchorPane baseAnchor;
    
    @FXML
    void loginManagerAction(ActionEvent event) throws IOException {

    	EstablishConnection();
		
		try {
			Integer mid = Integer.parseInt(userid.getText().trim());
	    	String pwd = password.getText().trim();
	    	setManagerid(mid);
	    	setManagerPwd(pwd);
			
			ManagerSearch(mid, pwd);
			if(!SearchResult.isEmpty() && SearchResult != null && SearchResult.size() == 2) {
				if(mid.equals(Integer.parseInt(SearchResult.get(0))) && encryptThisString(pwd).equals(SearchResult.get(1))) { 
					
					userId = Integer.parseInt(SearchResult.get(0));
					setIsEmployee(0);
					
					Parent root = FXMLLoader.load(getClass().getResource("SceneVersion2.fxml"));			
					baseAnchor.getScene().setRoot(root);
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success!");
					alert.setHeaderText("You successfully logged in!");
					alert.setContentText("Viewing main application (Manager Version)");
					alert.showAndWait();
				
				}
				else {
					
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Error!");
					alert.setHeaderText("Incorrect manager credentials!");
					alert.setContentText("Please try again!");
					alert.showAndWait();
					
				}
			}	
			else {
				
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error!");
				alert.setHeaderText("I don't think this manager exists...");
				alert.setContentText("Please try again!");
				alert.showAndWait();
				
			}
			
		} catch(Exception e) {
			
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error!");
			alert.setContentText("Error in login form!");
			alert.showAndWait();
			e.printStackTrace();
		}
		
    }

    @FXML
    void loginEmployeeAction(ActionEvent event) throws IOException {

    	EstablishConnection();
		
		try {	
	    	Integer eid = Integer.parseInt(userid.getText().trim());
	    	String pwd = password.getText().trim();
	    	setEmployeeid(eid);
	    	setEmployeePwd(pwd);
			
			EmployeeSearch(eid, pwd);
			
			if(!SearchResult.isEmpty() && SearchResult != null && SearchResult.size() == 2) {
				if(eid.equals(Integer.parseInt(SearchResult.get(0))) && encryptThisString(pwd).equals(SearchResult.get(1))) { 
					
					setIsEmployee(1);
					userId = Integer.parseInt(SearchResult.get(0));
					
					Parent root = FXMLLoader.load(getClass().getResource("SceneVersion2.fxml"));			
					baseAnchor.getScene().setRoot(root);
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success!");
					alert.setHeaderText("You successfully logged in!");
					alert.setContentText("Viewing main application (Employee Version)");
					alert.showAndWait();
				
				}
				else {
					
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Error!");
					alert.setHeaderText("Incorrect employee credentials!");
					alert.setContentText("Please try again!");
					alert.showAndWait();
					
				}
			}	
			else {
				
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error!");
				alert.setHeaderText("Are you sure this Employee exists?");
				alert.setContentText("Please try again!");
				alert.showAndWait();
				
			}
			
		} catch(Exception e) {
			
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error!");
			alert.setContentText("Error in login form!");
			alert.showAndWait();
			e.printStackTrace();
		}
		
    }
    
    protected void setValidity(Boolean validity) {
    	this.validity = validity;
    }
    
    protected Boolean getValidity() {
    	return validity;
    }
    
    public void ManagerSearch(Integer mid, String pwd) {

    	SearchResult = new ArrayList<>();
		String searchQuery = "SELECT * FROM Managers WHERE managerId = ? AND pwd = ? LIMIT 1";
		connection.SetQuery(searchQuery);
		
		try(PreparedStatement searchStatement = connection.GetConnection().prepareStatement(searchQuery)){
			
			connection.GetConnection().setAutoCommit(false);	
			searchStatement.setInt(1, mid);
			searchStatement.setString(2, encryptThisString(pwd));
			connection.SetPreparedStatement(searchStatement);
			connection.RunPreparedQuery(0);
			connection.GetConnection().commit();	
			
			System.out.println("Testing from within ManagerSearch method...");
			while (connection.GetResultSet().next()) {
		
					SearchResult.add(connection.GetResultSet().getString("managerId"));
					SearchResult.add(connection.GetResultSet().getString("pwd"));
		
			}
			
		} catch(SQLException errors) {
			
			errors.printStackTrace();
//			if(connection.GetConnection() != null) {
//				
//				 try {
//			         
//					 System.err.print("Transaction is being rolled back");
//			         connection.GetConnection().rollback();
//			        
//				 } catch (SQLException excep) {
//			      
//					 excep.printStackTrace();
//					 JDBCTutorialUtilities.printSQLException(excep);
//			        
//				 }
//				
//			}
		}
		
	}
    
    public void EmployeeSearch(Integer eid, String pwd) {
    	
    	SearchResult = new ArrayList<>();
		String searchQuery = "SELECT * FROM Employees WHERE empId = ? AND pwd = ? LIMIT 1";
		connection.SetQuery(searchQuery);
		
		try(PreparedStatement searchStatement = connection.GetConnection().prepareStatement(searchQuery)){
			
			connection.GetConnection().setAutoCommit(false);	
			searchStatement.setInt(1, eid);
			searchStatement.setString(2, encryptThisString(pwd));
			connection.SetPreparedStatement(searchStatement);
			connection.RunPreparedQuery(0);
			connection.GetConnection().commit();	
			
			System.out.println("Testing from within EmployeeSearch method...");
			while (connection.GetResultSet().next()) {
		
					SearchResult.add(connection.GetResultSet().getString("empId"));
					SearchResult.add(connection.GetResultSet().getString("pwd"));
		
			}
			
		} catch(SQLException errors) {
			
			errors.printStackTrace();
//			if(connection.GetConnection() != null) {
//				
//				 try {
//			         
//					 System.err.print("Transaction is being rolled back");
//			         connection.GetConnection().rollback();
//			        
//				 } catch (SQLException excep) {
//			      
//					 excep.printStackTrace();
//					 JDBCTutorialUtilities.printSQLException(excep);
//			        
//				 }
//				
//			}
		}
		
	}
    
    public void SetConnectionStatus(Boolean connStat) {
		this.connectionStatus = connStat;
	}
	
	public Boolean GetConnectionStatus() {
		return connectionStatus;
	}
	
	
	//established connection through the JDBCConnector class (using the Datasource import)
	public void EstablishConnection() {
		
		try {
			
			this.connection = new JDBCConnector();
			SetConnectionStatus(true);
			
		} catch(SQLException | NamingException sqlError) {
			
			sqlError.printStackTrace();
			SetConnectionStatus(false);

		}
		
	}
    
    public String encryptThisString(String input) 
    { 
        try { 
            // getInstance() method is called with algorithm SHA-1 
            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
  
            // digest() method is called 
            // to calculate message digest of the input string 
            // returned as array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            // Add preceding 0s to make it 32 bit 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            // return the HashText 
            return hashtext; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
    
    //getter and setter methods
    
    protected void setManagerid(Integer manager) {
    	this.managerId = manager;
    }
    
    protected Integer getManagerid() {
    	return managerId;
    }
    
    protected void setManagerPwd(String manager) {
    	this.managerPwd = manager;
    }
    
    protected String getManagerPwd() {
    	return managerPwd;
    }
    
    protected void setEmployeeid(Integer emp) {
    	this.empId = emp;
    }
    
    protected Integer getEmployeeid() {
    	return empId;
    }
    
    protected void setEmployeePwd(String pwd) {
    	this.empPwd = pwd;
    }
    
    protected String getEmployeePwd() {
    	return empPwd;
    }
    
    protected void setIsEmployee(Integer num) {
    	isEmployee = num; //is static, does not need this operator
    }
    
    protected Integer getIsEmployee() {
    	return isEmployee;
    }
    
    @FXML
    void forgotPasswordAction(ActionEvent event) { //will be implemented in a future release :)
    	
    	//will involve the use of a script, most likely will implement using Javascript
    	
    }

    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
