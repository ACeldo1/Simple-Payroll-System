package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*Overall, this is what I have and I'm working to integrate the example front end
 * we worked on. Basically, getting rid of the file reading/writing stuff and keeping
 * replacing it with sql methods, will update changes*/
public class JDBCConnector {
  
	/*Modify these values to however y'all have them on your end*/
	String user = "root";
	String pwd = "Bloodywolf1234!";
	String dbname = "csc430";
	String serverName = "localhost";
	String url = "jdbc:mysql://localhost:3306/csc430";
	
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	
	String query;
	ResultSet rs;
	
	public JDBCConnector() throws SQLException, NamingException {
		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setDatabaseName(dbname);
		dataSource.setServerName(serverName);
		dataSource.setUrl(url);
		
		SetConnection(dataSource.getConnection());
//		SetStatement(GetConnection().createStatement());

	}
	
	//Integer action will have a numerical value that will indicated what action is being done (add, search, edit, delete, etc)
	//Since I believe search should be done multiple times without reprecussions, while the others should be done one at a time
//	public JDBCConnector(Integer action) throws SQLException, NamingException {
//		
//		MysqlDataSource dataSource = new MysqlDataSource();
//		dataSource.setUser(user);
//		dataSource.setPassword(pwd);
//		dataSource.setDatabaseName(dbname);
//		dataSource.setServerName(serverName);
//		dataSource.setUrl(url);
//		
//		SetConnection(dataSource.getConnection());
//		
//		if(action == 1)
//			SetPreparedStatement(GetConnection().prepareStatement());
//		
//		else
//			SetStatement(GetConnection().createStatement());
//		
//	}
	
	public void SetConnection(Connection connection) {
		this.conn = connection;	
	}
	
	public Connection GetConnection() {
		return conn;
	}
	
	public void SetStatement(Statement statement) {
		this.stmt = statement;
	}
	
	public Statement GetStatement() {
		return stmt;
	}
	
	public void SetPreparedStatement(PreparedStatement preparedStatement) {
		this.pstmt = preparedStatement;
	}
	
	public PreparedStatement GetPreparedStatement() {
		return pstmt;
	}
	
	public void SetQuery(String query) {
		this.query = query;
	}
	
	public String GetQuery() {
		return query;
	}

	public void SetResultSet(ResultSet result) {
		this.rs = result;
	}
	
	public ResultSet GetResultSet() {
		return rs;
	}
	
	//will have to filter the inputQuery for SQL injection and other stuff
	public void RunQuery() {
		
		try {
			
			System.out.println("GetStatement: " + GetStatement().toString());
			System.out.println("Query: " + GetQuery());
			
			SetResultSet(GetStatement().executeQuery(GetQuery()));
			System.out.println("GetResultSet: " + GetResultSet().toString());
			
		} catch (SQLException e) {
			
			System.out.println("Error with retrieving results...");
			e.printStackTrace();
		
		}
		
	}
	
	//input will be 0 for search(select queries), 1 for adding(update queries), 2 for deleting
	public void RunPreparedQuery(Integer indicator) {
		
		try {
		
			System.out.println("GetPreparedStatement: " + GetPreparedStatement().toString());
			System.out.println("Query: " + GetQuery());
			
			if(indicator == 0) {
			
				SetResultSet(GetPreparedStatement().executeQuery());
				System.out.println("GetResultSet: " + GetResultSet().toString());
			
			}
			else if(indicator == 1) {
				
				GetPreparedStatement().executeUpdate();
				System.out.println("Run prepared query indicator 1 was successful");
				//SetResultSet(GetPreparedStatement().executeUpdate());
				//System.out.println("GetResultSet: " + GetResultSet().toString());
				
			}
			else if (indicator == 2) {
				
			}
			
		} catch (SQLException e) {
			
			System.out.println("Error with retrieving results...");
			e.printStackTrace();
		
		}
		
	}
	
	public void EndAllConnections() throws SQLException {
		
		GetResultSet().close();
		GetStatement().close();
		GetConnection().close();
		
	}
	
	//this is how i orginally had the constructor
//	public JDBCConnector() throws SQLException, NamingException {
//		
//		MysqlDataSource dataSource = new MysqlDataSource();
//		dataSource.setUser(user);
//		dataSource.setPassword(pwd);
//		dataSource.setDatabaseName(dbname);
//		dataSource.setServerName(serverName);
//		dataSource.setUrl(url);
//
//		Connection conn = dataSource.getConnection();
//		Statement stmt = conn.createStatement();
//		
//		String query = "SELECT * FROM Employees";
//		ResultSet rs = stmt.executeQuery(query);
//		
//		while(rs.next()) {
//			System.out.println("empId: " + rs.getInt("empId"));
//		}
//		
//		rs.close();
//		stmt.close();
//		conn.close();
//		
//	}
	
	
	//use this to test if the connection is working or not
//	public static void main(String[] args) throws SQLException, NamingException {
    
//		Context context = new InitialContext();
		
//		DataSource dataSource = (DataSource) context.lookup("jdbc:mysql://localhost:3306/csc430?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//		DataSource dataSource = (DataSource) context.lookup("jdbc:mysql://localhost:3306/csc430");
		
//		MysqlDataSource dataSource = new MysqlDataSource();
//		dataSource.setUser("root");
//		dataSource.setPassword("Bloodywolf1234!");
//		dataSource.setDatabaseName("csc430");
//		dataSource.setServerName("localhost");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/csc430");
//		
//		Connection conn = dataSource.getConnection();
//		Statement stmt = conn.createStatement();
//		
//		String query = "SELECT * FROM Employees";
//		ResultSet rs = stmt.executeQuery(query);
//		
//		while(rs.next()) {
//			System.out.println("empId: " + rs.getInt("empId"));
//		}
//		
//		rs.close();
//		stmt.close();
//		conn.close();
  
//	}

}
