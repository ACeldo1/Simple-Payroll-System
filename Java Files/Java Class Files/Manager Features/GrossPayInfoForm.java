package application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Optional;
import javax.naming.NamingException;

import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

import java.sql.SQLException;
import java.sql.PreparedStatement;

//this form will allow employee pay info to be added into the GrossPayInfo table
//will use a select query to compare the input empId to see if a record exists already in the Employee tables.
//If not, then the info will not be added and there will be an error
public class GrossPayInfoForm {

	GrossPay grosspay;
	NetPay netpay;
	Tax tax;
	
	JDBCConnector connection;
	Boolean connectionStatus;
	
	ArrayList<String> SearchResult = new ArrayList<>(); //search result arraylist for Employees table results
	ArrayList<String> SearchResult2 = new ArrayList<>(); //search result arraylist for GrossPayInfo table results
	ArrayList<String> ManagerVerify = new ArrayList<>(); //search result arraylist that will have manager credentials for verification
	
	Button confirm = new Button();
	Button add;
	Button reset;
	Button editconfirm;
	Button search;
	Button cancel;
	Button edit;
	Button delete;
	Button calculate;
	
	ComboBox <String> options;
	
	Label searchlabel = new Label("Search:");
	TextField cin_search;
	TextField cin_lastname;
	TextField cin_salary;
	TextField cin_hours;
	TextField cin_overtimehours;
	DatePicker cin_lastpaydate;
	TextField cin_bonuses;
	TextField cin_raisesalary;
	TextField cin_raisereason;
	DatePicker cin_raisedate;
	
	//Will use this at a later date, maybe after this class
	/*
	NumberFormat hoursFormat;
	NumberFormat bonusesFormat;
	NumberFormat raisesalaryFormat;
	*/
	
	VBox finalvbox = new VBox();
	VBox setfinalvbox = new VBox();
	RadioButton radio1;
	RadioButton radio2;
	ToggleGroup radioGroup;
	
	Alert alert;
	VBox combim10;
	HBox bcode;
	HBox bottommenu;
	VBox menu;
	HBox setmenu;
	int number = 0;
	int length = 1100, weight = 300,count=0;
	
	Integer confirmtype;//1 for adding a new record of pay info, 0 for updating an existing record of pay info
	Integer viewtype = 0; //0 for management, 1 for employees to see very limited info
	//will need to figure out how we get either a 0 or 1 from the login screen
	int makemenuType; //0 for management, 1 for employee, but I don't think im using this
	//rn im using a static variable that gets the input from the login form
	
	DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	Boolean inEmployeesTable = false;
	Boolean inGrossTable = false;
	
//	public static void main(String[] args) throws ParseException, FileNotFoundException {
//		// TODO Auto-generated method stub
//		launch(args);
//	}
	
	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		
		EstablishConnection();
		
		cin_search = new TextField();
		cin_search.setPromptText("Search using ID");
		cin_search.setPrefWidth(300);
		cin_search.setFocusTraversable(false);
		options = new ComboBox <>();
		options.getItems().addAll("ID");
			
		search = new Button("Search");
		search.setOnAction(new SearchHandler());

		add = new Button("Add Pay Info");
		add.setOnAction(new AddHandler());
		
		HBox combin_seach_text1 = new HBox(5,searchlabel,cin_search);
		HBox combin_seach_text2 = new HBox(5,combin_seach_text1,options,search);
		HBox addvbox = new HBox(add);
		addvbox.setAlignment(Pos.TOP_RIGHT);
		HBox top_menu = new HBox(50,combin_seach_text2,addvbox);
		setfinalvbox.getChildren().add(top_menu);
		Scene scene = new Scene(setfinalvbox,length,weight);
		
