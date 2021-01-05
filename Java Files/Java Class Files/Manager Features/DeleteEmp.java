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
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 
import javafx.fxml.Initializable;

public class DeleteEmp{
							
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
		
		protected DeleteEmp() {
			
		}
		
		public void start(Stage window) throws Exception{
			search = new TextField();
			search.setPromptText("Search using ID");
			search.setFocusTraversable(false);
			searchbutton= new Button("Search");
			searchbutton.setOnAction(new SearchHandler());
			search.setPrefWidth(300);
			topmenu = new HBox(search,searchbutton);
			topmenu.setAlignment(Pos.CENTER);
			setmenu.getChildren().add(topmenu);
			scene = new Scene(setmenu,length,weight);
			window.setScene(scene);
			window.setTitle("Delete the D");
			window.show();
		}

		class SearchHandler implements EventHandler<ActionEvent>
		{

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				topmenu.setAlignment(Pos.TOP_LEFT);
				if(search.getText().isEmpty())
				{
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("You did not enter anything.");
					alert.showAndWait();
				}
				else
				{
					try {
						connect = DriverManager.getConnection(databaseLocation, root, password);
						String queue = "Select * FROM Employees LEFT JOIN GrossPayInfo on Employees.empid = GrossPayInfo.empid where Employees.empId = "+search.getText();
						pst = connect.prepareStatement(queue);
						ResultSet rs = pst.executeQuery();
						if(rs.next()==false)
						{
							alert = new Alert(AlertType.WARNING);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("Id "+search.getText()+" does not exist.");
							if(botmenu.getChildren().isEmpty()==false)
							{
								setmenu.getChildren().remove(botmenu);
								botmenu.getChildren().remove(completeform);
								botmenu.getChildren().remove(buttons);
							}
							alert.showAndWait();
						}
						else
						{
								if(botmenu.getChildren().isEmpty()==false)
								{
									setmenu.getChildren().remove(botmenu);
									botmenu.getChildren().remove(completeform);
									botmenu.getChildren().remove(buttons);
								}
								makeobject();
								cin_firstname.setText(rs.getString("fname"));
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
								setdisable();
								if(deceased.isSelected()==false)
								{
								edit = new Button("Edit");
								edit.setOnAction(new EditHandler());
								buttons = new HBox(edit);
								buttons.setPadding(new Insets(20));
								botmenu.getChildren().add(buttons);
								}
								setmenu.getChildren().add(botmenu);
								
						}
				}catch(Exception e)
					{
					e.printStackTrace();
					}
			}
			}
		}

class EditHandler implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		set_notdisable();
		search.setDisable(true);
		searchbutton.setDisable(true);
		buttons.getChildren().remove(edit);
		confirm = new Button("Confrim");
		confirm.setOnAction(new ConfirmHandler());
		cancel = new Button("Cancel");
		cancel.setOnAction(new CancelHandler());
		VBox button = new VBox(10,confirm,cancel);
		buttons.getChildren().add(button);
	}
}
class CancelHandler implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		setmenu.getChildren().remove(botmenu);
		botmenu.getChildren().remove(buttons);
		botmenu.getChildren().remove(completeform);
		search.setDisable(false);
		searchbutton.setDisable(false);
	}
	
}
class ConfirmHandler implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String workstatus = null;
		if(workingstatus==0)
		workstatus="Active";
		else if(workingstatus==1)
		workstatus = "Terminated";
		else if(workingstatus==3)
		workstatus = "disable";
	checkempty();
	if(isempty==false)
		finderrors();
	if(finderror==false)
	{
		if(deceased.isSelected())
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("You changed his working status from "+workstatus+" to "+deceased.getText()+".\nAre you sure?");
		Optional<ButtonType> result = alert.showAndWait();
			if(result.get()==ButtonType.OK)
			{
				updatedata();
			}
		}
		else
		updatedata();
	}
	}
	
}
public void set_notdisable()
{
	cin_firstname.setDisable(false);
	cin_lastname.setDisable(false);
	cin_address.setDisable(false);
	cin_email.setDisable(false);
	cin_phonenum1.setDisable(false);
	cin_phonenum2.setDisable(false);
	if(ismanager==true)
	{
	radio1.setDisable(false);
	radio2.setDisable(false);
	radio3.setDisable(false);
	radio4.setDisable(false);
	active.setDisable(false);
	terminated.setDisable(false);
	deceased.setDisable(false);
	disable.setDisable(false);
	}
}
public void updatedata()
{
	//holdms = hold marital stauts, holdws = hold working status.
	
	
	//1 means single, 2 means married filing jointly, 3 means married filing separately, 4 means head of household.
	int holdms,holdws;
	if(radio1.isSelected())
		holdms=1;
	else if(radio2.isSelected())
		holdms=2;
	else if(radio3.isSelected())
		holdms=3;
	else
		holdms=4;
	
	//this is my bad. but I will just go for it. 1 means active, 2 means terminated, 3 means deceased, 4 means disable.
	if(active.isSelected())
		holdws = 0;
	else if(terminated.isSelected())
		holdws = 1;
	else if(deceased.isSelected())
		holdws = 2;
	else
		holdws=3;
	
	try {
		//update it to employees table.
		String queue = "UPDATE Employees SET fname= ? , lname= ? , address = ? , email = ? , phone_num=? , emergency_num = ? , marital_status = ? ,"+
						"working_status = ? WHERE empid = ?";
		pst = connect.prepareStatement(queue);
		pst.setString(1, cin_firstname.getText());
		pst.setString(2,cin_lastname.getText());
		pst.setString(3, cin_address.getText());
		pst.setString(4, cin_email.getText());
		pst.setString(5, cin_phonenum1.getText());
		pst.setString(6, cin_phonenum2.getText());
		pst.setInt(7, holdms);
		pst.setInt(8, holdws);
		pst.setString(9, search.getText());
		pst.executeUpdate();
		
		//because there is lname in the grosspayinfo table, so we also want to update that one.
		queue = "UPDATE grosspayinfo SET lname = ? WHERE empid = ?";
		pst=connect.prepareStatement(queue);
		pst.setString(1, cin_lastname.getText());
		pst.setString(2, search.getText());
		
		
		pst.close();
		
		
		alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Infromation");
		alert.setHeaderText(null);
		alert.setContentText("You updated "+search.getText()+"'s file succefully!");
		alert.showAndWait();
		cancel.fire();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Something bad happened!");
		alert.showAndWait();
	}
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
	
	botmenu.getChildren().add(completeform);
	
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
}
public void checkempty()
{
	isempty=false;
	if(cin_firstname.getText().isEmpty())
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("First name is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if(cin_lastname.getText().isEmpty()&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Last name is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if(cin_email.getText().isEmpty()&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Email is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if(cin_address.getText().isEmpty()&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Address is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if(cin_phonenum1.getText().isEmpty()&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Phone number is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if(cin_phonenum2.getText().isEmpty()&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Emergency number is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if(ismanager==true&&isempty==false)
	{
	if(cin_benefits.getText().isEmpty())
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Benefit code is empty.");
		isempty=true;
		alert.showAndWait();
	}
	if((radio1.isSelected()==false)&&(radio2.isSelected()==false)&&(radio3.isSelected()==false)&&(radio4.isSelected()==false)&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("You forgot to pick a staus!");
		isempty=true;
		alert.showAndWait();
	}
	if(active.isSelected()==false&&terminated.isSelected()==false&&deceased.isSelected()==false&&disable.isSelected()==false&&isempty==false)
	{
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("You forgot to pick a working staus!");
		isempty=true;
		alert.showAndWait();
	}
	}
	if(isempty==false)
	finderror=false;
}

