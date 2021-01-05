package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.naming.NamingException;

import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.BenefitsTreeTableView.BenefitsTreeTable;
import application.EmployeeTreeTableView.EmployeeTreeTable;
import application.NetTreeTableView.NetTreeTable;

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

public class GrossTreeTableView implements Initializable{

	Map <Integer, ArrayList<String>> grossMap = new HashMap<Integer, ArrayList<String>>();
	
	ArrayList<String> SearchResult = new ArrayList<>();
	ArrayList<String> SearchResult2 = new ArrayList<>();
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
    @FXML
	private FlowPane grossPane;

    @FXML
	private JFXTreeTableView<GrossTreeTable> grossTreeView;
       
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		JFXTreeTableColumn<GrossTreeTable,String> userIDCol = new JFXTreeTableColumn<>("User ID");
		userIDCol.setPrefWidth(150);
		userIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().userId;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> lnameCol = new JFXTreeTableColumn<>("Last Name");
		lnameCol.setPrefWidth(150);
		lnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().lname;
			}
		});

//		JFXTreeTableColumn<GrossTreeTable,String> grossCol = new JFXTreeTableColumn<>("Gross Pay");
//		grossCol.setPrefWidth(150);
//		grossCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
//			@Override
//			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
//				return param.getValue().getValue().gross;
//			}
//		});
		
		JFXTreeTableColumn<GrossTreeTable,String> salaryCol = new JFXTreeTableColumn<>("Salary");
		salaryCol.setPrefWidth(150);
		salaryCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().salary;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> hoursCol = new JFXTreeTableColumn<>("Hours");
		hoursCol.setPrefWidth(150);
		hoursCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().hours;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> overtimeCol = new JFXTreeTableColumn<>("Overtime Hours");
		overtimeCol.setPrefWidth(150);
		overtimeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().othours;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> bonusCol = new JFXTreeTableColumn<>("Bonuses");
		bonusCol.setPrefWidth(150);
		bonusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().bonus;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> raiseCol = new JFXTreeTableColumn<>("Raise Salary");
		raiseCol.setPrefWidth(150);
		raiseCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().raise;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> reasonCol = new JFXTreeTableColumn<>("Raise Reason");
		reasonCol.setPrefWidth(150);
		reasonCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().raisereason;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> dateCol = new JFXTreeTableColumn<>("Raise Effective");
		dateCol.setPrefWidth(150);
		dateCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().raisedate;
			}
		});
		
		JFXTreeTableColumn<GrossTreeTable,String> timeCol = new JFXTreeTableColumn<>("Full Time?");
		timeCol.setPrefWidth(150);
		timeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GrossTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GrossTreeTable, String> param){
				return param.getValue().getValue().full;
			}
		});
	
		//placeholder info, will change to gross pay info from other classes
		ObservableList<GrossTreeTable> grossList = FXCollections.observableArrayList();

		//establishing connection with database
		EstablishConnection();
						
		//number of rows in Employees Table
		Integer rowCount = -1;
		rowCount = getTableRowCount();
						
		//getting the records from the table into a resultset ready to be accessed
		//Will add grosspay value later
		//EmployeesRecord();
		GrossRecord();	
		
		//when modifying this in the future, use a map or set, its much easier,
		//what i used here is a quick, dirty solution
		try {
			for (Entry<Integer, ArrayList<String>> ee : grossMap.entrySet()) {
			    
				Integer key = ee.getKey();
			    ArrayList<String> values = ee.getValue();
			    // TODO: Do something.
						
			    grossList.add(new GrossTreeTable(key, values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8)));
						    
			}
					
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final TreeItem<GrossTreeTable> root = new RecursiveTreeItem<GrossTreeTable>(grossList, RecursiveTreeObject::getChildren);
		grossTreeView.getColumns().setAll(userIDCol, lnameCol, salaryCol, hoursCol, overtimeCol, bonusCol, raiseCol, reasonCol, dateCol, timeCol);
		grossTreeView.setRoot(root);
		grossTreeView.setShowRoot(false);
			
		try {
			connection.EndAllConnections();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	class GrossTreeTable extends RecursiveTreeObject<GrossTreeTable>{
		
		StringProperty userId;
		StringProperty lname;
		//StringProperty gross;
		StringProperty salary;
		StringProperty hours;
		StringProperty othours;
		StringProperty bonus;
		StringProperty raise;
		StringProperty raisereason;
		StringProperty raisedate;
		StringProperty full;
		
		public GrossTreeTable(Integer userId, String lname, /*Double gross*/ String sal, String hrs, String othrs, String b, String r, String rr, String rd, String ft) {
			this.userId = new SimpleStringProperty(userId.toString());
			this.lname = new SimpleStringProperty(lname);
			//this.gross = new SimpleStringProperty(gross.toString());
			this.salary = new SimpleStringProperty(sal);
			this.hours = new SimpleStringProperty(hrs);
			this.othours = new SimpleStringProperty(othrs);
			this.bonus = new SimpleStringProperty(b);
			this.raise = new SimpleStringProperty(r);
			this.raisereason = new SimpleStringProperty(rr);
			this.raisedate = new SimpleStringProperty(rd);
			this.full = new SimpleStringProperty(ft);
		
		}
		
	}

	//uses a regular statement, no parameters here
			public void EmployeesRecord() {
				
				String searchQuery = "SELECT gross_pay FROM Employees";
				connection.SetQuery(searchQuery);
				
				try(Statement searchStatement = connection.GetConnection().createStatement()){
					
					connection.GetConnection().setAutoCommit(false);	
					connection.SetStatement(searchStatement);
					connection.RunQuery();
					connection.GetConnection().commit();
					
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
			
			public void GrossRecord() {
				
				String searchQuery = "SELECT * FROM GrossPayInfo";
				connection.SetQuery(searchQuery);
				
				try(Statement searchStatement = connection.GetConnection().createStatement()){
					
					connection.GetConnection().setAutoCommit(false);	
					connection.SetStatement(searchStatement);
					connection.RunQuery();
					connection.GetConnection().commit();
					
					while (connection.GetResultSet().next()) {
						
//						SearchResult.add(connection.GetResultSet().getString("lname"));
//						SearchResult.add(connection.GetResultSet().getString("net_pay"));
								
						String lname = connection.GetResultSet().getString("lname");
						String salary = connection.GetResultSet().getString("salary");
						String hours = connection.GetResultSet().getString("hours");
						String othours = connection.GetResultSet().getString("overtimeHours");
						String bonus = connection.GetResultSet().getString("bonuses");
						String raise = connection.GetResultSet().getString("raiseSalary");
						String reason = connection.GetResultSet().getString("raiseReason");
						String date = connection.GetResultSet().getString("raiseEffectiveDate");
						String status = connection.GetResultSet().getString("full_time");
								
						grossMap.put(connection.GetResultSet().getInt("empId"), new ArrayList<String>(Arrays.asList(lname, salary, hours, othours, bonus, raise, reason, date, status)));
								
						//SearchResult.clear();
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
				String query = "SELECT COUNT(*) AS rowcount FROM GrossPayInfo";
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
