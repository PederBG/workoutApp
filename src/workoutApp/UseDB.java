package workoutApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Help class to enable communication with remote MySQL database
 * 
 * USAGE: From a given class call: UseDB.getTable(query) / UseDB.addRow()
 */
public class UseDB {
	
	//Help function for connecting to the external database.
		public static Connection connectDB() {
			System.out.println("Connecting to database..." + "\n");
			
			Connection conn = null;
			try {
			    conn =
			       DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/pederbg_workouts","pederbg_dbproj","dbproj");

			} catch (SQLException ex) {
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			return conn;
		}
		
		// Get table from database
		public static ArrayList<ArrayList<String>> getTable(String query) {
			
			Connection conn = connectDB();
			
			if (conn == null) {
				System.out.println("Can not connect to database");
				System.exit(0);
			}
			
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ArrayList<String>> table = null;
			
			System.out.println("Running query..");
			
			try {
			    stmt = conn.createStatement();
			    rs = stmt.executeQuery(query);
			    java.sql.ResultSetMetaData rsmd = rs.getMetaData();

			    int colNum = rsmd.getColumnCount();
			    table = new ArrayList<ArrayList<String>>();
			    
		        while(rs.next()) {
		        	ArrayList<String> col = new ArrayList<String>();
		        	for (int i = 1; i < colNum+1; i++) {
						col.add(rs.getString(i));
					}
		        	table.add(col);
			    }

			}
			catch (SQLException ex){
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			
		try { conn.close(); } catch (SQLException e) {/* ignore */}
		
	    System.out.println("Process finished, connection closed");
		return table;
		}
		
		// --------------------------------------------------------------------------
		
		/*
		 * Method for adding new rows into external database.
		 * 
		 * USAGE: UseDB.addRow('table', values...) // Values must be of type integer or string
		 * 
		 * EXAMPLE: UseDB.addRow("exercise", "Brøsth", "tren brøstet");
		 */
		public static boolean addRow(String table, Object...objects) {
			Connection conn = connectDB();
			boolean result_status = false;
			
			try {
				System.out.println("Inserting row into " + table + "...");
				Statement stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + table); // Need rs to get column count
			    java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			    int colNum = rsmd.getColumnCount();
			    String cols = "";
			    
			    for (int i = 1; i < colNum; i++) {
					cols += rsmd.getColumnName(i) + ", ";
				}
			    cols += rsmd.getColumnName(colNum); // MySQL tables are one-indexed
			    
			    String values = "";
			    for (Object val : objects) {
			    	if (val instanceof String) {
			    		values += "'" + val + "', ";
			    	}
			    	else if (val instanceof Integer) {
			    		values += val.toString() + ", ";
			    	}
			    }
			    values = values.substring(0, values.length() - 2);
			    
				String query = "INSERT INTO " + table + " (" + cols + ") VALUES (" + values + ")";
				stmt.executeUpdate(query);
				System.out.println("Row added!");
				result_status = true;
			}
			catch (SQLException ex){
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			
			try { conn.close(); } catch (SQLException e) {/* ignore */}
		    System.out.println("Process finished, connection closed");
			return result_status;
		}


}
