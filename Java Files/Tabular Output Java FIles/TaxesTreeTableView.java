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

import application.BenefitsTreeTableView.BenefitsTreeTable;
import application.GrossTreeTableView.GrossTreeTable;

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

public class TaxesTreeTableView implements Initializable{

	Map <Integer, ArrayList<String>> taxMap = new HashMap<Integer, ArrayList<String>>();
	
	ArrayList<String> SearchResult = new ArrayList<>();
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
    @FXML
	private FlowPane taxesPane;

    @FXML
	private JFXTreeTableView<TaxesTreeTable> taxesTreeView;
       
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		JFXTreeTableColumn<TaxesTreeTable,String> userIDCol = new JFXTreeTableColumn<>("User ID");
		userIDCol.setPrefWidth(150);
		userIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TaxesTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TaxesTreeTable, String> param){
				return param.getValue().getValue().userId;
			}
		});
		
		JFXTreeTableColumn<TaxesTreeTable,String> lnameCol = new JFXTreeTableColumn<>("Last Name");
		lnameCol.setPrefWidth(150);
		lnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TaxesTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TaxesTreeTable, String> param){
				return param.getValue().getValue().lname;
			}
		});

		JFXTreeTableColumn<TaxesTreeTable,String> statusCol = new JFXTreeTableColumn<>("Status");
		statusCol.setPrefWidth(150);
		statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TaxesTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TaxesTreeTable, String> param){
				return param.getValue().getValue().status;
			}
		});
		
		JFXTreeTableColumn<TaxesTreeTable,String> deductionCol = new JFXTreeTableColumn<>("Deductions");
		deductionCol.setPrefWidth(150);
		deductionCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TaxesTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TaxesTreeTable, String> param){
				return param.getValue().getValue().deduction;
			}
		});
		
		//placeholder info, will replace with info from taxes classes
		ObservableList<TaxesTreeTable> taxesList = FXCollections.observableArrayList();

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
					for(Entry<Integer, ArrayList<String>> ee : taxMap.entrySet()) {
						
						Integer key = ee.getKey();
						ArrayList<String> values = ee.getValue();
						
						taxesList.add(new TaxesTreeTable(key, values.get(0), values.get(1), values.get(2)));
						
					}
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final TreeItem<TaxesTreeTable> root = new RecursiveTreeItem<TaxesTreeTable>(taxesList, RecursiveTreeObject::getChildren);
				taxesTreeView.getColumns().setAll(userIDCol, lnameCol, statusCol, deductionCol/*, benefitsNameCol*/);
				taxesTreeView.setRoot(root);
				taxesTreeView.setShowRoot(false);
				
				try {
					connection.EndAllConnections();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	class TaxesTreeTable extends RecursiveTreeObject<TaxesTreeTable>{
		
		StringProperty userId;
		StringProperty lname;
		StringProperty status;
		StringProperty deduction;
		
		public TaxesTreeTable(Integer userId, String lname, String status, String deduct) {
			this.userId = new SimpleStringProperty(userId.toString());
			this.lname = new SimpleStringProperty(lname);
			this.status = new SimpleStringProperty(status);
			this.deduction = new SimpleStringProperty(deduct);
		}
		
	}

	//uses a regular statement, no parameters here
			public void EmployeesRecord() {
				
				String searchQuery = "SELECT empid, lname, marital_status, tax_deduct FROM Employees";
				connection.SetQuery(searchQuery);
				
				try(Statement searchStatement = connection.GetConnection().createStatement()){
					
					connection.GetConnection().setAutoCommit(false);	
					connection.SetStatement(searchStatement);
					connection.RunQuery();
					connection.GetConnection().commit();
				
					while(connection.GetResultSet().next()) {
						
						String lname = connection.GetResultSet().getString("lname");
						String status = connection.GetResultSet().getString("marital_status");
						String deduct = connection.GetResultSet().getString("tax_deduct");
						
						taxMap.put(connection.GetResultSet().getInt("empId"), new ArrayList<String>(Arrays.asList(lname, status, deduct)));
						
					}
					
					
				} catch(SQLException errors) {
					
					errors.printStackTrace();
//					if(connection.GetConnection() != null) {
//						
//						 try {
//					         
//							 System.err.print("Transaction is being rolled back");
//					         connection.GetConnection().rollback();
//					        
//						 } catch (SQLException excep) {
//					      
//							 excep.printStackTrace();
//							 JDBCTutorialUtilities.printSQLException(excep);
//					        
//						 }
//						
//					}
				}
				
			}
			
			//pay attention to commented sections of the code for code reusability when optimizing in the future
//			protected int getTableRowCount(String tableName) {
			protected int getTableRowCount() {
				int count = -1;

//				String query = "SELECT COUNT(*) AS rowcount FROM " + tableName;
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
