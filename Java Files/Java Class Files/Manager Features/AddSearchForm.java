package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 

public class AddSearchForm {
							
		//change to your stuff
		//project is my db name, change it to yours.
		//and in line 148, 264, and 285. change your stuffs.
		String databaseLocation = "jdbc:mysql://localhost:3306/csc430";
		String root = "root";
		String password = "Bloodywolf1234!";
		PersonalInfo personinfo;
		ArrayList<Button> addbutton_list = new ArrayList<>();
		Button confrim = new Button();
		Button reset;
		Button cancel;
		Button delete;
		Benefits all;
		ComboBox<String> theCompany = new ComboBox<>();
		ComboBox<String> theArea = new ComboBox<>();
		ComboBox<String> thePackage = new ComboBox<>();
		ComboBox<String> theType = new ComboBox<>();
		TextField cin_firstname;
		TextField cin_lastname;
		TextField cin_SSN;
		DatePicker cin_birthday;
		TextField cin_benefits=new TextField();
		TextField cin_phonenum1;
		TextField cin_phonenum2;
		TextField cin_address;
		TextField cin_email;
		VBox finalvbox= new VBox();
		VBox setfinalvbox = new VBox();
		RadioButton radio1;
		RadioButton radio2;
		RadioButton radio3;
		RadioButton radio4;
		ToggleGroup radioGroup;
		Alert alert;
		HBox bcode;
		HBox bottommenu;
		HBox setmenu;
		int idCreator;
		int number = 0;
		int lenght = 1500, weight = 300;
		int holdstatus;
		boolean isempty,finderror;
		boolean isId = false;
		PasswordField Password = new PasswordField();
//		public static void main(String[] args) throws ParseException {
//			// TODO Auto-generated method stub
//			launch(args);
//			
//		}

