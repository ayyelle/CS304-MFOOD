import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
					  "jdbc:oracle:thin:@localhost:1522:ug", "ora_d1v8", "a71528095");
			stmt = con.createStatement();		


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getCredentials(String rid, String eid, String password) {
		ResultSet rs;
		ArrayList<String> result = new ArrayList<String>();
		//!!HACKY! FIX!
		try {
			
			if (eid.isEmpty()) {
			//int ridInt = Integer.parseInt(rid);
			String query = "Select * FROM restaurant WHERE oid = '" + rid + "' and ownerpassword = '" + password + "'";
			rs = stmt.executeQuery(query);
				if(rs.next()) {
					result.add("OWNER");
					result.add(String.valueOf(rs.getInt("rid")));
					System.out.println(query);
				} else {
					result.add("NONE");
				}

			} else {
				ResultSet rsEmp;
				String queryEmp = "Select * FROM employeeworkat WHERE rid = '" + rid + "' and eid = '" + eid + "' and password = '" + password + "'";
				rsEmp = stmt.executeQuery(queryEmp);
				System.out.println(queryEmp);
				
				if (rsEmp.next()) {
					result.add("EMP");
					result.add(String.valueOf(rsEmp.getInt("rid")));
				} else {
					result.add("NONE");
				}

				
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public Vector<Vector> getMenuItems(String locationName) {
		ResultSet rs;
		//Location Name string in form: Name-Location
		System.out.println("MENU ITEMS FROM: " + locationName);
		String name = getRestaurantFromString(locationName);
		//String name = locationName.substring(0, locationName.indexOf("-"));
		String location = getLocationFromString(locationName);
		//String location = locationName.substring(locationName.indexOf("-") + 1);
		System.out.println(name + " " + location);
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from MenuItem where rid IN (select rid from restaurant where name='" + name + "'and location='" + location +"')";
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String foodName = rs.getString("name");
				String price = String.valueOf(rs.getInt("price"));
							
				System.out.println(foodName + " " + price);
				Vector<String> v = new Vector<String>();
				v.add(foodName);
				v.add(price);
				results.add(v);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public Vector<Vector> getRestaurantMenuItems(String rid) {
		ResultSet rs;
		//Location Name string in form: Name-Location
		System.out.println("MENU ITEMS FROM: " + rid);
		//String name = getRestaurantFromString(locationName);
		//String name = locationName.substring(0, locationName.indexOf("-"));
		//String location = getLocationFromString(locationName);
		//String location = locationName.substring(locationName.indexOf("-") + 1);
		//System.out.println(name + " " + location);
		Vector<Vector> results = new Vector<Vector>();
		String query = "SELECT * FROM MenuItem WHERE rid = " + rid;
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String foodName = rs.getString("name");
				String price = String.valueOf(rs.getInt("price"));
							
				System.out.println(foodName + " " + price);
				Vector<String> v = new Vector<String>();
				v.add(foodName);
				v.add(price);
				results.add(v);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	/**public Vector<String> getRestaurants(String rid) {
		ResultSet rs;
		Vector<String> restaurants = new Vector<String>();
		
		String query = "SELECT name, location FROM restaurant WHERE rid="+rid;
		System.out.println("SQLResturant.java getRestaurants query: " + query);
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
	}**/
	
	public String getRestaurantName(String rid) {
		String result = "";
		String query = "SELECT name, location FROM restaurant WHERE rid = '" + rid + "'";
		System.out.println("getRestaurantName from SQLRestaurant.Java: " + query);
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String location = rs.getString("location");
				result = name + " - " + location;
				System.out.println(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Vector<String> getRestaurants() {
		ResultSet rs;
		Vector<String> restaurants = new Vector<String>();
		
		String query = "SELECT name, location FROM restaurant";
		System.out.println(query);
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
	

	

	String getRestaurantFromString(String restaurantLocation) {
		String name = restaurantLocation.substring(0, restaurantLocation.indexOf("-"));
		return name;		
	}
	
	String getLocationFromString(String restaurantLocation) {
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
	
	public boolean addFoodItem(int fid, String name, String price, String rid){
		boolean result = false;
		
		String query = "INSERT INTO menuitem VALUES(";
				query += fid + ",";
				query += "'" + name + "',";
				query += price + ",";
				query += rid + ")";
		System.out.println("ADDING FOOD WITH QUERY :" + query);
		try {
		int rs = stmt.executeUpdate(query);
		result = rs==1? true: false;
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean deleteFoodItem(String RID, String foodName){
		boolean result = false;
		String query = "DELETE FROM menuitem WHERE rid = " + RID + " and name = '" + foodName + "'";
		try {
		int rs = stmt.executeUpdate(query);
		result = rs==1? true: false;
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateRestaurantName(String rid, String name, String location) {
		boolean result = false;
		String 	update = "UPDATE restaurant ";
				update += "SET location = '" + location + "', ";
				update += "name = '" + name + "' ";
				update += "WHERE rid = " + rid;
		System.out.println("SQLResturant.java Name Update Query: " + update);
		try {
		int rs = stmt.executeUpdate(update);
		result = rs==1? true: false;
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean addEmployee(int eid, String name, String password, String rid) {
		boolean result = false;
		String 	insert = "INSERT INTO employeeworkat VALUES (";
				insert += eid + ", ";
				insert += "'" + name + "', ";
				insert += "'" + password + "', ";
				insert += rid + ")";
		System.out.println("SQLResturant.java Name Update Query: " + insert);
		try {
		int rs = stmt.executeUpdate(insert);
		result = rs==1? true: false;
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
