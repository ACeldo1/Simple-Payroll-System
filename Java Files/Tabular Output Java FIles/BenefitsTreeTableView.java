package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.EmployeeTreeTableView.EmployeeTreeTable;

import java.net.URL;
import java.util.ResourceBundle;
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
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class BenefitsTreeTableView implements Initializable{

	Map <Integer, ArrayList<String>> benefitMap = new HashMap<Integer, ArrayList<String>>();
	
	ArrayList<String> SearchResult = new ArrayList<>();
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
    @FXML
	private FlowPane benefitsPane;

    @FXML
	private JFXTreeTableView<BenefitsTreeTable> benefitsTreeView;
       
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		JFXTreeTableColumn<BenefitsTreeTable,String> userIDCol = new JFXTreeTableColumn<>("User ID");
		userIDCol.setPrefWidth(150);
		userIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BenefitsTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BenefitsTreeTable, String> param){
				return param.getValue().getValue().userId;
			}
		});

		JFXTreeTableColumn<BenefitsTreeTable,String> lnameCol = new JFXTreeTableColumn<>("Last Name");
		lnameCol.setPrefWidth(150);
		lnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BenefitsTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BenefitsTreeTable, String> param){
				return param.getValue().getValue().lname;
			}
		});
	
		JFXTreeTableColumn<BenefitsTreeTable,String> planCodeCol = new JFXTreeTableColumn<>("Plan Code");
		planCodeCol.setPrefWidth(150);
		planCodeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BenefitsTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BenefitsTreeTable, String> param){
				return param.getValue().getValue().code;
			}
		});
		
//		JFXTreeTableColumn<BenefitsTreeTable,String> benefitsNameCol = new JFXTreeTableColumn<>("Benefit Name");
//		benefitsNameCol.setPrefWidth(150);
//		benefitsNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BenefitsTreeTable,String>, ObservableValue<String>>() {
//			@Override
//			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BenefitsTreeTable, String> param){
//				return param.getValue().getValue().benefitName;
//			}
//		});
	
		//placeholder info, will incorporate the classes with Benefits
		ObservableList<BenefitsTreeTable> benefits = FXCollections.observableArrayList();
		
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
			
			for(Entry<Integer, ArrayList<String>> ee : benefitMap.entrySet()) {
				
				Integer key = ee.getKey();
				ArrayList<String> values = ee.getValue();
				
				benefits.add(new BenefitsTreeTable(key, values.get(0), values.get(1)));
				
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final TreeItem<BenefitsTreeTable> root = new RecursiveTreeItem<BenefitsTreeTable>(benefits, RecursiveTreeObject::getChildren);
		benefitsTreeView.getColumns().setAll(userIDCol, lnameCol, planCodeCol/*, benefitsNameCol*/);
		benefitsTreeView.setRoot(root);
		benefitsTreeView.setShowRoot(false);
		
		try {
			connection.EndAllConnections();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	class BenefitsTreeTable extends RecursiveTreeObject<BenefitsTreeTable>{
		
		StringProperty userId;
		StringProperty lname;
		StringProperty code;
//		StringProperty benefitName;
		
		public BenefitsTreeTable(Integer userId, String lname, String code/*, String benefit*/) {
			this.userId = new SimpleStringProperty(userId.toString());
			this.lname = new SimpleStringProperty(lname);
			this.code = new SimpleStringProperty(code);
//			this.benefitName = new SimpleStringProperty(benefit);
			
		}
		
	}
	
	//uses a regular statement, no parameters here
		public void EmployeesRecord() {
			
			String searchQuery = "SELECT empId, lname, benefit_code FROM Employees";
			connection.SetQuery(searchQuery);
			
			try(Statement searchStatement = connection.GetConnection().createStatement()){
				
				connection.GetConnection().setAutoCommit(false);	
				connection.SetStatement(searchStatement);
				connection.RunQuery();
				connection.GetConnection().commit();
				
				while(connection.GetResultSet().next()) {
					
					String lname = connection.GetResultSet().getString("lname");
					String code = connection.GetResultSet().getString("benefit_code");
					
					benefitMap.put(connection.GetResultSet().getInt("empId"), new ArrayList<String>(Arrays.asList(lname, code)));
					
				}
				
			} catch(SQLException errors) {
				
				errors.printStackTrace();
//				if(connection.GetConnection() != null) {
//					
//					 try {
//				         
//						 System.err.print("Transaction is being rolled back");
//				         connection.GetConnection().rollback();
//				        
//					 } catch (SQLException excep) {
//				      
//						 excep.printStackTrace();
//						 JDBCTutorialUtilities.printSQLException(excep);
//				        
//					 }
//					
//				}
			}
			
		}
		
		//pay attention to commented sections of the code for code reusability when optimizing in the future
//		protected int getTableRowCount(String tableName) {
		protected int getTableRowCount() {
			int count = -1;

//			String query = "SELECT COUNT(*) AS rowcount FROM " + tableName;
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
