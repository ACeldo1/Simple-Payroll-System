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

import application.NetTreeTableView.NetTreeTable;
import application.GrossTreeTableView.GrossTreeTable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class NetTreeTableView implements Initializable{
	
	Map <Integer, ArrayList<String>> netMap = new HashMap<Integer, ArrayList<String>>();
	
	ArrayList<String> SearchResult = new ArrayList<>();
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
    @FXML
	private FlowPane netPane;

    @FXML
	private JFXTreeTableView<NetTreeTable> netTreeView;
       
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		JFXTreeTableColumn<NetTreeTable,String> userIDCol = new JFXTreeTableColumn<>("User ID");
		userIDCol.setPrefWidth(150);
		userIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NetTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NetTreeTable, String> param){
				return param.getValue().getValue().userId;
			}
		});

		JFXTreeTableColumn<NetTreeTable,String> lnameCol = new JFXTreeTableColumn<>("Last Name");
		lnameCol.setPrefWidth(150);
		lnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NetTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NetTreeTable, String> param){
				return param.getValue().getValue().lname;
			}
		});
		
		JFXTreeTableColumn<NetTreeTable,String> netCol = new JFXTreeTableColumn<>("Net Pay");
		netCol.setPrefWidth(150);
		netCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NetTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NetTreeTable, String> param){
				return param.getValue().getValue().net;
			}
		});
	
		//placeholder info, will replace with info from net pay classes
		ObservableList<NetTreeTable> netList = FXCollections.observableArrayList();
	
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
						
			for (Entry<Integer, ArrayList<String>> ee : netMap.entrySet()) {
						    
				Integer key = ee.getKey();
			    ArrayList<String> values = ee.getValue();
			    // TODO: Do something.
						
			    netList.add(new NetTreeTable(key, values.get(0), values.get(1)));
						    
			}
					
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		final TreeItem<NetTreeTable> root = new RecursiveTreeItem<NetTreeTable>(netList, RecursiveTreeObject::getChildren);
		netTreeView.getColumns().setAll(userIDCol, lnameCol, netCol/*, benefitsNameCol*/);
		netTreeView.setRoot(root);
		netTreeView.setShowRoot(false);
				
		try {
			connection.EndAllConnections();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	class NetTreeTable extends RecursiveTreeObject<NetTreeTable>{
		
		StringProperty userId;
		StringProperty lname;
		StringProperty net;
		
		public NetTreeTable(Integer userId, String lname, String net) {
			this.userId = new SimpleStringProperty(userId.toString());
			this.lname = new SimpleStringProperty(lname);
			this.net = new SimpleStringProperty(net);
		}
		
	}
	
	//uses a regular statement, no parameters here
	public void EmployeesRecord() {
				
		String searchQuery = "SELECT empId, lname, net_pay FROM Employees";
		connection.SetQuery(searchQuery);
				
		try(Statement searchStatement = connection.GetConnection().createStatement()){
					
			connection.GetConnection().setAutoCommit(false);	
			connection.SetStatement(searchStatement);
			connection.RunQuery();
			connection.GetConnection().commit();
					
			//connection.GetResultSet().first();
					
			while (connection.GetResultSet().next()) {
						
//				SearchResult.add(connection.GetResultSet().getString("lname"));
//				SearchResult.add(connection.GetResultSet().getString("net_pay"));
						
				String lname = connection.GetResultSet().getString("lname");						
				String net = connection.GetResultSet().getString("net_pay");
						
				netMap.put(connection.GetResultSet().getInt("empId"), new ArrayList<String>(Arrays.asList(lname, net)));
						
				SearchResult.clear();
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
