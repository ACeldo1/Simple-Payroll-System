package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 


public class EditBenefits {
							
		//change to your stuff
		//project is my db name, change it to yours.
		//and in line 116, 226, and 276. change your stuffs.
	String databaseLocation = "jdbc:mysql://localhost:3306/csc430";
	String root = "root";
	String password = "Bloodywolf1234!";
	Button edit;
	Button searchbutton;
	TextField search;
	TextField cin_firstname;
	TextField cin_lastname;
	TextField cin_SSN;
	TextField cin_benefits;
	RadioButton active;
	ToggleGroup radioGroup2;
	RadioButton terminated;
	RadioButton quit;
	RadioButton deceased;
	RadioButton disable;
	ComboBox<String> theCompany = new ComboBox<>();
	ComboBox<String> theArea = new ComboBox<>();
	ComboBox<String> thePackage = new ComboBox<>();
	ComboBox<String> theType = new ComboBox<>();
	Alert alert;
	HBox topmenu;
	HBox botmenu=new HBox();
	VBox setmenu = new VBox();
	HBox buttons;
	HBox completeform;
	int lenght = 800, weight = 300;
	int workingstatus;
	Scene scene;
	Connection connect;
	PreparedStatement pst;
	Benefits all;
	VBox benefitsmenu;
	VBox workstatus;

	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		//first we check if the file exists or not, if it exists then we check if there is things in the file or not.
		search = new TextField();
		search.setPromptText("Enter his/her ID");
		search.setFocusTraversable(false);
		searchbutton= new Button("Search");
		searchbutton.setOnAction(new SearchHandler());
		search.setPrefWidth(300);
		topmenu = new HBox(search,searchbutton);
		topmenu.setAlignment(Pos.CENTER);
		setmenu.getChildren().add(topmenu);
		scene = new Scene(setmenu,lenght,weight);
		window.setScene(scene);
		window.setTitle("Edit Benefits");
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
					String queue = "Select * FROM Employees where empId = "+search.getText();
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
							cin_benefits.setText(rs.getString("benefit_code"));
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
							else
							{
								Label cantedit = new Label("You can't do anything to his file!");
								cantedit.setPrefHeight(30);
								HBox deceased_label = new HBox(cantedit);
								deceased_label.setPadding(new Insets(10));
								cantedit.setTextFill(Color.web("#FF76a3"));
								workstatus.getChildren().add(deceased_label);
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
			search.setDisable(true);
			searchbutton.setDisable(true);
			edit.setDisable(true);
			alert = new Alert(AlertType.NONE);
			alert.setTitle("Modify benefits");
			alert.setHeaderText(null);
			try {
				benefitsmenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			alert.getDialogPane().setContent(benefitsmenu);
			ButtonType user_cancel = new ButtonType("Cancel");
			ButtonType user_confrim = new ButtonType("Confirm");
			alert.getButtonTypes().setAll(user_confrim,user_cancel);
			Optional<ButtonType> userchoice = alert.showAndWait();
			Boolean iscancel = false;
			if (userchoice.get()==user_confrim)
			{
				if(theCompany.getValue()==null||theArea.getValue()==null||thePackage.getValue()==null||theType.getValue()==null)
				{
					String reminded="We indicated that you didn't pick your benefits, do you mean not to take any benefits?";
					alert = new Alert(AlertType.NONE);
					alert.setHeaderText(null);
					alert.getDialogPane().setContentText(reminded);
					ButtonType yes = new ButtonType("Yes, I don't want any benefits.");
					ButtonType no = new ButtonType("No, I do want to take benefits.");
					alert.getButtonTypes().setAll(yes,no);
					Optional<ButtonType> choice = alert.showAndWait();
					if(choice.get()==yes)
					{
						iscancel=true;
						String benefitscode = "";
						String queue = "update employees set benefit_code = ? where empid = ? ";
						try {
						pst=connect.prepareStatement(queue);
						pst.setString(1, benefitscode);
						pst.setString(2, search.getText());
						pst.executeUpdate();
						pst.close();
						alert =  new Alert(AlertType.INFORMATION);
						alert.setTitle("Updated!");
						alert.setHeaderText(null);
						alert.setContentText("Modified benefits successfully.");
						alert.showAndWait();
						edit.setDisable(false);
						cin_benefits.setText("");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("Something bad happened!");
							alert.showAndWait();
						}
					}
					else
					{
						while(theCompany.getValue()==null||theArea.getValue()==null||thePackage.getValue()==null||theType.getValue()==null)
						{
						alert= new Alert(AlertType.NONE);
						alert.setHeaderText(null);
						alert.getDialogPane().setContent(benefitsmenu);
						ButtonType pickcancel = new ButtonType("Cancel");
						ButtonType pickconfrim = new ButtonType("Confirm");
						alert.getButtonTypes().setAll(pickconfrim,pickcancel);
						Optional<ButtonType> userpick = alert.showAndWait();
						if(userpick.get()==pickconfrim&&theCompany.getValue()!=null&&theArea.getValue()!=null&&thePackage.getValue()!=null&&theType.getValue()!=null)
						{
							iscancel=false;
							break;
						}
						else if(userpick.get()==pickcancel)
						{
							iscancel=true;
							break;
						}
						}
					}
				}
				if(iscancel==false)
				{
					String benefitscode = all.generateCode(theCompany.getValue(),theArea.getValue(), thePackage.getValue(), theType.getValue());
					String queue = "update employees set benefit_code = ? where empid = ? ";
					try {
					pst=connect.prepareStatement(queue);
					pst.setString(1, benefitscode);
					pst.setString(2, search.getText());
					pst.executeUpdate();
					pst.close();
					alert =  new Alert(AlertType.INFORMATION);
					alert.setTitle("Updated!");
					alert.setHeaderText(null);
					alert.setContentText("Modified benefits successfully");
					alert.showAndWait();
					edit.setDisable(false);
					cin_benefits.setText(all.generateCode(theCompany.getValue(),theArea.getValue(), thePackage.getValue(), theType.getValue()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Something bad happened!");
						alert.showAndWait();
					}
				}
			}
			edit.setDisable(false);
			search.setDisable(false);
			searchbutton.setDisable(false);
		}
	}
	
	
	public void setdisable()
	{
		cin_firstname.setDisable(true);
		cin_lastname.setDisable(true);
		cin_SSN.setDisable(true);
		cin_benefits.setDisable(true);
		active.setDisable(true);
		terminated.setDisable(true);
		deceased.setDisable(true);
		disable.setDisable(true);
	}
	
	
	public void makeobject()
	{
		Label L1 = new Label("    First name:");
		cin_firstname = new TextField();
		HBox combine1 = new HBox(L1,cin_firstname);
		
		Label L2 = new Label("    Last name:");
		cin_lastname = new TextField();
		HBox combine2 = new HBox(L2, cin_lastname);
		
		
		Label L3 = new Label("              SSN:");
		cin_SSN = new TextField();
		HBox combine3 = new HBox(L3, cin_SSN);
		
		Label L4 = new Label("Benefit code:");
		cin_benefits = new TextField();
		HBox combine4 = new HBox(L4,cin_benefits);
		
		VBox connect = new VBox(8,combine1,combine2,combine3,combine4);
		
		VBox textfield = new VBox(8,connect);
		textfield.setPadding(new Insets(20));
		
		radioGroup2 = new ToggleGroup();
		active = new RadioButton("Active      ");
		terminated = new RadioButton("Terminated");
		deceased =  new RadioButton("Deceased");
		disable = new RadioButton("Disable");
		active.setToggleGroup(radioGroup2);
		terminated.setToggleGroup(radioGroup2);
		deceased.setToggleGroup(radioGroup2);
		disable.setToggleGroup(radioGroup2);
		workstatus = new VBox(5,active,terminated,deceased,disable);
		if(deceased.isSelected())
		{
			Label label = new Label("You can't do anything to his file!");
			workstatus.getChildren().add(label);
		}
		VBox connect_group = new VBox(40,workstatus);
		connect_group.setPadding(new Insets(20));
		completeform = new HBox(textfield,connect_group);
		botmenu.getChildren().add(completeform);
		
	}
	
	
		
		public void benefitsmenu() throws IOException
		{
			
			Label thecompany = new Label ("*Company:");
			Label thereigon = new Label ("        *Area:");
			Label thepackage = new Label("       *Label:");
			Label thepeople = new Label ("     *Ensure:");
			
			all = new Benefits();
			theCompany.setPrefWidth(150);
			theArea.setPrefWidth(150);
			thePackage.setPrefWidth(150);
			theType.setPrefWidth(150);
			
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
		
			HBox benefits0 = new HBox (thecompany, theCompany);
			HBox benefits1 = new HBox(thereigon,theArea);
			HBox benefits2 = new HBox(thepackage,thePackage);
			HBox benefits3 = new HBox(thepeople,theType);
			benefitsmenu = new VBox(5,benefits0,benefits1,benefits2,benefits3);
		}
	
}