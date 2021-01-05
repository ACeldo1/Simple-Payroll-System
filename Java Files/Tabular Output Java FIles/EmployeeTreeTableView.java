package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.naming.NamingException;

import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.NetTreeTableView.NetTreeTable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class EmployeeTreeTableView implements Initializable{

	Employees empInfo;
	
	Map <Integer, ArrayList<String>> empMap = new HashMap<Integer, ArrayList<String>>();
	
	ArrayList<String> SearchResult = new ArrayList<>();
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
    @FXML
	private FlowPane employeePane;

    @FXML
	private JFXTreeTableView<EmployeeTreeTable> employeeTreeView;
       
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		JFXTreeTableColumn<EmployeeTreeTable,String> userIDCol = new JFXTreeTableColumn<>("User ID");
		userIDCol.setPrefWidth(150);
		userIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().userId;
			}
		});

		JFXTreeTableColumn<EmployeeTreeTable,String> fnameCol = new JFXTreeTableColumn<>("First Name");
		fnameCol.setPrefWidth(150);
		fnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().firstName;
			}
		});
	
		JFXTreeTableColumn<EmployeeTreeTable,String> lnameCol = new JFXTreeTableColumn<>("Last Name");
		lnameCol.setPrefWidth(150);
		lnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().lastName;
			}
		});
		
		JFXTreeTableColumn<EmployeeTreeTable,String> bdayCol = new JFXTreeTableColumn<>("Birth Date");
		bdayCol.setPrefWidth(150);
		bdayCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().birthdate;
			}
		});
		
		JFXTreeTableColumn<EmployeeTreeTable,String> addressCol = new JFXTreeTableColumn<>("Address");
		addressCol.setPrefWidth(150);
		addressCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().address;
			}
		});
