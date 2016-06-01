import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
	
	public Vector<Vector> getReviews(String locationName) {
		ResultSet rs;
		//Location Name string in form: Name-Location
		String name = locationName.substring(0, locationName.indexOf("-"));
		String location = locationName.substring(locationName.indexOf("-") + 1);
		System.out.println(name + " " + location);
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from reviews where rid IN (select rid from restaurant where name='" + name + "'and location='" + location +"')";
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("userName");
				String rating = String.valueOf(rs.getInt("rating"));
				String comments = rs.getString("comments");
				System.out.println(username + " " + rating + " " + comments);
				//Object[] o = { username, rating, comments };
				Vector<String> v = new Vector<String>();
				v.add(username);
				v.add(rating);
				v.add(comments);
				results.add(v);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

}
