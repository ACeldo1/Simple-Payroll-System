package application;

import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;


import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.util.Callback;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class tabPaneController implements Initializable {

	  /*For manager's view of employee list*/
    @FXML
    private FlowPane employeePane;

    @FXML
    private JFXTreeTableView<employeeTreeTable> employeeTreeView;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JFXTreeTableColumn<employeeTreeTable,String> userIDCol = new JFXTreeTableColumn<>("User ID");
		userIDCol.setPrefWidth(150);
		userIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<employeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<employeeTreeTable, String> param){
				return param.getValue().getValue().userId;
			}
		});

		JFXTreeTableColumn<employeeTreeTable,String> fnameCol = new JFXTreeTableColumn<>("First Name");
		fnameCol.setPrefWidth(150);
		fnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<employeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<employeeTreeTable, String> param){
				return param.getValue().getValue().firstName;
			}
		});
	
		JFXTreeTableColumn<employeeTreeTable,String> lnameCol = new JFXTreeTableColumn<>("Last Name");
		lnameCol.setPrefWidth(150);
		lnameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<employeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<employeeTreeTable, String> param){
				return param.getValue().getValue().lastName;
			}
		});
	
		JFXTreeTableColumn<employeeTreeTable,String> bdayCol = new JFXTreeTableColumn<>("Birth Date");
		bdayCol.setPrefWidth(150);
		bdayCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<employeeTreeTable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<employeeTreeTable, String> param){
				return param.getValue().getValue().birthdate;
			}
		});
		
		ObservableList<employeeTreeTable> employees = FXCollections.observableArrayList();
		employees.add(new employeeTreeTable("10001", "fname1", "lname1", "birthdate1"));
		employees.add(new employeeTreeTable("10002", "fname2", "lname2", "birthdate2"));
		employees.add(new employeeTreeTable("10003", "fname3", "lname3", "birthdate3"));
		employees.add(new employeeTreeTable("10004", "fname4", "lname4", "birthdate4"));
		
		TreeItem<employeeTreeTable> root = new RecursiveTreeItem<employeeTreeTable>(employees, RecursiveTreeObject::getChildren);
		employeeTreeView.getColumns().setAll(userIDCol, fnameCol, lnameCol, bdayCol);
		employeeTreeView.setRoot(root);
		employeeTreeView.setShowRoot(false);
		
	}
	
	class employeeTreeTable extends RecursiveTreeObject<employeeTreeTable>{
		
		StringProperty userId;
		StringProperty firstName;
		StringProperty lastName;
		StringProperty birthdate;
		
		public employeeTreeTable(String userId, String firstName, String lastName, String birthdate) {
			this.userId = new SimpleStringProperty(userId);
			this.firstName = new SimpleStringProperty(firstName);
			this.lastName = new SimpleStringProperty(lastName);
			this.birthdate = new SimpleStringProperty(birthdate);
		}
		
	}
	

}