//		JFXTreeTableColumn<EmployeeTreeTable,String> passwordCol = new JFXTreeTableColumn<>("Password");
//		passwordCol.setPrefWidth(150);
//		passwordCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
//			@Override
//			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
//				return param.getValue().getValue().password;
//			}
//		});
		JFXTreeTableColumn<EmployeeTreeTable,String> emailCol = new JFXTreeTableColumn<>("Email");
		emailCol.setPrefWidth(150);
		emailCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().email;
			}
		});
		
		JFXTreeTableColumn<EmployeeTreeTable,String> contactCol = new JFXTreeTableColumn<>("Contact Info");
		contactCol.setPrefWidth(150);
		contactCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().contact;
			}
		});
	
		JFXTreeTableColumn<EmployeeTreeTable,String> emergencyContactCol = new JFXTreeTableColumn<>("Emergency Contact");
		emergencyContactCol.setPrefWidth(150);
		emergencyContactCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().emergencyContact;
			}
		});
		
		JFXTreeTableColumn<EmployeeTreeTable,String> benefitsCodeCol = new JFXTreeTableColumn<>("Benefits Code");
		benefitsCodeCol.setPrefWidth(150);
		benefitsCodeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().benefitsCode;
			}
		});
		
		JFXTreeTableColumn<EmployeeTreeTable,String> maritalStatusCol = new JFXTreeTableColumn<>("Marital Status");
		maritalStatusCol.setPrefWidth(150);
		maritalStatusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().marital_status;
			}
		});
		
		JFXTreeTableColumn<EmployeeTreeTable,String> workStatusCol = new JFXTreeTableColumn<>("Work Status");
		workStatusCol.setPrefWidth(150);
		workStatusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTreeTable, String> param){
				return param.getValue().getValue().working_status;
			}
		});
	
		ObservableList<EmployeeTreeTable> employees = FXCollections.observableArrayList();
		
		//establishing connection with database
		EstablishConnection();
		
		//number of rows in Employees Table
		Integer rowCount = -1;
		rowCount = getTableRowCount();
		
		//getting the records from the table into a resultset ready to be accessed
		EmployeesRecord();
		
		//when modifying this in the future, use a map or set, its much easier,
		//what i used here is a quick, dirty solution
		try {

			for (Entry<Integer, ArrayList<String>> ee : empMap.entrySet()) {
			    
				Integer key = ee.getKey();
			    ArrayList<String> values = ee.getValue();
			    // TODO: Do something.
						
			    employees.add(new EmployeeTreeTable(key, values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9)));
						    
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final TreeItem<EmployeeTreeTable> root = new RecursiveTreeItem<EmployeeTreeTable>(employees, RecursiveTreeObject::getChildren);
		employeeTreeView.getColumns().setAll(userIDCol, fnameCol, lnameCol, bdayCol, addressCol/*passwordCol*/, emailCol, contactCol, emergencyContactCol, benefitsCodeCol, maritalStatusCol, workStatusCol);
		employeeTreeView.setRoot(root);
		employeeTreeView.setShowRoot(false);	
	
		
		try {
			connection.EndAllConnections();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class EmployeeTreeTable extends RecursiveTreeObject<EmployeeTreeTable>{
		
		StringProperty userId;
		StringProperty firstName;
		StringProperty lastName;
		StringProperty birthdate;
		StringProperty address;
//		StringProperty password;
		StringProperty email;
		StringProperty contact;
		StringProperty emergencyContact;
		StringProperty benefitsCode;
		StringProperty marital_status;
		StringProperty working_status;
		
		public EmployeeTreeTable(Integer userid, String firstName, String lastName, String birthdate, String address, /*String password,*/ String email, String contact, String emergencyContact, String code, String status, String work) {
			
			this.userId = new SimpleStringProperty(userid.toString());
			this.firstName = new SimpleStringProperty(firstName);
			this.lastName = new SimpleStringProperty(lastName);
			this.birthdate = new SimpleStringProperty(birthdate);
			this.address = new SimpleStringProperty(address);
//			this.password = new SimpleStringProperty(password);
			this.email = new SimpleStringProperty(email);
			this.contact = new SimpleStringProperty(contact);
			this.emergencyContact = new SimpleStringProperty(emergencyContact);
			this.benefitsCode = new SimpleStringProperty(code);
			this.marital_status = new SimpleStringProperty(status);
			this.working_status = new SimpleStringProperty(work);
		}
		
	}

	//uses a regular statement, no parameters here
	public void EmployeesRecord() {
		
		String searchQuery = "SELECT empid, fname, lname, birth_date, address, email, phone_num, emergency_num, benefit_code, marital_status, working_status FROM Employees";
		connection.SetQuery(searchQuery);
		
		try(Statement searchStatement = connection.GetConnection().createStatement()){
			
			connection.GetConnection().setAutoCommit(false);	
			connection.SetStatement(searchStatement);
			connection.RunQuery();
			connection.GetConnection().commit();
			
			while (connection.GetResultSet().next()) {
							
				String fname = connection.GetResultSet().getString("fname");
				String lname = connection.GetResultSet().getString("lname");
				String date = connection.GetResultSet().getString("birth_date");
				String address = connection.GetResultSet().getString("address");
				String email = connection.GetResultSet().getString("email");
				String phone = connection.GetResultSet().getString("phone_num");
				String emergency = connection.GetResultSet().getString("emergency_num");
				String code = connection.GetResultSet().getString("benefit_code");
				String status = connection.GetResultSet().getString("marital_status");
				String work = connection.GetResultSet().getString("working_status");
						
				empMap.put(connection.GetResultSet().getInt("empId"), new ArrayList<String>(Arrays.asList(fname, lname, date, address, email, phone, emergency, code, status, work)));
						
				//SearchResult.clear();
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
	
	//pay attention to commented sections of the code for code reusability when optimizing in the future
//	protected int getTableRowCount(String tableName) {
	protected int getTableRowCount() {
		int count = -1;

//		String query = "SELECT COUNT(*) AS rowcount FROM " + tableName;
		String query = "SELECT COUNT(*) AS rowcount FROM Employees";
		connection.SetQuery(query);
		
		try (Statement rowStmt = connection.GetConnection().createStatement()) {

			connection.GetConnection().setAutoCommit(false);	
			connection.SetStatement(rowStmt);
			connection.RunQuery();
			connection.GetConnection().commit();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			//System.err.printf("getTableRowCount()  %s   %s\n ", sql, ex);
		}

		return count;

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
	
	
}
