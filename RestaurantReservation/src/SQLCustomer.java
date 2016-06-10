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
				//currentUsername = username;
				//saveUsername(username);
				//System.out.println("username is now" + currentUsername);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
			
		
	}
	
/*	public String getUsername(){
		return currentUsername;
	}
	
	public void saveUsername(String usernameHere){
		this.currentUsername = usernameHere;
		
	}*/
	
	public boolean addReview(String restaurantName,String comment,String rating,String currentUsername){
		int restaurantID = 0;
		Integer rs;
		Boolean result = false;
		//not yet working
		String rName = sr.getRestaurantFromString(restaurantName);
		String location = sr.getLocationFromString(restaurantName);
		System.out.println("rname is " + rName);
		System.out.println("location name is " + location);
		//dummy values for now
		//String rName = "";
		//String location = "";
		ResultSet results;
		String getRestaurantID = "Select RID FROM restaurant WHERE name = '" + rName + "' and location = '" + location + "'";
		//String getRestaurantID = "select r.rid FROM restaurant r "+
		                         //"where r.name=" + rName + "and r.location=" +location;
	
		try {
			results = stmt.executeQuery(getRestaurantID);
			results.next();
			restaurantID = results.getInt("rid");
			results.next();
			System.out.println("restaurant ID is " + restaurantID);
		} catch (SQLException e1) {
			System.out.println("failed to get rid");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("restaurant id is now " + restaurantID);
		System.out.println("comment is now " + comment);
		System.out.println("rating is now" + rating);
		//String user = getUsername();
		//System.out.println(user);
		System.out.println("username is now " + currentUsername);
		
		String query = "insert into reviews values ('" + comment + "', '" + rating +"', '" + restaurantID +"', '"+ currentUsername + "')";
		//System.out.println("my query is " + query);
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
		//System.out.println(locationName);
		//String name = getRestaurantFromString(locationName);
		//String name = locationName.substring(0, locationName.indexOf("-"));
		//String location = getLocationFromString(locationName);
		//String location = locationName.substring(locationName.indexOf("-") + 1);
		//System.out.println(name + " " + location);
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from TableBooking where UserName='" + username +"'";
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				//String startDayTime = rs.getString("StartDayTime");
				String startDayTime = String.valueOf(rs.getTimestamp("StartDayTime"));
				String partySize = String.valueOf(rs.getInt("PartySize"));
				//String duration = String.valueOf(rs.getInt("Duration"));
				String rid = String.valueOf(rs.getInt("RID"));
				String tid = String.valueOf(rs.getInt("TID"));
							
				System.out.println(startDayTime + " " + partySize + " "+ " " + rid );
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
		//System.out.println(locationName);
		//String name = getRestaurantFromString(locationName);
		//String name = locationName.substring(0, locationName.indexOf("-"));
		//String location = getLocationFromString(locationName);
		//String location = locationName.substring(locationName.indexOf("-") + 1);
		//System.out.println(name + " " + location);
		System.out.println("username is " + username);
		Vector<String> results = new Vector<String>();
		String query = "Select * from TableBooking where UserName='" + username +"'";
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				//String startDayTime = rs.getString("StartDayTime");
				String startDayTime = String.valueOf(rs.getTimestamp("StartDayTime"));
				String partySize = String.valueOf(rs.getInt("PartySize"));
				//String duration = String.valueOf(rs.getInt("Duration"));
				String rid = String.valueOf(rs.getInt("RID"));
				String tid = String.valueOf(rs.getInt("TID"));
							
				System.out.println(startDayTime + " " + partySize + " "+ " " + rid );
				//Vector<String> v = new Vector<String>();
				String restaurantName = sr.getRestaurantFromRID(rid);
			results.add(restaurantName+"-"+startDayTime+"-"+partySize+"-"+tid);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("stringed is " + results);
		return results;
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

	public boolean deleteBooking(String restaurantName, String startDayTime, String username, String tid) {
		int restaurantID = 0;
		Integer rs;
		Boolean result = false;
		//not yet working
		String rName = sr.getRestaurantFromString(restaurantName);
		String location = sr.getLocationFromString(restaurantName);
		System.out.println("rname is " + rName);
		System.out.println("location name is " + location);
		//dummy values for now
		//String rName = "";
		//String location = "";
		ResultSet results;
		String getRestaurantID = "Select RID FROM restaurant WHERE name = '" + rName + "' and location = '" + location + "'";
		//String getRestaurantID = "select r.rid FROM restaurant r "+
		                         //"where r.name=" + rName + "and r.location=" +location;
	
		try {
			results = stmt.executeQuery(getRestaurantID);
			results.next();
			restaurantID = results.getInt("rid");
			results.next();
			System.out.println("restaurant ID is " + restaurantID);
		} catch (SQLException e1) {
			System.out.println("failed to get rid");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String query = "delete from TableBooking WHERE StartDayTime = '" + startDayTime + "' and rid = '" + restaurantID + "' and tid = '" + tid + "' and rid = '" + restaurantID + "'";
		//System.out.println("my query is " + query);
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
