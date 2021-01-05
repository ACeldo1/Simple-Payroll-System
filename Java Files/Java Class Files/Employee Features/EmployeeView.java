package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import application.EditBenefits.EditHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 
import javafx.fxml.Initializable;

public class EmployeeView{
							
		//change to your stuff
		//project is my db name, change it to yours.
		//and in line 126, 318, 333, change your stuffs.
		String databaseLocation = "jdbc:mysql://localhost:3306/csc430";
		String root = "root";
		String password = "Bloodywolf1234!";
		Button confirm;
		Button edit;
		Button cancel;
		Button searchbutton;
		TextField search;
		TextField cin_firstname;
		TextField cin_lastname;
		TextField cin_SSN;
		DatePicker cin_birthday;
		TextField cin_benefits;
		TextField cin_phonenum1;
		TextField cin_phonenum2;
		TextField cin_address;
		TextField cin_email;
		TextField cin_grosspay;
		TextField cin_tax;
		TextField cin_netpay;
		ToggleGroup radioGroup1;
		RadioButton radio1;
		RadioButton radio2;
		RadioButton radio3;
		RadioButton radio4;
		RadioButton active;
		ToggleGroup radioGroup2;
		RadioButton terminated;
		RadioButton quit;
		RadioButton deceased;
		RadioButton disable;
		Alert alert;
		HBox bcode;
		HBox topmenu;
		HBox botmenu=new HBox();
		VBox setmenu = new VBox();
		HBox buttons;
		HBox completeform;
		VBox workstatus;

		int length = 1500, weight = 300;
		int workingstatus;
		//ismanager = true when empType = 1. for now i just make it to true.
		boolean isempty,finderror,ismanager=true;
		Scene scene;
		Stage primaryStage;
		Connection connect;
		PreparedStatement pst;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//		public static void main(String[] args) throws ParseException {
//			// TODO Auto-generated method stub
//			launch(args);
//			
//		}
		
		protected EmployeeView() {
			
		}
		
		public void start(Pane parent, int id) throws Exception{
		
			//the form has to be made first because once another method starts, the result set can no longer be used
			makeobject();
			setdisable();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(databaseLocation, root, password);
				//connect = DriverManager.getConnection(databaseLocation, root, password);
				String queue = "Select * FROM Employees LEFT JOIN GrossPayInfo on Employees.empId = GrossPayInfo.empId where Employees.empId = "+id;
				pst = connect.prepareStatement(queue);
				ResultSet rs = pst.executeQuery();

			rs.next();
						
						cin_firstname.setText(rs.getString("fname"));
						System.out.println(cin_firstname.getText());
						cin_lastname.setText(rs.getString("lname"));
						cin_SSN.setText(rs.getString("ssn"));
						cin_birthday.setValue(LocalDate.parse(rs.getNString("birth_date")));
						cin_address.setText(rs.getString("address"));
						cin_email.setText(rs.getString("email"));
						cin_phonenum1.setText(rs.getString("phone_num"));
						cin_phonenum2.setText(rs.getString("emergency_num"));
						cin_benefits.setText(rs.getString("benefit_code"));
						cin_grosspay.setText(String.valueOf(rs.getDouble("gross_pay")));
						cin_tax.setText(String.valueOf(rs.getDouble("tax_deduct")));
						cin_netpay.setText(String.valueOf(rs.getDouble("net_pay")));
						if(rs.getInt("marital_status")==1)
						radio1.setSelected(true);
						else if(rs.getInt("marital_status")==2)
						radio2.setSelected(true);
						else if(rs.getInt("marital_status")==3)
						radio3.setSelected(true);
						else
							radio4.setSelected(true);
						if(rs.getInt("working_status")==0)
						active.setSelected(true);
						else if(rs.getInt("working_status")==1)
						terminated.setSelected(true);
						else if(rs.getInt("working_status")==2)
						deceased.setSelected(true);
						else
						disable.setSelected(true);	
						workingstatus = rs.getInt("working_status");
						if(deceased.isSelected()==false)
						{
//						edit = new Button("Edit");
//						edit.setOnAction(new EditHandler());
//						buttons = new HBox(edit);
//						buttons.setPadding(new Insets(20));
//						botmenu.getChildren().add(buttons);
						}
//						botmenu.getChildren().add(buttons);
						setmenu.getChildren().addAll(botmenu);
						
//						setdisable();

			

		parent.getChildren().addAll(setmenu);
		
	}
		