public void finderrors()
{
	alert=new Alert(AlertType.WARNING);
	alert.setTitle("Error");
	alert.setHeaderText(null);
	//check if first name is valid.
		for(int i = 0;i<cin_firstname.getText().length();i++)
		{
			if(Character.isDigit(cin_firstname.getText().charAt(i)))
			{
				alert.setContentText("First name contains a number!");
				alert.showAndWait();
				finderror = true;
				break;
			}
		}
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
		//check if email is valid.
		if(finderror == false)
		{
			//length has to be bigger than 5. For example, the 1@.com is allowed!
			if(cin_email.getText().length()<=5)
			{
				alert.setContentText("Not a valid email!");
				alert.showAndWait();
				finderror=true;
			}
			//if length is good, then we check if the email ends with .com or .org or something else, we can add more.
			//we also check if the email contains a @.
			else
			{
				String email_ending = cin_email.getText().substring(cin_email.getText().length()-4, cin_email.getText().length());
				if((!(cin_email.getText().contains("@")))||(cin_email.getText().indexOf("@")>cin_email.getText().indexOf("."))||(!email_ending.equals(".com")&&!email_ending.equals(".org")))
				{
					alert.setContentText("Email does not contains @ or does not end with .com/.org ");
					alert.showAndWait();
					finderror=true;
				}
			}
		}
		// check if phone number is valid.
		if(finderror == false)
		{
			//check if the length of the phone number is 12.(we include the 2 dashes)
			if(cin_phonenum1.getText().length()!=12)
			{
				alert.setContentText("Invalid phone number, wrong number of characters!");
				alert.showAndWait();
				finderror=true;
			}
			//check if the dashes are at the correct positions or not.
			else if(cin_phonenum1.getText().charAt(3)!='-' || cin_phonenum1.getText().charAt(7)!='-')
			{
				alert.setContentText("Invalid phone number, dashes at wrong positions!");
				alert.showAndWait();
				finderror=true;
			}
			//nothing wrong with that, then we check if the phone number contains a character or not.
			else
			{
				for(int i = 0;i<cin_phonenum1.getText().length();i++)
				{
					if(i!=3&&i!=7)
					{
						if(!Character.isDigit(cin_phonenum1.getText().charAt(i)))
						{
							alert.setContentText("Invalid phone number, contains a character that is not a digit!");
							alert.showAndWait();
							finderror=true;
							break;
						}
					}
				}
			}
		}
		//check for phone number2 which is emergency contact number.
		if(finderror == false)
		{
			//check length.
			if(cin_phonenum2.getText().length()!=12)
			{
				alert.setContentText("Invalid emergency contact number, wrong number of characters!");
				alert.showAndWait();
				finderror=true;
			}
			//check dashes.
			else if(cin_phonenum2.getText().charAt(3)!='-' || cin_phonenum2.getText().charAt(7)!='-')
			{
				alert.setContentText("Invalid emergency contact number, dashes at wrong positions!");
				alert.showAndWait();
				finderror=true;
			}
			//check if the number contains characters.
			else
			{
				for(int i = 0;i<cin_phonenum2.getText().length();i++)
				{
					if(i!=3&&i!=7)
					{
						if(!Character.isDigit(cin_phonenum2.getText().charAt(i)))
						{
							alert.setContentText("Invalid emergency contact number, contains a character that is not a digit!");
							alert.showAndWait();
							finderror=true;
							break;
						}
					}
				}
			}
		}
}

}