import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
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

			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug", "ora_d1v8", "a71528095");
			stmt = con.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getCredentials(String rid, String eid, String password) {
		ResultSet rs;
		ArrayList<String> result = new ArrayList<String>();
		try {
			//Check if owner first
			String query = "Select * FROM restaurant WHERE oid = '" + eid + "' and ownerpassword = '" + password + "' and rid = '" + rid + "'";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				result.add("OWNER");
				result.add(String.valueOf(rs.getInt("rid")));

			} else {
				// Now, check if employee.
				ResultSet rsEmp;
				String queryEmp = "Select * FROM employeeworkat WHERE rid = '" + rid + "' and eid = '" + eid
						+ "' and password = '" + password + "'";
				rsEmp = stmt.executeQuery(queryEmp);

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
		// Location Name string in form: Name-Location
		String name = locationName.substring(0, locationName.indexOf("-"));
		String location = locationName.substring(locationName.indexOf("-") + 1);
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from reviews where rid IN (select rid from restaurant where name='" + name
				+ "'and location='" + location + "')";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("userName");
				String rating = String.valueOf(rs.getInt("rating"));
				String comments = rs.getString("comments");
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

	public Vector<Vector> getReviewsByRID(String rid) {
		ResultSet rs;
		// Location Name string in form: Name-Location
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from reviews where rid =" + rid;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("userName");
				String rating = String.valueOf(rs.getInt("rating"));
				String comments = rs.getString("comments");

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

	public String getEmployeeName(String rid, String eid) {
		ResultSet rs;
		String employeename = "";
		String query = "Select name from employeeworkat where eid=" + eid + " AND rid=" + rid;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				employeename = rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeename;
	}

	public Vector<Vector> getMenuItems(String locationName) {
		ResultSet rs;
		// Location Name string in form: Name-Location
		String name = getRestaurantFromString(locationName);
		String location = getLocationFromString(locationName);
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from MenuItem where rid IN (select rid from restaurant where name='" + name
				+ "'and location='" + location + "')";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String foodName = rs.getString("name");
				String price = String.valueOf(rs.getFloat("price"));
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
		Vector<Vector> results = new Vector<Vector>();
		String query = "SELECT * FROM MenuItem WHERE rid = " + rid;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String foodName = rs.getString("name");
				String price = String.valueOf(rs.getFloat("price"));

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

	public Vector<Vector> getMaxCusine() {
		ResultSet rs;
		ResultSet rs2;

		Vector<Vector> results = new Vector<Vector>();
		// if only using this query, you get all cuisines with their max rating
		String query2 = "Select DISTINCT r.cuisine,re.rating, r.name, r.location FROM restaurant r, reviews re WHERE r.rid=re.rid and re.rating = (SELECT MAX(m.maxRating) FROM CuisineMax m where m.cuisine = r.cuisine)";
		try {
			rs2 = stmt.executeQuery(query2);
			while (rs2.next()) {

				String cuisineType = "";
				String ratingValue = "";
				String nameLocation = "";

				cuisineType = rs2.getString("cuisine");
				String restaurantName = rs2.getString("name");
				String restaurantLocation = rs2.getString("location");
				ratingValue = rs2.getString("rating");
				nameLocation = restaurantName + "-" + restaurantLocation;

				Vector<String> v = new Vector<String>();
				v.add(cuisineType);
				v.add(nameLocation);
				v.add(ratingValue);
				results.add(v);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }

	public Vector<Vector> getAverageCuisine() {
		ResultSet rs;
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select r.cuisine, AVG(re.rating) AS avgrating from restaurant r,reviews re where r.RID=re.RID group by r.cuisine";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {

				String cuisine = rs.getString("cuisine");
				Float ratingValue = rs.getFloat("avgrating");
				BigDecimal result = round(ratingValue,2);
				String rating = String.valueOf(result);
				Vector<String> v = new Vector<String>();
				v.add(cuisine);
				v.add(rating);
				results.add(v);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	public Vector<String> getRestaurantsByID(String rid) {
		ResultSet rs;
		Vector<String> restaurants = new Vector<String>();
		String query = "SELECT name, location FROM restaurant WHERE rid=" + rid;
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

	public String getRestaurantName(String rid) {
		String result = "";
		String query = "SELECT name, location FROM restaurant WHERE rid = '" + rid + "'";

		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String location = rs.getString("location");
				result = name + " - " + location;
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

	public String getRestaurantFromRID(String rid) {
		ResultSet rs;
		Vector<String> restaurants = new Vector<String>();
		String query = "Select name, location from restaurant where RID ='" + rid + "'";

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
		return restaurants.get(0);
	}

	public boolean requestReservation(Date date, String time, String restaurantLocation, String partySize,
			String customerID) {

		boolean result = false;
		String requestTime = toTimestampFormat(date, time);
		String name = getRestaurantFromString(restaurantLocation);
		String location = getLocationFromString(restaurantLocation);
		String query = "select r.rid, t.tid from restaurant r, hastable t " + "where r.rid = t.rid and r.name = '"
				+ name + "' and r.location = '" + location + "' and t.tablesize >=" + partySize + " " + "MINUS "
				+ "(select t.rid, t.tid " + "from tablebooking tb, hastable t, restaurant r "
				+ "where t.tid = tb.tid and t.rid = tb.rid and r.name = '" + name + "' and r.location = '" + location
				+ "' " + "and ((to_timestamp('" + requestTime
				+ "', 'yyyy:mm:dd hh24:mi')) < (tb.startDaytime + interval'2' hour))" + "and ((to_timestamp('"
				+ requestTime + "', 'yyyy:mm:dd hh24:mi')) + interval '2' hour) > (tb.startDayTime))";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				result = false;
			} else {
				result = true;
				rs.next();
				// take first table that fits criteria
				int rid = rs.getInt("rid");
				int tid = rs.getInt("tid");
				String insertQuery = "insert into tablebooking values (TO_TIMESTAMP('" + requestTime
						+ "', 'yyyy-mm-dd hh24:mi:ss')," + partySize + ", 2, " + tid + ", " + rid + ", '" + customerID
						+ "')";

				int rows = stmt.executeUpdate(insertQuery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean requestReservationByOwner(Date date, String time, String rid, String partySize, String customerID) {
		// time string format: HH:MM (24 hour hour)
		boolean result = false;
		String requestTime = toTimestampFormat(date, time);

		String query = "select r.rid, t.tid from restaurant r, hastable t " + "where r.rid = t.rid and r.rid = '" + rid
				+ "' and t.tablesize >=" + partySize + " " + "MINUS " + "(select t.rid, t.tid "
				+ "from tablebooking tb, hastable t, restaurant r "
				+ "where t.tid = tb.tid and t.rid = tb.rid and r.rid = '" + rid + "' " + "and ((to_timestamp('"
				+ requestTime + "', 'yyyy:mm:dd hh24:mi')) < (tb.startDaytime + interval'2' hour))"
				+ "and ((to_timestamp('" + requestTime
				+ "', 'yyyy:mm:dd hh24:mi')) + interval '2' hour) > (tb.startDayTime))";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				result = false;
			} else {
				result = true;
				rs.next();
				// take first
				int tid = rs.getInt("tid");
				String insertQuery = "insert into tablebooking values (TO_TIMESTAMP('" + requestTime
						+ "', 'yyyy-mm-dd hh24:mi:ss')," + partySize + ", 2, " + tid + ", " + rid + ", '" + customerID
						+ "')";

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
		int month = date.getMonth() + 1;
		String monthStr = String.format("%02d", month);
		int year = date.getYear() + 1900;
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

	// For the Reservations Panel when viewed by owner
	public Vector<Vector> getReservations(String resID) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		ResultSet rs;
		Vector<Vector> result = new Vector<Vector>();
		String query = "select startdaytime, duration, partysize, tid, rid, firstname, lastname, c.username "
				+ "from customer c, tablebooking t " + "where c.username=t.username AND t.rid=" + resID + " order by startdaytime";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Timestamp starttimestamp = rs.getTimestamp("startdaytime");
				String starttime = format.format(starttimestamp).toString();
				String partysize = String.valueOf(rs.getInt("partysize"));
				String duration = rs.getString("duration");
				String tid = String.valueOf(rs.getInt("tid"));
				String rid = String.valueOf(rs.getInt("rid"));
				String customerFirstName = rs.getString("firstname");
				String customerLastName = rs.getString("lastname");
				String username = rs.getString("username");
				String customerName = customerFirstName + " " + customerLastName;

				Vector<String> newStr = new Vector<String>();
				newStr.add(starttime);
				newStr.add(duration);
				newStr.add(partysize);
				newStr.add(tid);
				newStr.add(rid);
				newStr.add(customerName);
				newStr.add(username);
				result.add(newStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// projection
	public Vector<Vector> getSelectReservations(String resID, String selected, int indices[], String d, String df) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		// System.out.println("In getReservations resID: " + resID);
		ResultSet rs;
		Vector<Vector> result = new Vector<Vector>();
		//String query = "select " + selected + " from customer c, tablebooking t "
		//		+ "where c.username=t.username AND t.rid=" + resID;
		System.out.println("SELECTED: " + selected);
		String query = "SELECT " + selected
				+ " FROM tablebooking t, customer c  " + "WHERE c.username=t.username  "
				+ "AND startdaytime>to_timestamp('" + d + "','YYYY-MM-DD HH:MI:SS.FF') "
				+ "AND startdaytime<to_timestamp('" + df + "','YYYY-MM-DD HH:MI:SS.FF') " + "AND t.rid=" +resID
				+ " order by startdaytime ASC";
		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Vector<String> newStr = new Vector<String>();
				for (int i = 0; i < indices.length; i++) {
					//{"startdaytime", "duration", "partysize", "tid", "rid", 
					//"firstname", "lastname", "c.username"};
					if (indices[i] == 0) {
						Timestamp starttimestamp = rs.getTimestamp("startdaytime");
						String starttime = format.format(starttimestamp).toString();
						newStr.add(starttime);
					}
					if (indices[i] == 1) {
						String duration = rs.getString("duration");
						newStr.add(duration);
					}
					if (indices[i] == 2) {
						String partysize = String.valueOf(rs.getInt("partysize"));
						newStr.add(partysize);
					}
					if (indices[i] == 3) {
						String tid = String.valueOf(rs.getInt("tid"));
						newStr.add(tid);
					}
					if (indices[i] == 4) {
						String rid = String.valueOf(rs.getInt("rid"));
						newStr.add(rid);
					}
					if (indices[i] == 5) {
						String customerFirstName = rs.getString("firstname");
						String customerLastName = rs.getString("lastname");
						String customerName = customerFirstName + " " + customerLastName;
						newStr.add(customerName);
					}
					if (indices[i] == 6) {
						String username = rs.getString("username");
						newStr.add(username);
					}

				}
				result.add(newStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// get the restaurant's reservations, select dates
	public Vector<Vector> getReservationsByDate(String resID, String d, String df) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		System.out.println("In getReservationsByDate resID: " + resID + " date: " + d);
		ResultSet rs;
		Vector<Vector> result = new Vector<Vector>();
		String query = "SELECT startdaytime, duration, partysize, tid, rid, firstname, lastname, c.username "
				+ "FROM tablebooking t, customer c  " + "WHERE c.username=t.username  "
				+ "AND startdaytime>to_timestamp('" + d + "','YYYY-MM-DD HH:MI:SS.FF') "
				+ "AND startdaytime<to_timestamp('" + df + "','YYYY-MM-DD HH:MI:SS.FF') " + "AND t.rid=" +resID;
		System.out.println("getReservationsByDate query: " + query);
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("THERE IS A NEXT");
				Timestamp starttimestamp = rs.getTimestamp("startdaytime");
				String starttime = format.format(starttimestamp).toString();

				//String starttime = rs.getString("startdaytime");
				String partysize = String.valueOf(rs.getInt("partysize"));
				String duration = rs.getString("duration");
				String tid = String.valueOf(rs.getInt("tid"));
				String rid = String.valueOf(rs.getInt("rid"));
				String customerFirstName = rs.getString("firstname");
				String customerLastName = rs.getString("lastname");
				String username = rs.getString("username");
				String customerName = customerFirstName + " " + customerLastName;

				Vector<String> newStr = new Vector<String>();
				newStr.add(starttime);
				newStr.add(duration);
				newStr.add(partysize);
				newStr.add(tid);
				newStr.add(rid);
				newStr.add(customerName);
				newStr.add(username);
				System.out.println("getReservationsByDate newStr: " + newStr);
				result.add(newStr);
			}
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		System.out.println("getReservationsByDate result: " + result);
		return result;
	}

	// get List of tables for specific restaurant
	public Vector<Vector> getTablesForRestaurant(String resID) {
		ResultSet rs;
		Vector<Vector> result = new Vector<Vector>();
		String query = "select distinct tid, tablesize, numbooked " + "from hastable " + "where rid=" + resID;

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String tid = String.valueOf(rs.getInt("tid"));
				String tablesize = String.valueOf(rs.getInt("tablesize"));
				String numbooked = String.valueOf(rs.getInt("numbooked"));

				Vector<String> newStr = new Vector<String>();
				newStr.add(tid);
				newStr.add(tablesize);
				newStr.add(numbooked);

				result.add(newStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getOwnerFromRID(String resID) {
		String query = "select ownername from restaurant where rid = " + resID;
		String result = "";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) { // just runs once
				result = rs.getString("ownername");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteTable(String rid, String tid) {
		boolean result = false;
		String query = "DELETE FROM hastable WHERE rid = " + rid + " and tid = '" + tid + "'";
		try {
			int rs = stmt.executeUpdate(query);
			result = rs == 1 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Vector<String> getCustomerUsernames() {
		ResultSet rs;
		Vector<String> results = new Vector<String>();
		String query = "select username from customer";
		String username;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				username = rs.getString("username");
				results.add(username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;

	}

	public boolean ownerDeleteBooking(String rid, String startDayTime, String username, String tid) {
		Boolean result = false;

		// String query = "delete from TableBooking WHERE StartDayTime = '" +
		// startDayTime + "' and rid = '" + restaurantID + "' and tid = '" + tid
		// + "' and rid = '" + restaurantID + "'";
		String query = "delete from TABLEBOOKING WHERE STARTDAYTIME = to_timestamp('" + startDayTime
				+ "', 'YYYY-MM-DD HH24-mi-ss.FF') and rid = '" + rid + "' and tid = '" + tid + "' and username = '"
				+ username + "'";
		try {
			stmt.executeUpdate(query);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Vector<Vector> getFilteredReviews(String ratingSelection) {
		ResultSet rs;
		Vector<Vector> result = new Vector<Vector>();
		String query = "select name, location, rating, comments, username" + " from reviews r, restaurant t "
				+ "where r.rid = t.rid and " + ratingSelection + " <= ALL (select rating " + "from reviews r1 "
				+ "where r1.rid = r.rid)";

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
				result.add(newStr);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public boolean addFoodItem(int fid, String name, String price, String rid) {
		boolean result = false;

		String query = "INSERT INTO menuitem VALUES(";
		query += fid + ",";
		query += "'" + name + "',";
		query += price + ",";
		query += rid + ")";

		try {
			int rs = stmt.executeUpdate(query);
			result = rs == 1 ? true : false;

		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				result = false;

			} else {
				result = false;

			}
		}

		return result;
	}

	public boolean deleteFoodItem(String RID, String foodName) {
		boolean result = false;
		String query = "DELETE FROM menuitem WHERE rid = " + RID + " and name = '" + foodName + "'";
		try {
			int rs = stmt.executeUpdate(query);
			result = rs == 1 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateRestaurantName(String rid, String name, String location) {
		String result = "CHECK_FAIL";
		String update = "UPDATE restaurant ";
		update += "SET location = '" + location + "', ";
		update += "name = '" + name + "' ";
		update += "WHERE rid = " + rid;
		try {
			int rs = stmt.executeUpdate(update);
			result = rs == 1 ? (result = "SUCCESS") : (result = "CHECK_FAIL");

		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			if (e.getErrorCode() == 1) {
				// Primary key constraint violation
				result = "PRIMARY_KEY_FAIL";
			} else if (e.getErrorCode() == 2290) {
				result = "CHECK_FAIL";
			}
		}

		return result;
	}

	public boolean addEmployee(int eid, String name, String password, String rid) {
		boolean result = false;
		String insert = "INSERT INTO employeeworkat VALUES (";
		insert += eid + ", ";
		insert += "'" + name + "', ";
		insert += "'" + password + "', ";
		insert += rid + ")";
		try {
			int rs = stmt.executeUpdate(insert);
			result = rs == 1 ? true : false;

		}
		catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				// Integrity constraint violated -- not unique employee id
				result = false;
			}

		}

		return result;
	}
	
	public String getImage(String locationName) {
		String name = getRestaurantFromString(locationName);
		String location = getLocationFromString(locationName);
		Vector<Vector> results = new Vector<Vector>();
		String query = "Select * from Restaurant where rid IN (select rid from restaurant where name='" + name
				+ "'and location='" + location + "')";
		String result = "";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				result = rs.getString("img");
			}
			
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Boolean deleteReview(String rid, String userName, String comment) {
		boolean result = false;
		String query = "DELETE FROM reviews WHERE rid = " + rid; 
				query += " and comments = '" + comment + "'";
				query += " and username = '" + userName + "'";
		try {
			int rs = stmt.executeUpdate(query);
			result = rs == 1 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