	public void makeobject()
	{
		Label L1 = new Label("       First name:");
		cin_firstname = new TextField();
		HBox combine1 = new HBox(L1,cin_firstname);
		
		Label L2 = new Label("               Last name:");
		cin_lastname = new TextField();
		HBox combine2 = new HBox(L2, cin_lastname);
		
		HBox connect1 = new HBox(8,combine1,combine2);
		
		Label L3 = new Label("                 SSN:");
		cin_SSN = new TextField();
		HBox combine3 = new HBox(L3, cin_SSN);
		
		Label L4 = new Label("          Birthday:");
		cin_birthday = new DatePicker();
		HBox combine4 = new HBox(L4,cin_birthday);
		
		HBox connect2 = new HBox(8,combine3,combine4);
		
		Label L5 = new Label("           Address:");
		cin_address = new TextField();
		HBox combine5 = new HBox(L5,cin_address);
		
		Label L6 = new Label("                      Email:");
		cin_email = new TextField();
		HBox combine6 = new HBox(L6, cin_email);
		
		HBox connect3 = new HBox(8,combine5,combine6);
		
		Label L7 = new Label("Phone number:");
		cin_phonenum1 = new TextField();
		HBox combine7 = new HBox(L7,cin_phonenum1);
		
		Label L8 = new Label("Emergency number:");
		cin_phonenum2 = new TextField();
		HBox combine8 = new HBox(L8, cin_phonenum2);
		
		HBox connect4 = new HBox(8,combine7,combine8);
		
		Label L9 = new Label("    Benefit code:");
		cin_benefits = new TextField();
		HBox combine9 = new HBox(L9,cin_benefits);
		
		
		Label L10 = new Label("               Gross pay:");
		cin_grosspay = new TextField();
		HBox combine10 = new HBox(L10,cin_grosspay);
		
		HBox connect5 = new HBox(8,combine9,combine10);
		
		Label L11 = new Label("                  Tax:");
		cin_tax = new TextField();
		HBox combine11 = new HBox(L11,cin_tax);
		
		Label L12 = new Label("                   Net pay:");
		cin_netpay = new TextField();
		HBox combine12 = new HBox(L12,cin_netpay);
		
		HBox connect6 = new HBox(8,combine11,combine12);
		
		
		VBox textfield = new VBox(8,connect1,connect2,connect3,connect4,connect5,connect6);
		textfield.setPadding(new Insets(20));
		radioGroup1 = new ToggleGroup();
		radio1 = new RadioButton("Single");
		radio2 = new RadioButton("Married filing jointly");
		radio3 = new RadioButton("Married filing separately");
		radio4 = new RadioButton("Head of household");
		radio1.setToggleGroup(radioGroup1);
		radio2.setToggleGroup(radioGroup1);
		radio3.setToggleGroup(radioGroup1);
		radio4.setToggleGroup(radioGroup1);
		VBox group1 = new VBox(5,radio1,radio2,radio3,radio4);
		
		radioGroup2 = new ToggleGroup();
		active = new RadioButton("Active      ");
		terminated = new RadioButton("Terminated");
		deceased =  new RadioButton("Deceased");
		disable = new RadioButton("Disable");
		active.setToggleGroup(radioGroup2);
		terminated.setToggleGroup(radioGroup2);
		deceased.setToggleGroup(radioGroup2);
		disable.setToggleGroup(radioGroup2);
		HBox workstatus1 = new HBox(5,active,terminated);
		HBox workstatus2 = new HBox(5,deceased,disable);
		VBox group2 = new VBox(5,workstatus1,workstatus2);
		
		VBox connect_group = new VBox(40,group1,group2);
		connect_group.setPadding(new Insets(20));
		completeform = new HBox(textfield,connect_group);
		
		botmenu.getChildren().addAll(completeform);
		
	}
	public void setdisable()
	{
		cin_firstname.setDisable(true);
		cin_lastname.setDisable(true);
		cin_SSN.setDisable(true);
		cin_birthday.setDisable(true);
		cin_address.setDisable(true);
		cin_email.setDisable(true);
		cin_phonenum1.setDisable(true);
		cin_phonenum2.setDisable(true);
		cin_benefits.setDisable(true);
		cin_grosspay.setDisable(true);
		cin_tax.setDisable(true);
		cin_netpay.setDisable(true);
		radio1.setDisable(true);
		radio2.setDisable(true);
		radio3.setDisable(true);
		radio4.setDisable(true);
		active.setDisable(true);
		terminated.setDisable(true);
		deceased.setDisable(true);
		disable.setDisable(true);
		
		cin_firstname.setVisible(true);
		cin_lastname.setVisible(true);
		cin_SSN.setVisible(true);
		cin_birthday.setVisible(true);
		cin_address.setVisible(true);
		cin_email.setVisible(true);
		cin_phonenum1.setVisible(true);
		cin_phonenum2.setVisible(true);
		cin_benefits.setVisible(true);
		cin_grosspay.setVisible(true);
		cin_tax.setVisible(true);
		cin_netpay.setVisible(true);
		radio1.setVisible(true);
		radio2.setVisible(true);
		radio3.setVisible(true);
		radio4.setVisible(true);
		active.setVisible(true);
		terminated.setVisible(true);
		deceased.setVisible(true);
		disable.setVisible(true);
	}


}