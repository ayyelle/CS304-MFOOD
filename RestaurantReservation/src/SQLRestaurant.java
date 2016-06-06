import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
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
		System.out.println(locationName);
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

	public Vector<String> getRestaurants() {
		ResultSet rs;
		Vector<String> restaurants = new Vector<String>();
		
		String query = "Select name, location from restaurant";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String location = rs.getString("location");
				restaurants.add(name + "-" + location);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurants;
	}
	
	public boolean requestReservation(Date date, String time, String restaurantLocation, String partySize, String customerID) {
		//time string format: HH:MM (24 hour hour)
		boolean result = false;
		String requestTime = toTimestampFormat(date, time);
		System.out.println(requestTime);
		String name = getRestaurantFromString(restaurantLocation);
		String location = getLocationFromString(restaurantLocation);
		String query = "select r.rid, t.tid from restaurant r, hastable t " + 
					"where r.rid = t.rid and r.name = '" + name + "' and r.location = '" + location + "' and t.tablesize >=" + partySize + " "+
					"MINUS " + 
    				"(select t.rid, t.tid " +
    				"from tablebooking tb, hastable t, restaurant r " +
    				"where t.tid = tb.tid and t.rid = tb.rid and r.name = '" + name + "' and r.location = '" + location + "' " +
    				"and ((to_timestamp('"+ requestTime +"', 'yyyy:mm:dd hh24:mi')) < (tb.startDaytime + interval'2' hour))" +
    				"and ((to_timestamp('" + requestTime +"', 'yyyy:mm:dd hh24:mi')) + interval '2' hour) > (tb.startDayTime))";
		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				result =  false;
			} else {
				result = true;
				rs.next();
				//take first
				int rid = rs.getInt("rid");
				int tid = rs.getInt("tid");
				String insertQuery = "insert into tablebooking values (TO_TIMESTAMP('" + requestTime +"', 'yyyy-mm-dd hh24:mi:ss')," + partySize +", 2, " + tid + ", " + rid + ", '" + customerID + "')";
				System.out.println("RID: " + rid + " TID: " + tid);
				System.out.println(insertQuery);
				int rows = stmt.executeUpdate(insertQuery);
				
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String toTimestampFormat(Date date, String time) {
		int day = date.getDate();
		String dayStr = String.format("%02d", day);
		int month = date.getMonth()+1;
		String monthStr = String.format("%02d", month);
		int year = date.getYear()+1900;
		
		String formatStr = year + ":" + monthStr + ":" + dayStr + " " + time;
		return formatStr;
	}
	
	private String getRestaurantFromString(String restaurantLocation) {
		String name = restaurantLocation.substring(0, restaurantLocation.indexOf("-"));
		return name;
		
	}
	
	private String getLocationFromString(String restaurantLocation) {
		String location = restaurantLocation.substring(restaurantLocation.indexOf("-") + 1);
		return location;

		
	}

	public Vector<Vector> getFilteredReviews(String ratingSelection) {
		ResultSet rs;
		Vector<Vector> result = new Vector<Vector>();
		String query = "select name, location, rating, comments, username" +
					" from reviews r, restaurant t "+
					"where r.rid = t.rid and "+ ratingSelection +" <= ALL (select rating " +
					"from reviews r1 " +
					"where r1.rid = r.rid)";
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String location = rs.getString("location");
				String nameLocation = name + "-" + location;
				
				String rating = String.valueOf(rs.getInt("rating"));
				String comments = rs.getString("comments");
				String username = rs.getString("username");
				
				Vector<String> newStr = new Vector<String>();
				newStr.add(nameLocation);
				newStr.add(username);
				newStr.add(rating);
				newStr.add(comments);
				System.out.println(comments);
				result.add(newStr);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
