import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLRestaurant {
	Connection con;
	Statement stmt;
	
	public SQLRestaurant() {
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


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean getCredentials(String rid, String password) {
		ResultSet rs;
		boolean result = false;
		try {
			
			int ridInt = Integer.parseInt(rid);
			String query = "Select * FROM restaurant where rid = '" + ridInt + "' and password = '" + password + "'";
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
		} catch (NumberFormatException e) {
			//If the entered rid isn't an integer, then it cannot be a valid ID
			result = false;
		}
		return result;
		
	}

}