		window.setScene(scene);
		window.setTitle("Manage Employee Pay");
		window.show();
		
		
		//if click search.
		//if an employee is found in Employee table, we keep the info in an arraylist and the user
		//can add/update info for the GrossPayInfo table and based on that info, another update 
		// query will run adding the gross to the Employee table
//		search.setOnAction(e->{
//			
//			//check is search text field is empty or not.
//			if(cin_search.getText().isEmpty()) {
//
//				alert=new Alert(AlertType.WARNING);
//				alert.setTitle("Warning");
//				alert.setHeaderText(null);
//				alert.setContentText("You didn't enter anything");
//				alert.showAndWait();
//			}
//			//check if the option is pick or not.
//			else if(options.getValue()==null) {
//				
//				alert=new Alert(AlertType.WARNING);
//				alert.setTitle("Warning");
//				alert.setHeaderText(null);
//				alert.setContentText("Which one are u finding? ID/SSN");
//				alert.showAndWait();
//
//			}
//			//if everything is not empty, we run this.
//			//this will use the method FindEmployee()
//			else {
//				
//				//EmployeeSearch(Integer.parseInt(cin_search.getText()));
//				
//				if(options.getValue().equals("ID")) {
//					
//					Integer myid = -1;
//					try {
//						myid = Integer.parseInt(cin_search.getText());
//					} catch(Exception iderror) {
//						iderror.printStackTrace();
//					}
//					
//					System.out.println(myid);
//					
//					EmployeeSearch(myid);
//					
//					//for checking if the employee is in the Employees table
//					if(connection.GetResultSet() == null || SearchResult.isEmpty()) {
//						
//						alert=new Alert(AlertType.WARNING);
//						alert.setTitle("Warning");
//						alert.setHeaderText(null);
//						alert.setContentText("Employee does not exist!");
//						alert.showAndWait();
//							
//					}
//					
//					else {
//						
//						SetInEmployeesTable(true);
//						EmployeeSearchGross(myid);
//						
//						//for checking if the employee is in the GrossPayInfo table
//						if(connection.GetResultSet() == null || SearchResult2.isEmpty()) {
//							
//							alert=new Alert(AlertType.WARNING);
//							alert.setTitle("Warning");
//							alert.setHeaderText(null);
//							alert.setContentText("Employee exists, but doesn't have any pay info set! Consider adding pay info instead");
//							alert.showAndWait();
//								
//						}
//						else {
//						
//							SetInGrossTable(true);
//							add.setDisable(true);
//						
//							alert=new Alert(AlertType.INFORMATION);
//							alert.setTitle("Search Info");
//							alert.setHeaderText(null);
//							alert.setContentText("Employee exists! You can update pay info for this employee!");
//							alert.showAndWait();
//							
//							
//							/*
//							 * Will come back to these other buttons, I am more concerned about
//							 * 1) the logic for calculations and storing those values into the Employees table
//							 * 2) Storing the pay info using the Add Record button to the GrossPayInfo table
//							 * */
//							
//							cancel = new Button("Cancel");
//							cancel.setOnAction(new CancelHandler());
//							edit = new Button("Edit");
//							edit.setOnAction(new EditHandler());
//							editconfirm = new Button("Edit Confirm");
//							editconfirm.setOnAction(new ConfirmHandler());
////							editconfirm.setOnAction(new EditConfirmHandler());
//							calculate = new Button("Calculate");
//							calculate.setOnAction(new CalculationHandler());
//							
//							//delete = new Button("Delete");
//							//delete.setOnAction(new DeleteHandler());
//							
//							SetConfirmType(0);
//							makemenu(0);
//							
//							try {
//								
//								cin_lastname.setText(SearchResult2.get(1));
//								cin_lastname.setDisable(true);
//								cin_salary.setText(SearchResult2.get(2));
//								cin_hours.setText(SearchResult2.get(3));
//								//cin_hours.setDisable(true);
//								cin_overtimehours.setText(SearchResult2.get(4));
//								cin_bonuses.setText(SearchResult2.get(5));
//								cin_raisesalary.setText(SearchResult2.get(6));
//								cin_raisereason.setText(SearchResult2.get(7));
//								cin_raisedate.setValue(LocalDate.parse(SearchResult2.get(8),fmt));
//								
//								
//							} catch(Exception error) {
//								error.printStackTrace();
//							}
//							
//							HBox editbutton = new HBox(edit);
//							HBox cancelbutton = new HBox(cancel);
//							HBox editconfirmbutton = new HBox(editconfirm);
//							HBox calculatebutton = new HBox(calculate);
//							//HBox deletebutton = new HBox(delete);
//							editconfirm.setVisible(false);
//							calculate.setVisible(false);
//							VBox combim10 = new VBox(10,editconfirmbutton,editbutton,calculatebutton,/*deletebutton,*/cancelbutton);
//							bottommenu = new HBox(10,setmenu,combim10);
//							bottommenu.setPadding(new Insets(20));
//							setfinalvbox.getChildren().add(bottommenu);
//							setdisable();
//						}
//						
//					}
//					
//					
//				}
//			
//			}
//			
//		});
//		
//		//if add person is clicked.
//		add.setOnAction(e->{
//			
//			
//			if (GetInEmployeesTable() == true) {
//			
//				add.setDisable(true);	
//				search.setDisable(true);
//				options.setDisable(true);
//				cin_search.setDisable(true);
//				
//				confirm = new Button("Confirm");
//				confirm.setOnAction(new ConfirmHandler());
//				reset = new Button("Reset");
//				reset.setOnAction(new ResetHandler());
//				cancel = new Button("Cancel");
//				cancel.setOnAction(new CancelHandler());
//				calculate = new Button("Calculate");
//				calculate.setOnAction(new CalculationHandler());
//				
//				SetConfirmType(1);
//				makemenu(1);
//				
//	//				try {
//	//					makemenu();
//	//				} catch (IOException e1) {
//	//					// TODO Auto-generated catch block
//	//					e1.printStackTrace();
//	//				}
//	
//				HBox confirmbutton = new HBox(confirm);
//				HBox resetbutton = new HBox(reset);
//				HBox cancelbutton = new HBox(cancel);
//				HBox calculatebutton = new HBox(calculate);
//				VBox combim10 = new VBox(10,calculatebutton,confirmbutton,resetbutton,cancelbutton);
//				bottommenu = new HBox(10,setmenu,combim10);
//					
//				bottommenu.setPadding(new Insets(20));
//				setfinalvbox.getChildren().add(bottommenu);
//				setfinalvbox.getChildren().add(combim11);
//			}
//		
//			else {
//			
//				alert=new Alert(AlertType.WARNING);
//				alert.setTitle("Warning");
//				alert.setHeaderText(null);
//				alert.setContentText("Verify if the empid exists first!");
//				alert.showAndWait();
//				
//			}
//		
//		});
		
	}
	
	public void SetViewType(Integer view) {
		this.viewtype = view;
	}
	
	public Integer GetViewType() {
		return viewtype;
	}
	
	public void SetInEmployeesTable(Boolean emp) {
		this.inEmployeesTable = emp;
	}
	
	public Boolean GetInEmployeesTable() {
		return inEmployeesTable;
	}
	
	public void SetInGrossTable(Boolean gross) {
		this.inGrossTable = gross;
	}
	
	public Boolean GetInGrossTable() {
		return inGrossTable;
	}
	
	public void SetConfirmType(Integer confirm) {
		this.confirmtype = confirm;
	}
	
	public Integer GetConfirmType() {
		return confirmtype;
	}
	
	class SearchHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
		
			//check is search text field is empty or not.
			if(cin_search.getText().isEmpty()) {

				alert=new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("You didn't enter anything");
				alert.showAndWait();
			}
			//check if the option is pick or not.
			else if(options.getValue()==null) {
				
				alert=new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("Select an option from the dropbox");
				alert.showAndWait();

			}
			//if everything is not empty, we run this.
			//this will use the method FindEmployee()
			else {
				
				//EmployeeSearch(Integer.parseInt(cin_search.getText()));
				
				if(options.getValue().equals("ID")) {
					
					Integer myid = -1;
					try {
						myid = Integer.parseInt(cin_search.getText());
					} catch(Exception iderror) {
						iderror.printStackTrace();
					}
					
					System.out.println(myid);
					
					EmployeeSearch(myid);
					
					//for checking if the employee is in the Employees table
					if(connection.GetResultSet() == null || SearchResult.isEmpty()) {
						
						alert=new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Employee does not exist!");
						alert.showAndWait();
							
					}
					
					else {
						
						SetInEmployeesTable(true);
						EmployeeSearchGross(myid);
						
						//for checking if the employee is in the GrossPayInfo table
						if(connection.GetResultSet() == null || SearchResult2.isEmpty()) {
							
							alert=new Alert(AlertType.WARNING);
							alert.setTitle("Warning");
							alert.setHeaderText(null);
							alert.setContentText("Employee exists, but doesn't have any pay info set! Consider adding pay info instead");
							alert.showAndWait();
								
						}
						else {
						
							SetInGrossTable(true);
							add.setDisable(true);
						
							alert=new Alert(AlertType.INFORMATION);
							alert.setTitle("Search Info");
							alert.setHeaderText(null);
							alert.setContentText("Employee exists! You can update pay info for this employee!");
							alert.showAndWait();
							
							
							/*
							 * Will come back to these other buttons, I am more concerned about
							 * 1) the logic for calculations and storing those values into the Employees table
							 * 2) Storing the pay info using the Add Record button to the GrossPayInfo table
							 * */
							
							cancel = new Button("Cancel");
							cancel.setOnAction(new CancelHandler());
							edit = new Button("Edit");
							edit.setOnAction(new EditHandler());
							editconfirm = new Button("Edit Confirm");
							editconfirm.setOnAction(new ConfirmHandler());
//							editconfirm.setOnAction(new EditConfirmHandler());
							calculate = new Button("Calculate");
							calculate.setOnAction(new CalculationHandler());
							
							
							SetConfirmType(0);
							makemenu(0);
							
							try {
								
								cin_lastname.setText(SearchResult2.get(1));
								cin_lastname.setDisable(true);
								cin_salary.setText(SearchResult2.get(2));
								cin_hours.setText(SearchResult2.get(3));
								//cin_hours.setDisable(true);
								cin_overtimehours.setText(SearchResult2.get(4));
								cin_lastpaydate.setValue(LocalDate.parse(SearchResult2.get(5), fmt));
								cin_bonuses.setText(SearchResult2.get(6));
								cin_raisesalary.setText(SearchResult2.get(7));
								cin_raisereason.setText(SearchResult2.get(8));
								cin_raisedate.setValue(LocalDate.parse(SearchResult2.get(9),fmt));
								
								
							} catch(Exception error) {
								error.printStackTrace();
							}
							
							HBox editbutton = new HBox(edit);
							HBox cancelbutton = new HBox(cancel);
							HBox editconfirmbutton = new HBox(editconfirm);
							HBox calculatebutton = new HBox(calculate);
							//HBox deletebutton = new HBox(delete);
							editconfirm.setVisible(false);
							calculate.setVisible(false);
							combim10 = new VBox(10,editconfirmbutton,editbutton,calculatebutton,/*deletebutton,*/cancelbutton);
							bottommenu = new HBox(10,setmenu,combim10);
							bottommenu.setPadding(new Insets(20));
							setfinalvbox.getChildren().add(bottommenu);
							setdisable();
						}
						
					}
							
				}
			
			}
			
		}
		
	}
	
	class AddHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (GetInEmployeesTable() == true) {
				
				add.setDisable(true);	
				search.setDisable(true);
				options.setDisable(true);
				cin_search.setDisable(true);
				
				confirm = new Button("Confirm");
				confirm.setOnAction(new ConfirmHandler());
				reset = new Button("Reset");
				reset.setOnAction(new ResetHandler());
				cancel = new Button("Cancel");
				cancel.setOnAction(new CancelHandler());
				calculate = new Button("Calculate");
				calculate.setOnAction(new CalculationHandler());
				
				SetConfirmType(1);
				makemenu(1);
				
				HBox confirmbutton = new HBox(confirm);
				HBox resetbutton = new HBox(reset);
				HBox cancelbutton = new HBox(cancel);
				HBox calculatebutton = new HBox(calculate);
				combim10 = new VBox(10,calculatebutton,confirmbutton,resetbutton,cancelbutton);
				bottommenu = new HBox(10,setmenu,combim10);
					
				bottommenu.setPadding(new Insets(20));
				setfinalvbox.getChildren().add(bottommenu);
				setfinalvbox.getChildren().add(combim10);
			}
		
			else {
			
				alert=new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("Verify if the empid exists first!");
				alert.showAndWait();
				
			}
			
		}
		
	}
	//uses the gross, tax, and net classes to do all calculations
	//will be used in 2 situations
	// 1) when the manager clicks on the calculate button and
	// 2} when the pay info will be updated in the Employees Table (edit confirm button)
	public void GenerateGrossTaxNet() {
		
		grosspay = new GrossPay();
		//netpay = new NetPay();
		
		//going to ignore raisesalary for now, my intention is to make the program use the 
		//raised salary of the raisedate is today or in the past
		//local vars for an easier time
		Double salary, hours, othours, bonus, raisesalary;
		salary = Double.parseDouble(cin_salary.getText());
		hours = Double.parseDouble(cin_hours.getText());
		othours = Double.parseDouble(cin_overtimehours.getText());
		bonus = Double.parseDouble(cin_bonuses.getText());
		raisesalary = Double.parseDouble(cin_raisesalary.getText());
		
		grosspay.setSalary(salary);
		grosspay.setHours(hours);
		grosspay.setOvertimeHours(othours);
		grosspay.setBonuses(bonus);
		grosspay.isFullTime();
		grosspay.setRaisedSalary(raisesalary);
		
		//calculates the methods used for calculating grosspay
		grosspay.calcTotalWages();
		grosspay.calcOvertimeWages();
		grosspay.calcOtherWages();
		grosspay.calcRaiseTotal();
		grosspay.calcRaiseOvertimeWages();
		
		//calculating the grosspay
		grosspay.calcGrossPay();

		//initializing tax, setting the marital status, and calculating the total deduction
		tax = new Tax(grosspay);
		tax.setstatus(Integer.parseInt(SearchResult.get(11)));
		tax.cal_taxdeduct();
	
		//initializing netpay with references to grosspay and tax objects and calculating netpay
		netpay = new NetPay(grosspay, tax);
		netpay.calnet_pay();
	}
	
	
	//reset
	class ResetHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			cin_lastname.setText("");
			cin_salary.setText("");
			cin_hours.setText("");
			cin_overtimehours.setText("");
			cin_lastpaydate.setValue(null);
			cin_bonuses.setText("");
			cin_raisesalary.setText("");
			cin_raisereason.setText("");
			cin_raisedate.setAccessibleText("");
			cin_raisedate.setValue(null);
			radio1.setSelected(true);
			radio1.setSelected(false);
					
		}
	}
	
	//will calculate gross, net, and tax for manager to see before commiting any changes
	class CalculationHandler implements EventHandler<ActionEvent> {
	
		@Override
		public void handle (ActionEvent arg0) {
		
			//Get text from gross pay text box
			//parse that into double and pass the value with type Double
			//GrossPay Tax and NetPay will be skeleton code that when the appropriate methods are called, will have a final Double value
			//GP and Tax will have methods that will return their respective values of type Double
			//Using those two values, they will be passed as parameters to the NetPay class, which will have a method that will return the netpay
			//Only GP will be input by user, other two will be calculated when the calculate button is clicked (using Calculation handler)
			
			GenerateGrossTaxNet();
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Calculated Values");
			alert.setHeaderText(null);
			alert.setContentText("Approximated Values:");
			
			Label annualgross = new Label("Annual Gross Pay: ");
			TextField cin_annualgross = new TextField();
			cin_annualgross.setText(String.valueOf(grosspay.getGrossPay()));
			cin_annualgross.setVisible(true);
			cin_annualgross.setDisable(true);
			HBox gross = new HBox(10,annualgross,cin_annualgross);
			
			Label annualtax = new Label("Annual Tax Deduction: ");
			TextField cin_annualtax = new TextField();
			cin_annualtax.setText(String.valueOf(tax.get_taxdeduct()));
			cin_annualtax.setVisible(true);
			cin_annualtax.setDisable(true);
			HBox tax = new HBox(10,annualtax,cin_annualtax);
			
			Label annualnet = new Label("Annual Net Pay: ");
			TextField cin_annualnet = new TextField();
			cin_annualnet.setText(String.valueOf(netpay.getnet_pay()));
			cin_annualnet.setVisible(true);
			cin_annualnet.setDisable(true);
			HBox net = new HBox(10,annualnet,cin_annualnet);
										
			VBox infobox = new VBox(10,gross, tax, net);
			alert.getDialogPane().setContent(infobox);
			alert.showAndWait();
		}
		
	}
	
	//Confirm actions.
	class ConfirmHandler implements EventHandler<ActionEvent> {
				
		@Override
		public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//if the require information is missing, throw alert to tell the users.
			if(/*||cin_grosspay.getText().isEmpty()*/cin_hours.getText().isEmpty()||cin_overtimehours.getText().isEmpty()||cin_lastpaydate.getValue() == null||cin_bonuses.getText().isEmpty()||cin_raisesalary.getText().isEmpty()||cin_raisereason.getText().isEmpty()||cin_raisedate.getValue()==null)
			{
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Some of the require information is missing! Please check carefully!");
				alert.showAndWait();
			}
			//missing status information.
			else if((radio1.isSelected()==false)&&(radio2.isSelected()==false))
			{
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("What type of employee is this? Choose one!");
				alert.showAndWait();
			}
			//if these require information is all good, then we run these, we haven't really check what is the user enter the correct format yet.
			else
			{
				boolean finderror = false; 
				String holdbenefits;
				double holdgrosspay = 0,holdtax=0,holdnetpay=0;
				int holdstatus;
				alert=new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText(null);
					
				//check if last name is valid.
				if(finderror==false)
				{	
					for(int i = 0;i<cin_lastname.getText().length();i++)
					{
						if(Character.isDigit(cin_lastname.getText().charAt(i)))
						{
							alert.setContentText("Last name contains a number!");
							alert.showAndWait();
							finderror=true;
							break;
						}
					}
				}
										
				//checking cin_hours for errors
				if(finderror == false) {
									
					//check if there are more than 4 characters/digits
					if(cin_hours.getText().length() > 4) {
						alert.setContentText("Invalid number of hours. Incorrect format!");
						alert.showAndWait();
						finderror = true;
					}
					//check if hours > 40
					if(Double.parseDouble(cin_hours.getText()) > 40.00) {
						alert.setContentText("Invalid number of hours! After 40 hours, it's overtime!");
						alert.showAndWait();
						finderror = true;
					}
					//check for period/decimal
					if(cin_hours.getText().charAt(cin_hours.getText().length() - 2) != '.') {
						alert.setContentText("Missing a period in hours");
						alert.showAndWait();
						finderror = true;
					}
					//if previous conditions pass, we check if all the values are numbers, not anything else
					else {
								
						for(int i = 0; i < cin_hours.getText().length(); i++) {
								
							if(i!=cin_hours.getText().length() - 2) {
									
								if(!Character.isDigit(cin_hours.getText().charAt(i))) {
											
									alert.setContentText("Invalid amount, contains a non-digit character! (in hours)");
									alert.showAndWait();
									finderror = true;
									break;
										
								}
							}
						}	
					}
				}
		
				//checking overtime hours
				if(finderror == false) {
							
					//check if there are more than 4 character/digits
					if(cin_overtimehours.getText().length() > 4) {
						alert.setContentText("Invalid number of hours! Incorrect format");
						alert.showAndWait();
						finderror = true;
					}
					//check if more hours > 20, this is just a bit of fun I was having lol
					if(Double.parseDouble(cin_overtimehours.getText()) > 20.0) {
						alert.setContentText("Stop overworking your employees!");
						alert.showAndWait();
						finderror = true;
					}
					//check for period/decimal
					else if(cin_overtimehours.getText().charAt(cin_overtimehours.getText().length() - 2) != '.') {
						alert.setContentText("Missing a period in overtime hours");
						alert.showAndWait();
						finderror = true;
					}
					//if previous conditions pass, we check if all the values are numbers, not anything else
					else {
								
						for(int i = 0; i < cin_overtimehours.getText().length(); i++) {
								
							if(i!=cin_overtimehours.getText().length() - 2)	
									
								if(!Character.isDigit(cin_overtimehours.getText().charAt(i))) {
									alert.setContentText("Invalid character input! (in overtimehours)");
									alert.showAndWait();
									finderror = true;
									break;
								}
						}								
					}
				}
				//checking last pay date, just copied this lol
				if(finderror == false) {
					if(cin_lastpaydate.getValue().compareTo(java.time.LocalDate.now()) >= 0) {
						alert.setContentText("Please check the last pay date!");
						alert.showAndWait();
						finderror=true;
					}
				}
				//checking bonuses
				if(finderror == false) {
						
					if(cin_bonuses.getText().charAt(cin_bonuses.getText().length() - 2) != '.') {
						alert.setContentText("Missing a period in bonuses");
						alert.showAndWait();
						finderror = true;
					}
					else {
								
						for(int i = 0; i < cin_bonuses.getText().length(); i++) {
								
							if(i!=cin_bonuses.getText().length() - 2)	
									
								if(!Character.isDigit(cin_bonuses.getText().charAt(i))) {
									alert.setContentText("Invalid character input! (in bonuses)");
									alert.showAndWait();
									finderror = true;
									break;
								}
						}				
							
					}
					
				}
						
				//checking raisesalary
				if(finderror == false) {
							
					if(cin_raisesalary.getText().charAt(cin_raisesalary.getText().length() - 2) != '.') {
						alert.setContentText("Missing a period in raise salary");
						alert.showAndWait();
						finderror = true;
					}
					else {
								
						for(int i = 0; i < cin_raisesalary.getText().length(); i++) {
									
							if(i!=cin_raisesalary.getText().length() - 2)	
									
								if(!Character.isDigit(cin_raisesalary.getText().charAt(i))) {
									alert.setContentText("Invalid character input!");
									alert.showAndWait();
									finderror = true;
									break;
								}
						}					
					}
				}
						
				//checking raisereason
				if(finderror == false) {
							
					//need to come up with some protection here, its just accepting 
					//anything, which is no bueno
							
				}
				//checking raise date, just copied this lol
				if(finderror == false) {
					if(cin_raisedate.getValue().compareTo(java.time.LocalDate.now()) >= 0) {
						alert.setContentText("Please check the raise date!");
						alert.showAndWait();
						finderror=true;
					}
				}
						
				//if nothing goes wrong, then we can start to do our stuff.
				if(finderror == false)
				{
								
					alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Manager Password Required");
					alert.setHeaderText("Please enter the manager password");

					Label username = new Label("Manager Id: ");
					TextField cin_username = new TextField();
					HBox usernametextf = new HBox(10,username,cin_username);
					
					Label pass = new Label("  	     Password:");
					PasswordField Password = new PasswordField();
					Label confirmpass = new Label("Confirm Password:");
					PasswordField confirmPassword = new PasswordField();
													
					HBox passwordtextf = new HBox(10,pass,Password);
					HBox confirmpasswordtextf = new HBox(10,confirmpass,confirmPassword);
					VBox username_pass = new VBox(10,usernametextf,passwordtextf, confirmpasswordtextf);
					Label passwordlabel = new Label("Placeholder text");
					VBox setpass = new VBox(10,username_pass,passwordlabel);
					
					alert.getDialogPane().setContent(setpass);
					ButtonType user_cancel = new ButtonType("Cancel");
					ButtonType user_confirm = new ButtonType("Confirm");
					alert.getButtonTypes().setAll(user_confirm,user_cancel);
					Optional<ButtonType> userchoice = alert.showAndWait();
					boolean iscancel = false;
					if(userchoice.get()==user_confirm)
					{
						int idCreator = 0;
						Label errorlabel = new Label("*Please check the password.");
						errorlabel.setTextFill(Color.web("#FF76a3"));
						passwordtextf.getChildren().add(errorlabel);
						
						SetManagerVerify(Integer.parseInt(cin_username.getText()), Password.getText());
						while(Password.getText().isEmpty() || Password.getText().length()<6 || ManagerVerify(Integer.parseInt(cin_username.getText()), encryptThisString(Password.getText()), encryptThisString(confirmPassword.getText())) == false)
						{
							Password.setStyle("-fx-border-color: pink;");
							userchoice=alert.showAndWait();
							iscancel=false;
						}
								
						if(viewtype == 0) {
								
								
							if(iscancel==false)
							{
								if(radio1.isSelected())
									holdstatus = 1;
								else
									holdstatus = 0;
								try
								{
								
									//String birthdayString =fmt.format(cin_birthday.getValue());
									//cin_firstname.setText(cin_firstname.getText().trim());
									cin_lastname.setText(cin_lastname.getText().trim());
									cin_hours.setText(cin_hours.getText().trim());
									cin_overtimehours.setText(cin_overtimehours.getText().trim());
									
											
									//at this point we are ready to add the new employee info to the database
									if(GetConnectionStatus() == false) {
											
										System.out.println("not connected to database, error here");
											
									}
										
									else if (GetConnectionStatus() == true) {
											
										//for when we are adding a new record to the GrossPayInfo table (add)
										if(GetConfirmType() == 1) {
										
											EmployeeAddGross(Integer.parseInt(cin_search.getText().trim()), holdstatus);
										
										}
										//for when we are updating a record in GrossPayInfo table (edit)
										else if(GetConfirmType() == 0) {
											
											//can be changed to not include fulltime status passed as a parameter
											UpdateGrossTable(Integer.parseInt(cin_search.getText().trim()), holdstatus);
											
										}
									
										
										GenerateGrossTaxNet();
										//this will update the EmployeesTable with the gross, tax, and net
										UpdateEmployeesTable(Integer.parseInt(cin_search.getText().trim()), grosspay.getGrossPay(), tax.get_taxdeduct(), netpay.getnet_pay());
									
									}
											
								} catch(Exception e) {
												
									alert=new Alert(AlertType.ERROR);
									alert.setTitle("Error");
									alert.setHeaderText(null);
									alert.setContentText("Something bad happened!");
									alert.showAndWait();
								}
										
								alert=new Alert(AlertType.INFORMATION);
								alert.setTitle("Information");
								alert.setHeaderText(null);
								alert.setContentText("You added a new person."+"His/Her id is to be determined: "/*+idCreator*/); //the id here would require a select query as it is auto generated in MySql
								alert.showAndWait();
								search.setDisable(false);
								options.setDisable(false);
								setfinalvbox.getChildren().remove(bottommenu);
								setfinalvbox.getChildren().remove(combim10);
							}
						}
						//if the user click cancel, we do nothing.
						else if(viewtype == 1)
						{
							viewtype=0;
						}
								
								
					}
					else if(userchoice.get() == user_cancel) {
						
					}
				}
					
			}
					
		}
				
	}
			
	//cancel
	class CancelHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			search.setDisable(false);
			options.setDisable(false);
			cin_search.setDisable(false);
			add.setDisable(false);
			setfinalvbox.getChildren().remove(combim10);
			setfinalvbox.getChildren().remove(bottommenu);
			number = 0;
			
			//reset everything, so the user must again verify that the employee exists with the search feature
			SetInEmployeesTable(false);
			SetInGrossTable(false);
			SearchResult.clear();
			SearchResult2.clear();
			
		}
	}
			
			
	//edit		
	class EditHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			search.setDisable(true);
			cin_lastname.setDisable(false);
			cin_salary.setDisable(false);
			cin_hours.setDisable(false);
			cin_overtimehours.setDisable(false);
			cin_lastpaydate.setDisable(false);
			cin_bonuses.setDisable(false);
			cin_raisesalary.setDisable(false);
			cin_raisereason.setDisable(false);
			cin_raisedate.setDisable(false);
			radio1.setDisable(false);
			radio2.setDisable(false);
			editconfirm.setVisible(true);
			edit.setVisible(false);
			calculate.setVisible(true);
			viewtype=1;
			System.out.println(cin_lastname.getText());
		}
				
	}	
	
	//make text fields.
	@SuppressWarnings("unlikely-arg-type")
	public void makemenu(int condition)
	{
		cin_lastname = new TextField();
		Label l_label = new Label("      *Last name:");
		HBox lname = new HBox(5,l_label,cin_lastname);
		cin_lastname.setDisable(true);
		lname.setDisable(true);
		lname.setDisable(true);
		
		if(condition == 1) {	
			cin_lastname.setVisible(false);
			cin_lastname.setDisable(true);
			l_label.setDisable(true);
			l_label.setVisible(false);
			lname.setDisable(true);
			lname.setVisible(false);
		}
		
		cin_salary = new TextField();
		Label salary_label = new Label("	*Wage (per hour):");
		HBox salary = new HBox(5,salary_label,cin_salary);
		
		cin_hours = new TextField();
		Label hours_label = new Label("     *Hours:");
		HBox hours = new HBox(5,hours_label,cin_hours);
		
		cin_overtimehours = new TextField();
		Label overtimehours_label = new Label("              *Overtime Hours:");
		HBox othours = new HBox(5,overtimehours_label,cin_overtimehours);
		
		cin_lastpaydate = new DatePicker();
		cin_lastpaydate.setPrefWidth(162.5);
		Label last_date_label = new Label("         *Last Pay Date:");
		HBox lastdate = new HBox(5,last_date_label,cin_lastpaydate);
		
		cin_bonuses = new TextField();
		Label bonuses_label = new Label("   *Bonuses:");
		HBox bonuses = new HBox(5,bonuses_label,cin_bonuses);
		
		cin_raisesalary = new TextField();
		Label raisesalary_label = new Label("   *Raise Wage (per hour):");
		HBox raisesalary = new HBox(5,raisesalary_label,cin_raisesalary);
		
		cin_raisereason = new TextField();
		Label raisereason_label = new Label(" *Raise Reason:");
		HBox raisereason = new HBox(5,raisereason_label,cin_raisereason);
		
		cin_raisedate = new DatePicker();
		cin_raisedate.setPrefWidth(162.5);
		Label date_label = new Label("         *Raise Date:");
		HBox raisedate = new HBox(5,date_label,cin_raisedate);
		
		HBox combim0 = new HBox(10, lname);

		HBox combim1 = new HBox(5,salary, hours);
		VBox combim2 = new VBox(5, combim0, combim1);
				
		HBox combim3 = new HBox(5,othours,raisesalary);
		VBox combim4 = new VBox(5,combim2,combim3);
		
		HBox combim5 = new HBox(5, lastdate, bonuses);
		VBox combim6 = new VBox(5, combim4, combim5);
		
		HBox combim7 = new HBox(5, raisereason, raisedate);
		VBox combim8 = new VBox(5, combim6, combim7);
		
		radio1 = new RadioButton("Full Time");
		radio2 = new RadioButton("Part Time");
										
		radioGroup = new ToggleGroup();
		radio1.setToggleGroup(radioGroup);
		radio2.setToggleGroup(radioGroup);
		
		VBox radiobuttonvbox = new VBox();
		radiobuttonvbox.getChildren().add(radio1);
		radiobuttonvbox.getChildren().add(radio2);
		VBox radiobutton = new VBox(radiobuttonvbox);
		HBox add1 = new HBox(10,combim8,radiobutton);
		
		menu = new VBox(5,add1);
		setmenu = new HBox(10,menu);
		setmenu = new HBox(10,add1,menu);
		
//		HBox combim1 = new HBox(5,lname,salary);
//		HBox combim2 = new HBox(5,hours,othours);
//		VBox combim3 = new VBox(5,combim1,combim2);
//		
//		HBox combim4 = new HBox(5, lastdate, bonuses);
//		VBox combim5 = new VBox(5, combim3, combim4);
//		
//		HBox combim6 = new HBox(5, raisesalary, raisereason);
//		VBox combim7 = new VBox(5, combim5, combim6);
//		
//		HBox combim8 = new HBox(5, raisedate);
//		VBox combim9 = new VBox(5, combim7, combim8);
//		
//		radio1 = new RadioButton("Full Time");
//		radio2 = new RadioButton("Part Time");
//										
//		radioGroup = new ToggleGroup();
//		radio1.setToggleGroup(radioGroup);
//		radio2.setToggleGroup(radioGroup);
//		
//		VBox radiobuttonvbox = new VBox();
//		radiobuttonvbox.getChildren().add(radio1);
//		radiobuttonvbox.getChildren().add(radio2);
//		VBox radiobutton = new VBox(radiobuttonvbox);
//		HBox add1 = new HBox(10,combim9,radiobutton);
//		
//		menu = new VBox(5,add1);
//		setmenu = new HBox(10,menu);
//		setmenu = new HBox(10,add1,menu);
	}
			
	public void UpdateEmployeesTable(Integer employeeId, Double gross, Double tax, Double net) {
			
		System.out.println("Connection boolean value: " + GetConnectionStatus().toString());
		
		String updateQuery = "UPDATE Employees SET gross_pay = ?, tax_deduct = ?, net_pay = ? WHERE empid = ? AND lname = ?";
		connection.SetQuery(updateQuery);
		
		try(PreparedStatement updateStatement = connection.GetConnection().prepareStatement(connection.GetQuery())) {
			
			connection.GetConnection().setAutoCommit(false);
			updateStatement.setDouble(1, gross);
			updateStatement.setDouble(2, tax);
			updateStatement.setDouble(3, net);
			updateStatement.setInt(4, employeeId);
			updateStatement.setString(5, SearchResult.get(2));
			connection.SetPreparedStatement(updateStatement);
			connection.RunPreparedQuery(1); //sets the result in ResultSet
			connection.GetConnection().commit();		
			
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

	//have to replace the search result 2 with cin_fields, only use search results to fill textfields
	//with results after a search method is run
	//used when there already exists a record of pay info in the gross table, no need to update the empid
	public void UpdateGrossTable(Integer employeeId, Integer status) {
		
		System.out.println("Connection boolean value: " + GetConnectionStatus().toString());
		
		String updateQuery = "UPDATE GrossPayInfo SET salary = ?, hours = ?, overtimeHours = ?, lastPayDate = ?, bonuses = ?, raiseSalary = ?, raiseReason = ?, raiseEffectiveDate = ?, full_time = ? WHERE empid = ?";
		connection.SetQuery(updateQuery);
		
		try(PreparedStatement updateStatement = connection.GetConnection().prepareStatement(connection.GetQuery())) {
			
			connection.GetConnection().setAutoCommit(false);
			updateStatement.setDouble(1, Double.parseDouble(cin_salary.getText()));
			updateStatement.setDouble(2, Double.parseDouble(cin_hours.getText()));
			updateStatement.setDouble(3, Double.parseDouble(cin_overtimehours.getText()));
			String lastdate = cin_lastpaydate.getValue().toString();
			updateStatement.setString(4, lastdate);
			updateStatement.setDouble(5, Double.parseDouble(cin_bonuses.getText()));
			updateStatement.setDouble(6, Double.parseDouble(cin_raisesalary.getText()));
			updateStatement.setString(7, cin_raisereason.getText());
			String raisedate = cin_raisedate.getValue().toString();
			updateStatement.setString(8, raisedate);
			updateStatement.setInt(9, status);
			connection.SetPreparedStatement(updateStatement);
			connection.RunPreparedQuery(1); //sets the result in ResultSet
			connection.GetConnection().commit();
			
			
			
		} catch(SQLException errors) {
			
			errors.printStackTrace();

		}
		
	}
	
	public void EmployeeSearch(Integer employeeId) {
		
		System.out.println("Connection boolean value: " + GetConnectionStatus().toString());
		
		String searchQuery = "SELECT * FROM Employees WHERE empid = ?";
		connection.SetQuery(searchQuery);
		
		try(PreparedStatement searchStatement = connection.GetConnection().prepareStatement(connection.GetQuery())) {
			
			connection.GetConnection().setAutoCommit(false);
			searchStatement.setInt(1, employeeId);
			connection.SetPreparedStatement(searchStatement);
			connection.RunPreparedQuery(0); //sets the result in ResultSet
			connection.GetConnection().commit();
			
			System.out.println("Testing from within EmployeeSearch method...");
			while (connection.GetResultSet().next()) {
			
				System.out.println("empId: " + connection.GetResultSet().getInt("empId") + "\nlname: " + connection.GetResultSet().getString("lname"));
				
				SearchResult.add(connection.GetResultSet().getString("empId"));
				SearchResult.add(connection.GetResultSet().getString("fname"));
				System.out.println("fname: " + SearchResult.get(1));
				SearchResult.add(connection.GetResultSet().getString("lname"));
				System.out.println("lname: " + SearchResult.get(2));
				SearchResult.add(connection.GetResultSet().getString("birth_date"));
				SearchResult.add(connection.GetResultSet().getString("address"));
				SearchResult.add(connection.GetResultSet().getString("email"));
				SearchResult.add(connection.GetResultSet().getString("phone_num"));
				SearchResult.add(connection.GetResultSet().getString("emergency_num"));
				SearchResult.add(connection.GetResultSet().getString("ssn"));
				SearchResult.add(connection.GetResultSet().getString("pwd"));
				SearchResult.add(connection.GetResultSet().getString("benefit_code"));
				SearchResult.add(String.valueOf(connection.GetResultSet().getInt("marital_status")));
				SearchResult.add(String.valueOf(connection.GetResultSet().getDouble("gross_pay")));
				SearchResult.add(String.valueOf(connection.GetResultSet().getDouble("tax_deduct")));
				SearchResult.add(String.valueOf(connection.GetResultSet().getDouble("net_pay")));
				
			}
			
		} catch(SQLException errors) {
			
			errors.printStackTrace();

		}
		
	}
	//adds employee info to the GrossPayInfo table
	public void EmployeeAddGross(Integer employeeId, Integer status) {
		
		System.out.println("Connection boolean value: " + GetConnectionStatus().toString());
		
		String addQuery = "INSERT INTO GrossPayInfo (empId, lname, salary, hours, overtimeHours, lastPayDate, bonuses, raiseSalary, raiseReason, raiseEffectiveDate, full_time) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		connection.SetQuery(addQuery);
		
		try(PreparedStatement addStatement = connection.GetConnection().prepareStatement(connection.GetQuery())) {
			
			connection.GetConnection().setAutoCommit(false);
			addStatement.setInt(1, employeeId);
			addStatement.setString(2, SearchResult.get(2));
			addStatement.setDouble(3, Double.parseDouble(cin_salary.getText()));
			addStatement.setDouble(4, Double.parseDouble(cin_hours.getText()));
			addStatement.setDouble(5, Double.parseDouble(cin_overtimehours.getText()));
			String lastdate = cin_lastpaydate.getValue().toString();
			addStatement.setString(6, lastdate);
			addStatement.setDouble(7, Double.parseDouble(cin_bonuses.getText()));
			addStatement.setDouble(8, Double.parseDouble(cin_raisesalary.getText()));
			addStatement.setString(9, cin_raisereason.getText());
			String raisedate = cin_raisedate.getValue().toString();
			addStatement.setString(10, raisedate);
			//Integer fulltime;
			addStatement.setInt(11, status);
			connection.SetPreparedStatement(addStatement);
			connection.RunPreparedQuery(1); //sets the result in ResultSet
			connection.GetConnection().commit();
			
			
			
		} catch(SQLException errors) {
			
			errors.printStackTrace();

		}		
		
	}
	
	//searches the GrossPayInfo table by empid
	public void EmployeeSearchGross(Integer employeeId) {
		
		System.out.println("Connection boolean value: " + GetConnectionStatus().toString());
		
		String searchQuery = "SELECT * FROM GrossPayInfo WHERE empid = ?";
		connection.SetQuery(searchQuery);
		
		try(PreparedStatement searchStatement = connection.GetConnection().prepareStatement(connection.GetQuery())) {
			
			connection.GetConnection().setAutoCommit(false);
			searchStatement.setInt(1, employeeId);
			connection.SetPreparedStatement(searchStatement);
			connection.RunPreparedQuery(0); //sets the result in ResultSet
			connection.GetConnection().commit();
			
			System.out.println("Testing from within EmployeeSearchGross method...");
			while (connection.GetResultSet().next()) {
			
				System.out.println("empId: " + connection.GetResultSet().getInt("empId") + "\nlname: " + connection.GetResultSet().getString("lname"));
				
				SearchResult2.add(connection.GetResultSet().getString("empId"));
				SearchResult2.add(connection.GetResultSet().getString("lname"));
				SearchResult2.add(String.valueOf(connection.GetResultSet().getDouble("salary")));
				SearchResult2.add(String.valueOf(connection.GetResultSet().getDouble("hours")));
				SearchResult2.add(String.valueOf(connection.GetResultSet().getDouble("overtimeHours")));
				SearchResult2.add(connection.GetResultSet().getString("lastPayDate"));
				SearchResult2.add(String.valueOf(connection.GetResultSet().getDouble("bonuses")));
				SearchResult2.add(String.valueOf(connection.GetResultSet().getDouble("raiseSalary")));
				SearchResult2.add(connection.GetResultSet().getString("raiseReason"));
				SearchResult2.add(connection.GetResultSet().getString("raiseEffectiveDate"));
				
			}
			
		} catch(SQLException errors) {
			
			errors.printStackTrace();

		}
		
	}
	
	//set disable
			public void setdisable()
			{
				options.setDisable(true);
				add.setDisable(true);
				search.setDisable(true);
				cin_search.setDisable(true);
				cin_lastname.setDisable(true);
				cin_salary.setDisable(true);
				cin_hours.setDisable(true);
				cin_overtimehours.setDisable(true);
				cin_lastpaydate.setDisable(true);
				cin_bonuses.setDisable(true);
				cin_raisesalary.setDisable(true);
				cin_raisereason.setDisable(true);
				cin_raisedate.setDisable(true);
				radio1.setDisable(true);
				radio2.setDisable(true);
			}
	
	//will implement at another time, not enough time for this class
	/*
	public void SetUpFormats() {
		
		hoursFormat = NumberFormat.getNumberInstance();
		hoursFormat.setMaximumFractionDigits(2);
		
		bonusesFormat = NumberFormat.getNumberInstance();
		bonusesFormat.setMaximumFractionDigits(2);
		
		raisesalaryFormat = NumberFormat.getNumberInstance();
		raisesalaryFormat.setMaximumFractionDigits(2);
	}*/ 
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
			
			
			
			//method that will initialize VerifyResults so we can use that arraylist for verifying changes
			public void SetManagerVerify(Integer mid, String pass) {
							
				String searchQuery = "SELECT managerId, pwd FROM Managers WHERE pwd = ? AND managerId = ?";
				connection.SetQuery(searchQuery);
				
				try(PreparedStatement searchStatement = connection.GetConnection().prepareStatement(connection.GetQuery())){
					
					connection.GetConnection().setAutoCommit(false);
					searchStatement.setString(1, encryptThisString(pass));
					searchStatement.setInt(2, mid);
					connection.SetPreparedStatement(searchStatement);
					connection.RunPreparedQuery(0); //sets the result in ResultSet
					connection.GetConnection().commit();
					
					System.out.println("Testing from within EmployeeSearch method...");
					while (connection.GetResultSet().next()) {
					
						ManagerVerify.add(connection.GetResultSet().getString("managerId"));
						ManagerVerify.add(encryptThisString(connection.GetResultSet().getString("pwd")));
					}
					
					if(ManagerVerify.size() < 2) {
						ManagerVerify.add("wrong");
						ManagerVerify.add("wrong");
					}
					
				} catch(SQLException errors) {
					
					errors.printStackTrace();
					
				}
				
			}
			
			//when making a change, run this method to verify manager password
			//use simple SELECT query
			public Boolean ManagerVerify(Integer manager_id, String manager_pwd, String manager_pwd2) {
			
				System.out.println(manager_id);
				System.out.println(manager_pwd);
				System.out.println(ManagerVerify.get(0));
				System.out.println(ManagerVerify.get(1));
				
				if(ManagerVerify.get(0).equals(manager_id.toString()) && ManagerVerify.get(1).equals(encryptThisString(manager_pwd)) && ManagerVerify.get(1).equals(encryptThisString(manager_pwd))) {
					System.out.println("Manager verified!");
					return true;
				}
				else {
					System.out.println("Manager verification error!");
					return false;
				}
				
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
			
	public void SetConnectionStatus(Boolean connStat) {
		this.connectionStatus = connStat;
	}
			
	public Boolean GetConnectionStatus() {
		return connectionStatus;
	}
	
}