		public void start(Stage primaryStage) throws Exception {
			// TODO Auto-generated method stub
			//first we check if the file exists or not, if it exists then we check if there is things in the file or not.
			number = 1;
			confrim = new Button("Confirm");
			confrim.setOnAction(new ConfrimHandler());
			reset = new Button("Reset");
			reset.setOnAction(new ResetHandler());
			cancel = new Button("Cancel");
			cancel.setOnAction(new CancelHandler());
			try {
				makeaddmenu();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HBox confirmbutton = new HBox(confrim);
			HBox resetbutton = new HBox(reset);
			HBox cancelbutton = new HBox(cancel);
			VBox combim10 = new VBox(10,confirmbutton,resetbutton,cancelbutton);
			bottommenu = new HBox(10,setmenu,combim10);
			
			bottommenu.setPadding(new Insets(20));
			setfinalvbox.getChildren().add(bottommenu);
			Scene scene = new Scene(setfinalvbox,lenght,weight);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Add/Search For An Employee");
			primaryStage.show();
			
		}
		//Confirm actions.
	class ConfrimHandler implements EventHandler<ActionEvent>
	{

		public void handle(ActionEvent arg0) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
//				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Connection connect;
			PreparedStatement pst;
			isempty=false;
			
			checkempty();
			
			//if these require information is all good, then we run these, we haven't really check what is the user enter the correct format yet.
			if(isempty == false)
			{
				finderrors();
					
				if(finderror == false) {
				
					try {
					
						connect = DriverManager.getConnection(databaseLocation, root, password);
						pst = connect.prepareStatement("Select empId FROM Employees");
						ResultSet rs = pst.executeQuery();
							
						Random rand = new Random();
						int unique;
						ArrayList <Integer> list = new ArrayList<>();
						while(rs.next())
						{
							list.add(rs.getInt(1));
						}
						
						do 
						{
							unique = 0;
							idCreator = (rand.nextInt(9)+1)*100000;
							idCreator += rand.nextInt(10)*10000;
							idCreator += rand.nextInt(10)*1000;
							idCreator += rand.nextInt(10)*100;
							idCreator += rand.nextInt(10)*10;
							idCreator += rand.nextInt(10);
							
							for(int i = 0; i< list.size(); i++)
							{
								if(idCreator == list.get(i))
									unique++;
									
							}
							
						}
						while(unique > 0);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
					
					
					//if nothing goes wrong, then we can start to do our stuff.
					if(finderror == false)
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						Label username = new Label("		   	ID:");
						TextField cin_username = new TextField();
						
						
						alert.setTitle("Set Password");
						alert.setHeaderText("Remember that the length of the password has to be bigger than 6.");
						cin_username.setText(Integer.toString(idCreator)); 
						cin_username.setEditable(false);
						Password.clear();
						
						HBox usernametextf = new HBox(10,username,cin_username);
						Label pass = new Label("  	     Password:");
					
						
						Label confirmpass = new Label("Confirm Password:");
						PasswordField confirmPassword = new PasswordField();
						
						HBox passwordtextf = new HBox(10,pass,Password);
						HBox confirmpasswordtextf = new HBox(10,confirmpass,confirmPassword);
						VBox username_pass = new VBox(10,usernametextf,passwordtextf,confirmpasswordtextf);
						Label passwordlabel = new Label("Note: This ID is your Username.");
						VBox setpass = new VBox(10,username_pass,passwordlabel);
						alert.getDialogPane().setContent(setpass);
						ButtonType user_cancel = new ButtonType("Cancel");
						ButtonType user_confrim = new ButtonType("Confirm");
						alert.getButtonTypes().setAll(user_confrim,user_cancel);
						Optional<ButtonType> userchoice = alert.showAndWait();
						boolean iscancel = false;
					
						
					
						if(userchoice.get()==user_confrim)
						{
							
							Label errorlabel = new Label();
							errorlabel.setTextFill(Color.web("#FF76a3"));
							passwordtextf.getChildren().add(errorlabel);	
							errorlabel.setText("*Please check the password.");
						while(Password.getText().isEmpty()||Password.getText().length()<6||!Password.getText().contentEquals(confirmPassword.getText()))
						{
							Password.setStyle("-fx-border-color: pink;");
							confirmPassword.setStyle("-fx-border-color: pink;");
							userchoice=alert.showAndWait();
							
							iscancel=false;
							if(userchoice.get()==user_cancel)
							{
								
								iscancel=true;
								break;
							}
						}
						}
						else
						{
							iscancel=true;
							alert.close();
						}
					
						if(iscancel==false)
						{
							findstatus();

							
						try
						{
							
							cin_benefits.setText(all.generateCode(theCompany.getValue(),theArea.getValue(), thePackage.getValue(), theType.getValue()));
							cin_firstname.setText(cin_firstname.getText().trim());
							cin_lastname.setText(cin_lastname.getText().trim());
							cin_address.setText(cin_address.getText().trim());
							cin_email.setText(cin_email.getText().trim());
							
							
							String query = "INSERT INTO Employees (empId,fname, lname, address, birth_date, email, phone_num,emergency_num, "
									+ "ssn, pwd, benefit_code, marital_status, working_status, gross_pay, tax_deduct, net_pay)"
									+ " VALUES (?, ?, ?, ?, ?, ?, ?, ? , ?, sha1(?), ?, ?, 1, 0.0, 0.0, 0.0)";
						
						
							connect = DriverManager.getConnection(databaseLocation, root, password);
							pst = connect.prepareStatement(query);
								
							pst.setInt(1, idCreator);
							pst.setString(2, cin_firstname.getText().trim());
							pst.setString(3, cin_lastname.getText().trim());
							pst.setString(4, cin_address.getText().trim());
							pst.setString(5,cin_birthday.getValue().toString());
							pst.setString(6, cin_email.getText().trim());
							pst.setString(7, cin_phonenum1.getText().trim());
							pst.setString(8, cin_phonenum2.getText().trim());	
							pst.setString(9, cin_SSN.getText().trim());
							pst.setString(10, Password.getText());
							pst.setString(11, cin_benefits.getText());
							pst.setInt(12, holdstatus);
							pst.executeUpdate();
							
//							  salary DOUBLE NOT NULL,
//							    hours DOUBLE NOT NULL,
//							    overtimeHours DOUBLE NOT NULL,
//							    lastPayDate CHAR(20) NOT NULL,
//							    bonuses DOUBLE NOT NULL,
//							    raiseSalary DOUBLE NOT NULL,
//							    raiseReason VARCHAR(255) NOT NULL,
//							    raiseEffectiveDate CHAR(20) NOT NULL,
//							    full_time BIT NOT NULL
							
							//query setting dummy values, will fix remove after class is over
							//comeback to this, ask what can be done about this, issue is that an employee is added into the first table
							//but the second (below), will not take the dummy values
							query = "INSERT INTO GrossPayInfo(empId, lname, salary, hours, overtimeHours, lastPayDate, bonuses, raiseSalary, raiseReason, raiseEffectiveDate, full_time)"
									+ " VALUES(?,?, 0.0, 0.0, 0.0, 'noreason', 0.0, 0.0, 'noreason', 'noreaaon', 0)";
							pst=connect.prepareStatement(query);
							pst.setInt(1, idCreator);
							pst.setString(2, cin_lastname.getText().trim());
							pst.executeUpdate();
							
							pst.close();
							
							alert=new Alert(AlertType.INFORMATION);
							alert.setTitle("Infromation");
							alert.setHeaderText(null);
							alert.setContentText("You added a new peron."+"His/Her id is "+idCreator);
							alert.showAndWait();
							cancel.fire();
							
							}
							
						
						catch(Exception e)
						{
							
							alert=new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("Something bad happened!");
							System.out.println(e.getMessage());
							alert.showAndWait();
						}
						
						}//end of if cancel == false
						
					
						
						
						}
					}
			}
		}
		
	
	//reset
	class ResetHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			cin_firstname.setText("");
			cin_lastname.setText("");
			cin_SSN.setText("");
			cin_birthday.setValue(null);;
			cin_address.setText("");
			cin_email.setText("");
			cin_phonenum1.setText("");
			cin_phonenum2.setText("");
			radio1.setSelected(true);
			radio1.setSelected(false);
			try {
			theArea.setValue(null);
			theCompany.setValue(null);
			thePackage.setValue(null);
			theType.setValue(null);
			}catch(Exception e)
			{
				
			}
		}
	}
	//cancel
	class CancelHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
		}
	}
	
	
		//make text fields.
		public void makeaddmenu() throws IOException
		{
			cin_firstname = new TextField();
			Label f_label = new Label("        *First name:");
			HBox fname = new HBox(5,f_label,cin_firstname);
			cin_lastname = new TextField();
			Label l_label = new Label("                *Last name:");
			HBox lname = new HBox(5,l_label,cin_lastname);
			cin_SSN = new TextField();
			Label ssn_label = new Label("                  *SSN:");
			HBox ssn = new HBox(5,ssn_label,cin_SSN);
			cin_birthday = new DatePicker();
			cin_birthday.setPrefWidth(162.5);
			Label birth_label = new Label("                         *Birth:");
			HBox birth = new HBox(5,birth_label,cin_birthday);
			cin_address = new TextField();
			Label address_label = new Label("            *Address:");
			HBox address = new HBox(5,address_label,cin_address);
			cin_email = new TextField();
			Label email_label = new Label("                        *Email:");
			HBox email = new HBox(5,email_label,cin_email);
			cin_phonenum1 = new TextField();
			Label pnum1_label = new Label(" *Phone number:");
			HBox pnum1 = new HBox(5,pnum1_label,cin_phonenum1);
			cin_phonenum2 = new TextField();
			Label pnum2_label = new Label(" *Emergency Contact:");
			HBox pnum2 = new HBox(5,pnum2_label,cin_phonenum2);
			
			HBox combim1 = new HBox(5,fname,lname);
			HBox combim2 = new HBox(5,ssn,birth);
			VBox combim3 = new VBox(5,combim1,combim2);
			HBox combim4 = new HBox(5,address,email);
			VBox combim5 = new VBox(5,combim3,combim4);
			HBox combim6 = new HBox(5,pnum1,pnum2);
			VBox combim7 = new VBox(5,combim5,combim6);
			
			radio1 = new RadioButton("Single");
			radio2 = new RadioButton("Married filing jointly");
			radio3 = new RadioButton("Married filing separately");
			radio4 = new RadioButton("Head of household");
			
			
			
			Label thecompany = new Label ("*Company:");
			Label thereigon = new Label ("        *Area:");
			Label thepackage = new Label("       *Label:");
			Label thepeople = new Label ("     *Ensure:");
			
			all = new Benefits();
			theCompany.setPrefWidth(190);
			theArea.setPrefWidth(190);
			thePackage.setPrefWidth(190);
			theType.setPrefWidth(190);
			
			if(!theCompany.getItems().isEmpty())
			theCompany.getItems().clear();
			
			
			for(String key : all.AllMedicalBenefits.keySet())		
				theCompany.getItems().add(key);
			
			theCompany.getSelectionModel().selectedItemProperty().addListener(
					 new ChangeListener<String>() {
						 
						public void changed(ObservableValue ov, String old_val, String new_val) {
						
						if(!theArea.getItems().isEmpty())
						theArea.getItems().clear();
						try {
						for(String key : all.AllMedicalBenefits.get(new_val).keySet())
						{
						
							theArea.getItems().add(key);
						}
						}catch(Exception e)
						{
							
						}
						theArea.getSelectionModel().selectedItemProperty().addListener(
								 new ChangeListener<String>() {
									 public void changed(ObservableValue ov1, String old_val1, String new_val1) {
									
									if (!thePackage.getItems().isEmpty())	 
									thePackage.getItems().clear();
									
									
									double ba1, ba2, ba3, ba4, ba5;
									double b1, b2, b3, b4, b5;
									double s1, s2, s3, s4, s5;
									double g1, g2, g3, g4, g5;
									double p1, p2, p3, p4, p5;
									
									try {
									ba1 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBasics().getIndividual();
									ba2 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBasics().getIndividual_and_Spouse();
									ba3 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBasics().getParentAndChildren();
									ba4 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBasics().getFamily();
									ba5 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBasics().getChild();
									
									b1 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBronze().getIndividual();
									b2 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBronze().getIndividual_and_Spouse();
									b3 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBronze().getParentAndChildren();
									b4 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBronze().getFamily();
									b5 = all.AllMedicalBenefits.get(new_val).get(new_val1).getBronze().getChild();
									
									s1 = all.AllMedicalBenefits.get(new_val).get(new_val1).getSilver().getIndividual();
									s2 = all.AllMedicalBenefits.get(new_val).get(new_val1).getSilver().getIndividual_and_Spouse();
									s3 = all.AllMedicalBenefits.get(new_val).get(new_val1).getSilver().getParentAndChildren();
									s4 = all.AllMedicalBenefits.get(new_val).get(new_val1).getSilver().getFamily();
									s5 = all.AllMedicalBenefits.get(new_val).get(new_val1).getSilver().getChild();
									
									g1 = all.AllMedicalBenefits.get(new_val).get(new_val1).getGold().getIndividual();
									g2 = all.AllMedicalBenefits.get(new_val).get(new_val1).getGold().getIndividual_and_Spouse();
									g3 = all.AllMedicalBenefits.get(new_val).get(new_val1).getGold().getParentAndChildren();
									g4 = all.AllMedicalBenefits.get(new_val).get(new_val1).getGold().getFamily();
									g5 = all.AllMedicalBenefits.get(new_val).get(new_val1).getGold().getChild();
									
									p1 = all.AllMedicalBenefits.get(new_val).get(new_val1).getPlatinum().getIndividual();
									p2 = all.AllMedicalBenefits.get(new_val).get(new_val1).getPlatinum().getIndividual_and_Spouse();
									p3 = all.AllMedicalBenefits.get(new_val).get(new_val1).getPlatinum().getParentAndChildren();
									p4 = all.AllMedicalBenefits.get(new_val).get(new_val1).getPlatinum().getFamily();
									p5 = all.AllMedicalBenefits.get(new_val).get(new_val1).getPlatinum().getChild();
									
									
									if(ba1 != Double.MAX_VALUE || ba2 != Double.MAX_VALUE || ba3 != Double.MAX_VALUE ||
									   ba4 != Double.MAX_VALUE || ba5!= Double.MAX_VALUE)
									{
										thePackage.getItems().add("Basic");
										
									}
									
									if(b1 != Double.MAX_VALUE || b2 != Double.MAX_VALUE || b3 != Double.MAX_VALUE ||
									   b4 != Double.MAX_VALUE || b5!= Double.MAX_VALUE)
									{
										thePackage.getItems().add("Bronze");
									
									}
									
									if(s1 != Double.MAX_VALUE || s2 != Double.MAX_VALUE || s3 != Double.MAX_VALUE ||
									   s4 != Double.MAX_VALUE || s5!= Double.MAX_VALUE)
									{
										thePackage.getItems().add("Silver");
										
									}
									
									if(g1 != Double.MAX_VALUE || g2 != Double.MAX_VALUE || g3 != Double.MAX_VALUE ||
									   g4 != Double.MAX_VALUE || g5!= Double.MAX_VALUE)
									{
										thePackage.getItems().add("Gold");
										
									}
									if(p1 != Double.MAX_VALUE || p2 != Double.MAX_VALUE || p3 != Double.MAX_VALUE ||
									   p4 != Double.MAX_VALUE || p5!= Double.MAX_VALUE)
									{
										thePackage.getItems().add("Platinum");	
										
									}

							
									
									thePackage.getSelectionModel().selectedItemProperty().addListener(
									new ChangeListener<String>() {
									public void changed(ObservableValue ov, String old_val2, String new_val2) {
									
										if (!theType.getItems().isEmpty())
										theType.getItems().clear();
										
										theType.getItems().addAll("Individual", "Individual and Spouse", "Parent and Child", "Family");
									
										if(new_val2 == "Basic" && ba5 != Double.MAX_VALUE)
											theType.getItems().add("Child");
										
										else if (new_val2 == "Bronze" && b5 != Double.MAX_VALUE)
											theType.getItems().add("Child");
								
										else if (new_val2 == "Silver" && s5 != Double.MAX_VALUE)
											theType.getItems().add("Child");
									
										else if (new_val2 == "Gold" && g5 != Double.MAX_VALUE)
											theType.getItems().add("Child");
										
										else if (new_val2 == "Platinum" && p5 != Double.MAX_VALUE)
											theType.getItems().add("Child");
									
												
												 
											  }});
									
									}catch(Exception e)
									{
										
									}
							}});
						
						 
						 }});
		
			HBox benefits0 = new HBox (5, thecompany, theCompany);
			HBox benefits1 = new HBox(5,thereigon,theArea);
			HBox benefits2 = new HBox(5,thepackage,thePackage);
			HBox benefits3 = new HBox(5,thepeople,theType);
			VBox combine = new VBox(5,benefits0,benefits1,benefits2,benefits3);

			
			radioGroup = new ToggleGroup();
			radio1.setToggleGroup(radioGroup);
			radio2.setToggleGroup(radioGroup);
			radio3.setToggleGroup(radioGroup);
			radio4.setToggleGroup(radioGroup);
			VBox radiobuttonvbox = new VBox();
			radiobuttonvbox.getChildren().add(radio1);
			radiobuttonvbox.getChildren().add(radio2);
			radiobuttonvbox.getChildren().add(radio3);
			radiobuttonvbox.getChildren().add(radio4);
			VBox radiobutton = new VBox(radiobuttonvbox);
			HBox add1 = new HBox(10,combim7,radiobutton,combine);
			
			
			setmenu = new HBox(10,add1);
		}

	
	public void checkempty()
	{
		isempty=false;
		if(cin_firstname.getText().isEmpty()||cin_lastname.getText().isEmpty()||cin_birthday.getValue()==null||cin_SSN.getText().isEmpty()||cin_address.getText().isEmpty()||cin_phonenum1.getText().isEmpty()||
				cin_phonenum2.getText().isEmpty()||theCompany.getValue()==null||theArea.getValue()==null||thePackage.getValue()==null||theType.getValue()==null)
		{
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Some of the require information is missing! Please check carefully!");
			isempty=true;
			alert.showAndWait();
		}
		//missing status information.
		else if((radio1.isSelected()==false)&&(radio2.isSelected()==false)&&(radio3.isSelected()==false)&&(radio4.isSelected()==false))
		{
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("You forgot to pick a staus!");
			isempty=true;
			alert.showAndWait();
		}
		finderror=false;
	}
	
	
	public void findstatus()
	{
		if(radio1.isSelected())
			holdstatus = 1;
		else if(radio2.isSelected())
			holdstatus = 2;
		else if(radio3.isSelected())
			holdstatus = 3;
		else
			holdstatus = 4;
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
			//check if SSN is valid.
			if(finderror == false)
			{
				//check length.
			if(cin_SSN.getText().length()!=11)
			{
				alert.setContentText("Invalid social security number, wrong number of characters!");
				alert.showAndWait();
				finderror=true;
			}
			//if nothing wrong with length, check the places of dashes.
			else if(cin_SSN.getText().charAt(3)!='-' || cin_SSN.getText().charAt(6)!='-')
			{
				alert.setContentText("Invalid social security number, dashes at wrong positions!");
				alert.showAndWait();
				finderror=true;
			}
			//if nothing wrong with that, then we check if the SSN contains characters.
			else
			{
				for(int i = 0;i<cin_SSN.getText().length();i++)
				{
					if(i!=3&&i!=6)
					{
						if(!Character.isDigit(cin_SSN.getText().charAt(i)))
						{
							alert.setContentText("Invalid the social security number, contains a character that is not a digit!");
							alert.showAndWait();
							finderror=true;
							break;
						}
					}
				}
			}
			}
			//check is birthday is valid.
			if(finderror==false)
			{
			if(cin_birthday.getValue().compareTo(java.time.LocalDate.now())>=0)
			{
				alert.setContentText("Please check the birthday!");
				alert.showAndWait();
				finderror=true;
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

		


