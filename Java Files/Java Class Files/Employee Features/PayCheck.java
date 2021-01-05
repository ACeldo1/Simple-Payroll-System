package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PayCheck {

	String databaseLocation = "jdbc:mysql://localhost:3306/csc430";
	String root = "root";
	String password = "Bloodywolf1234!";
	GrossPay gross;
	NetPay netpay;
	Tax tax;
	Benefits ben;
	DecimalFormat df = new DecimalFormat("#,###,##0.00");
//	int id = 290330;
	
	public void start(Pane parent, int id) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connect = DriverManager.getConnection(databaseLocation, root, password);
		
		
		String query = "SELECT * FROM employees join grosspayinfo where employees.empId = grosspayinfo.empId and  employees.empId = ?";
		PreparedStatement pst = connect.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		
		rs.next();
		
		System.out.println("rs.getDouble() : " + rs.getDouble(19));
		System.out.println("rs.getDouble() : " + rs.getDouble(20));
		System.out.println("rs.getDouble() : " + rs.getDouble(21));
		System.out.println("rs.getDouble() : " + rs.getDouble(23));
		System.out.println("rs.getString() : " + rs.getString(12));
		System.out.println("rs.getDouble() : " + rs.getString(22));
		System.out.println("rs.getInt() : " + rs.getInt(13));
		
		
//		double salary, double hours, double overtimeHours, double bonuses
		
		ben = new Benefits();
		gross = new GrossPay(rs.getDouble(19), rs.getDouble(20),rs.getDouble(21),rs.getDouble(23), rs.getDouble(24));
		gross.calcGrossPay();
		gross.getGrossPay();
		tax = new Tax(gross);
		tax.setstatus(rs.getInt(13));
		tax.find_federaltax();
		tax.find_statetax();
		ben.setPackageCode(rs.getString(12));
		
		netpay = new NetPay(gross, tax);
		
		double takehome = gross.getGrossPay()*tax.getfederaltax();
		takehome += gross.getGrossPay()*tax.getstatetax();
		takehome += gross.getGrossPay()*tax.getMedicaretax();
		takehome += gross.getGrossPay()*tax.getSSN_tax();
		takehome += rs.getDouble("bonuses");

//				takehome += rs.getDouble("")+15;
//		takehome += ben.getMedicalDeductable()/4;
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setAlignment(Pos.TOP_LEFT);
	
		
		Label title = new Label("Meme Matrix");		
		Label address1 = new Label ("2800 Victory Blvd");
		Label address2 = new Label ("Staten Island, NY 10314");
		VBox company = new VBox(title, address1, address2);

		
		Date now = new Date ();
		DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
		Label Date = new Label ("Date:    ");
		Label today = new Label(format.format(now));
		HBox date = new HBox(Date, today);
		
		
		
		grid.setConstraints(company, 0, 0);
		grid.setConstraints(date, 40, 0);
		
		grid.getChildren().addAll(company, date);
		
		
		Label pay = new Label("PAY TO THE");		
		Label pay2 = new Label ("ORDER OF  ");
		Label name = new Label ("\n	 "+rs.getString(2)+" "+rs.getString(3));
		
		
		VBox payed = new VBox(pay, pay2);
		
		Label money = new Label("\n$ ");
		Double m = (gross.getGrossPay()-takehome)/52.0;
		Label  dollars= new Label ("\n"+df.format(m));
		Label blank = new Label();
		
		GridPane grid2 = new GridPane();
		grid2.setPadding(new Insets(10,10,10,10));
		grid2.setVgap(8);
		grid2.setHgap(10);
		
		grid2.setConstraints(payed, 0, 0);
		grid2.setConstraints(name, 1, 0);
	 	grid2.setConstraints(blank, 35, 0);
		grid2.getChildren().addAll(payed, name, blank);
		
		VBox AllMoney = new VBox(grid2);
		
		HBox mtop = new HBox(AllMoney, money,dollars);
		
		DecimalFormat f = new DecimalFormat("0.00");
		String[] parts = f.format(m).split("\\.");

		//		String[] parts = m.toString().split("\\.");
		Label words = new Label("\n				 "+NumberConverter.numberToWord(Integer.parseInt(parts[0]))+" and "+parts[1]+"/100");
		VBox mbottom = new VBox(mtop, words);
		
		GridPane grid3 = new GridPane();
		grid3.setPadding(new Insets(10,10,10,10));
		grid3.setVgap(8);
		grid3.setHgap(10);
		

	
		Label Account = new Label ("\n\n\n\n|| 001234 ||  |:0000456789|:  000000089||") ;
		Label sig = new Label("\n\nMemeachu");
		
		grid3.setConstraints(Account, 0, 0);
		grid3.setConstraints(sig, 30, 0);
		
		
		
		grid3.getChildren().addAll(Account, sig);
		
		
		
		
		
		
		
		VBox check = new VBox(grid, mbottom, grid3 );
		check.setAlignment(Pos.TOP_CENTER);
		
		
	
		parent.getChildren().addAll(check);
	
	}

}



