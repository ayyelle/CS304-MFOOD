import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SQLCustomer {
	Connection con;
	Statement stmt;
	//String currentUsername;
	SQLRestaurant sr = new SQLRestaurant();
	
	public SQLCustomer() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug", "ora_d1v8", "a71528095");
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM restaurant");
			rs.next();
			rs.next();
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
	
	
	public boolean addReview(String restaurantName,String comment,String rating,String currentUsername){
		int restaurantID = 0;
		Integer rs;
		Boolean result = false;
		//not yet working
		String rName = sr.getRestaurantFromString(restaurantName);
		String location = sr.getLocationFromString(restaurantName);
		ResultSet results;
		String getRestaurantID = "Select RID FROM restaurant WHERE name = '" + rName + "' and location = '" + location + "'";

		try {
			results = stmt.executeQuery(getRestaurantID);
			results.next();
			restaurantID = results.getInt("rid");
			results.next();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String query = "insert into reviews values ('" + comment + "', '" + rating +"', '" + restaurantID +"', '"+ currentUsername + "')";
		try {
			//TODO make sure this actually works 
			rs = stmt.executeUpdate(query);
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public Vector<Vector> getReservations(String username) {
		ResultSet rs;
		//Location Name string in form: Name-Location
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from TableBooking where UserName='" + username +"'";
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		//java.util.Date date= new java.util.Date();
		Timestamp ts_now = new Timestamp(today.getTime());
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Timestamp sqlTime = rs.getTimestamp("StartDayTime");
				//do not include old reservations
				if (sqlTime.before(ts_now)){
					continue;
				}
				String startDayTime = String.valueOf(rs.getTimestamp("StartDayTime"));
				String partySize = String.valueOf(rs.getInt("PartySize"));
				String rid = String.valueOf(rs.getInt("RID"));
				String tid = String.valueOf(rs.getInt("TID"));
				Vector<String> v = new Vector<String>();
				String restaurantName = sr.getRestaurantFromRID(rid);
				v.add(restaurantName);
				v.add(startDayTime);
				v.add(partySize);
				v.add(tid);
				results.add(v);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	
	public Vector<String> getReservationsAsString(String username) {
		ResultSet rs;
		//Location Name string in form: Name-Location
		Vector<String> results = new Vector<String>();
		String query = "Select * from TableBooking where UserName='" + username +"'";
		Date today = new Date();
		//java.util.Date date= new java.util.Date();
		Timestamp ts_now = new Timestamp(today.getTime());
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Timestamp sqlTime = rs.getTimestamp("StartDayTime");
				if (sqlTime.before(ts_now)){
					continue;
				}
				String startDayTime = String.valueOf(rs.getTimestamp("StartDayTime"));
				String partySize = String.valueOf(rs.getInt("PartySize"));
				String rid = String.valueOf(rs.getInt("RID"));
				String tid = String.valueOf(rs.getInt("TID"));
				String restaurantName = sr.getRestaurantFromRID(rid);
			results.add(restaurantName+"-"+startDayTime+"-"+partySize+"-"+tid);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	
	public String addUser(String username, String password, String firstName, String lastName, String phoneNum) {
		ResultSet rs;
		String result = "";
		try {
			String query = "insert into customer values ('" + username + "', '" + password +"', '" + firstName +"', '"+ lastName + "', '" + phoneNum + "')";
			int resultInt = stmt.executeUpdate(query);
			result = "Success";
			
			
		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				result = "NotUnique";
			}
		}
		return result;
	}

	public boolean deleteBooking(String restaurantName, String startDayTime, String username, String tid) {
		int restaurantID = 0;
		Integer rs;
		Boolean result = false;
		String rName = sr.getRestaurantFromString(restaurantName);
		String location = sr.getLocationFromString(restaurantName);
		ResultSet results;
		String getRestaurantID = "Select RID FROM restaurant WHERE name = '" + rName + "' and location = '" + location + "'";
		try {
			results = stmt.executeQuery(getRestaurantID);
			results.next();
			restaurantID = results.getInt("rid");
			results.next();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String query = "delete from TableBooking WHERE StartDayTime = to_timestamp('" + startDayTime + "', 'YYYY-MM-DD HH24-mi-ss.FF') and rid = '" + restaurantID + "' and tid = '" + tid + "' and rid = '" + restaurantID + "'";
		try {
			//TODO make sure this actually works 
			rs = stmt.executeUpdate(query);
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}

}
