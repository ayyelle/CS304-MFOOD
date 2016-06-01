import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SQLCustomer {
	Connection con;
	Statement stmt;
	
	public SQLCustomer() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(
					  "jdbc:oracle:thin:@localhost:1522:ug", "ora_l9t7", "a65123085");
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM restaurant");
			rs.next();
			System.out.println(rs.getInt("rid"));
			rs.next();
			System.out.println(rs.getString("name"));
			


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean checkCredentials(String username, String password) {
		ResultSet rs;
		boolean result = false;
		try {
			String query = "Select * FROM customer where UserName = '" + username + "' and password = '" + password + "'";
			rs = stmt.executeQuery(query);
			System.out.println(query);
			if (!rs.isBeforeFirst()) {
				result = false;
			} else {
				result = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
		
		
	}
	
	public String addUser(String username, String password, String firstName, String lastName, String phoneNum) {
		ResultSet rs;
		String result = "";
		try {
			String query = "insert into customer values ('" + username + "', '" + password +"', '" + firstName +"', '"+ lastName + "', '" + phoneNum + "')";
			int resultInt = stmt.executeUpdate(query);
			System.out.println(query);
			result = "Success";
			
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getErrorCode());
			if (e.getErrorCode() == 1) {
				result = "NotUnique";
			}
		}
		return result;
		
	}

}
