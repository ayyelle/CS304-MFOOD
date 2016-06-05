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
	String currentUsername;
	SQLRestaurant sr;
	
	public SQLCustomer() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(
					  "jdbc:oracle:thin:@localhost:1522:ug", "ora_b9x8", "a82200106");
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
				currentUsername = username;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
		
		
	}
	
	public boolean addReview(String restaurantName,String comment,String rating){
		int restaurantID = 0;
		Integer rs;
		Boolean result = false;
		//not yet working
		//String rName = sr.getRestaurantFromString(restaurantName);
		//String location = sr.getLocationFromString(restaurantName);
		//dummy values for now
		String rName = "";
		String location = "";
		ResultSet results;
		String getRestaurantID = "select r.rid from restaurant "+
		                         "where r.name=" + rName + "and r.location=" +location;
	
		try {
			results = stmt.executeQuery(getRestaurantID);
			restaurantID = results.getInt("rid");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String query = "insert into reviews values ('" + comment + "', '" + restaurantID +"', '" + rating +"', '"+ currentUsername + "')";
		try {
			rs = stmt.executeUpdate(query);
			result = true;
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
