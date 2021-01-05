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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 
import javafx.fxml.Initializable;

public class SearchAnotherEmployee {
							
		//change to your stuff
		//project is my db name, change it to yours.
		//and in line 126, 318, 333, change your stuffs.
		String databaseLocation = "jdbc:mysql://localhost:3306/csc430";
		String root = "root";
		String password = "Bloodywolf1234!";
		Button cancel;
		Button searchbutton;
		TextField search;
		TextField cin_firstname;
		TextField cin_lastname;
		TextField cin_phonenum1;
		TextField cin_email;
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
		
		protected SearchAnotherEmployee() {
			
		}
		
		public void start(Pane parent) throws Exception{
			
//			makeobject();
//			setdisable();
			System.out.print("beginning of search emp start method works");
			search = new TextField();
			search.setPromptText("Search using employee id");
			search.setFocusTraversable(false);
			searchbutton = new Button("Search");
//			searchbutton.setOnAction(new SearchHandler());
			search.setPrefWidth(300);
			topmenu = new HBox(search,searchbutton);
			topmenu.setAlignment(Pos.CENTER);
			setmenu.getChildren().add(topmenu);
					
			//if click search.
			search.setOnAction(e->{
				
				makeobject();
				setdisable();
				
				topmenu.setAlignment(Pos.TOP_LEFT);
//		    	String searchName = search.getText().trim();
//		    	int str_len = searchName.length();
		    	
//		    	for(int i = 0; i < str_len; i++) {
//					if((Character.isLetter(searchName.charAt(i)) == false)) {
//						alert = new Alert(AlertType.WARNING);
//						alert.setTitle("Error");
//						alert.setHeaderText(null);
//						alert.setContentText("Invalid character detected!");
//						alert.showAndWait();
//					}
//				}
		    	
//				if(search.getText().isEmpty() || str_len <= 1)
				if(search.getText().isEmpty())
				{
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("You did not enter anything, or input was too small.");
					alert.showAndWait();
				}
				else
				{
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection connect = DriverManager.getConnection(databaseLocation, root, password);
							System.out.println("Success connect mysqlserver!");
							
//						connect = DriverManager.getConnection(databaseLocation, root, password);
						String queue = "SELECT fname, lname, email, phone_num FROM Employees WHERE Employees.empId = "+search.getText();
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
								cin_firstname.setText(rs.getString("fname"));
								cin_lastname.setText(rs.getString("lname"));
								cin_email.setText(rs.getString("email"));
								cin_phonenum1.setText(rs.getString("phone_num"));
								
						}
				}catch(Exception e1)
					{
					e1.printStackTrace();
					}
			}
				
			});
			
			System.out.println("End of emp search start method");
		
			parent.getChildren().addAll(setmenu);

		}

//		class SearchHandler implements EventHandler<ActionEvent>
//		{
//
//			@Override
//			public void handle(ActionEvent arg0) {
//				// TODO Auto-generated method stub			
//				
////				 int len = s.length();
////			      for (int i = 0; i < len; i++) {
////			         // checks whether the character is not a letter
////			         // if it is not a letter ,it will return false
////			         if ((Character.isLetter(s.charAt(i)) == false)) {
////			            return false;
////			         }
////			      }
//				
//				topmenu.setAlignment(Pos.TOP_LEFT);
////		    	String searchName = search.getText().trim();
////		    	int str_len = searchName.length();
//		    	
////		    	for(int i = 0; i < str_len; i++) {
////					if((Character.isLetter(searchName.charAt(i)) == false)) {
////						alert = new Alert(AlertType.WARNING);
////						alert.setTitle("Error");
////						alert.setHeaderText(null);
////						alert.setContentText("Invalid character detected!");
////						alert.showAndWait();
////					}
////				}
//		    	
////				if(search.getText().isEmpty() || str_len <= 1)
//				if(search.getText().isEmpty())
//				{
//					alert = new Alert(AlertType.WARNING);
//					alert.setTitle("Error");
//					alert.setHeaderText(null);
//					alert.setContentText("You did not enter anything, or input was too small.");
//					alert.showAndWait();
//				}
//				else
//				{
//					try {
//						connect = DriverManager.getConnection(databaseLocation, root, password);
//						String queue = "SELECT fname, lname, email, phone_num FROM Employees WHERE Employees.empId = "+search.getText();
//						pst = connect.prepareStatement(queue);
//						ResultSet rs = pst.executeQuery();
//						if(rs.next()==false)
//						{
//							alert = new Alert(AlertType.WARNING);
//							alert.setTitle("Error");
//							alert.setHeaderText(null);
//							alert.setContentText("Id "+search.getText()+" does not exist.");
//							if(botmenu.getChildren().isEmpty()==false)
//							{
//								setmenu.getChildren().remove(botmenu);
//								botmenu.getChildren().remove(completeform);
//								botmenu.getChildren().remove(buttons);
//							}
//							alert.showAndWait();
//						}
//						else
//						{
//								if(botmenu.getChildren().isEmpty()==false)
//								{
//									setmenu.getChildren().remove(botmenu);
//									botmenu.getChildren().remove(completeform);
//									botmenu.getChildren().remove(buttons);
//								}
//								cin_firstname.setText(rs.getString("fname"));
//								cin_lastname.setText(rs.getString("lname"));
//								cin_email.setText(rs.getString("email"));
//								cin_phonenum1.setText(rs.getString("phone_num"));
//								
//						}
//				}catch(Exception e)
//					{
//					e.printStackTrace();
//					}
//			}
//			}
//		}

//	class CancelHandler implements EventHandler<ActionEvent>
//	{
//		@Override
//		public void handle(ActionEvent arg0) {
//			// TODO Auto-generated method stub
//			setmenu.getChildren().remove(botmenu);
//			botmenu.getChildren().remove(buttons);
//			botmenu.getChildren().remove(completeform);
//			search.setDisable(false);
//			searchbutton.setDisable(false);
//		}
//	}

	public void makeobject()
	{
		Label L1 = new Label("       First name:");
		cin_firstname = new TextField();
		HBox combine1 = new HBox(L1,cin_firstname);
		
		Label L2 = new Label("               Last name:");
		cin_lastname = new TextField();
		HBox combine2 = new HBox(L2, cin_lastname);
		
		HBox connect1 = new HBox(8,combine1,combine2);

		Label L3 = new Label("    Email: ");
		cin_email = new TextField();
		HBox combine3 = new HBox(L3, cin_email);
		
		Label L4 = new Label("Phone number:");
		cin_phonenum1 = new TextField();
		HBox combine4 = new HBox(L4,cin_phonenum1);
		
		HBox connect2 = new HBox(8, combine3, combine4);

		VBox textfield = new VBox(8,connect1,connect2);
		textfield.setPadding(new Insets(20));
	
		completeform = new HBox(textfield);
		
		botmenu.getChildren().add(completeform);
		
	}
	public void setdisable()
	{
		cin_firstname.setDisable(true);
		cin_lastname.setDisable(true);
		cin_email.setDisable(true);
		cin_phonenum1.setDisable(true);
	}

}