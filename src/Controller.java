import java.sql.*;
import java.util.ArrayList;

import models.*;

public class Controller {

	static String username = null;
	static String password = null;

	public static void login(String username, String password) {
		Controller.username = username;
		Controller.password = password;
	}
	
	public static Connection connectToSQL(String username, String password) {
		// TODO: Clear credentials
		String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
		username = username == null ? "nebssa" : username;
		password = password == null ? "oosejooj" : password;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return null;	
	}
	
    public static Table fetchTable(Tables tableToFetch) {

        Table table = new Table();

        try {
            Connection conn = connectToSQL(username, password);
            String query = String.format("SELECT * FROM %s", Utils.tableToString(tableToFetch));
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            ResultSetMetaData rsmd = results.getMetaData();

            int numberOfColumns = rsmd.getColumnCount();

            for (int i = 1; i <= numberOfColumns; i++) {
                String columnName = rsmd.getColumnName(i);
                table.addColumn(columnName);
            }

            while (results.next()) {
                String[] row = new String[numberOfColumns];
                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnValue = results.getString(i);
                    row[i-1] = columnValue;
                }
                table.addRow(row);
            }

            
            statement.close();
            conn.close();

        } catch (Exception e) {
        	System.err.println(e);
            e.printStackTrace();
            return null;
        }
        return table;
    }

    public static IModel makeModel(Tables table) {
        
    	IModel model;
    	switch (table) {
        case MEMBERS:
            model = new models.Member();
            break;
        case MEMBERPROFILE:
            model = new models.MemberProfile();
            break;
        case MOVIE:
            model = new models.Movie();
            break;
        case ACTOR:
            model = new models.Actor();
            break;
        case WATCH:
            model = new models.Watch();
            break;
        case MOVIE_GENRE:
            model = new models.MovieGenre();
            break;
        case LIKES:
            model = new models.Likes();
            break;
        case STARRED_BY:
            model = new models.StarredBy();
            break;
        default:
            model = null;
            break;
        }
    	return model;

    }
    
    public static boolean insertRecord(Tables table, String argv) throws SQLIntegrityConstraintViolationException, SQLException {
    	IModel model = makeModel(table);
    	return insertRecord(model, argv);
    }
    
    public static boolean insertRecord(IModel table, String argv) throws SQLIntegrityConstraintViolationException, SQLException {
    	System.out.println(table);
    	System.out.println(table.insertFormat());
        
    	// NOTE: Splits by comma followed by optional space
        String[] values = argv.split(",\\s*");
        String query = table.insertFormat();

        try {
            Connection con = connectToSQL(username, password);
            PreparedStatement pstmt = con.prepareStatement(query);
            table.insertRecord(pstmt, values);
            pstmt.executeUpdate();
            

            pstmt.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            // if the user already exists
            System.err.println("User may already exist!\n" + e.toString());
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
        	throw e;
        } catch (Exception e) {
            System.err.println("cannot insert values");
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        
        System.out.println(String.format("New tuple \"(%s)\" has been inserted.", String.join(", ", values)));
        return true;
        
    }
    
    static boolean updateTable(String table, String argv) throws SQLException {
    	Tables tables = Utils.tablesFromString(table);
    	IModel model = makeModel(tables);
    	return updateTable(model, argv);
    }
    
    static boolean updateTable(Tables table, String argv) throws SQLException {
    	IModel model = makeModel(table);
    	return updateTable(model, argv);
    }
    
    static boolean updateTable(IModel table, String argv) throws SQLException {
        
    	// NOTE: Splits by comma followed by optional space
        String[] values = argv.split(",\\s*");
        String tableName = "";
        String query = String.format("UPDATE %s SET ", argv);

        try {
            Connection con = connectToSQL(username, password);
            PreparedStatement pstmt = con.prepareStatement(query);
            table.insertRecord(pstmt, values);
            pstmt.executeUpdate();
            
            
            

            pstmt.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            // if the user already exists
            System.err.println("User may already exist!\n" + e.toString());
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
        	throw e;
        } catch (Exception e) {
            System.err.println("cannot insert values");
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        
        System.out.println(String.format("New tuple \"(%s)\" has been inserted.", String.join(", ", values)));
        return true;
    }
    
    static boolean deleteRecord(String table, String id) throws SQLException {
    	Tables tables = Utils.tablesFromString(table);
    	IModel model = makeModel(tables);
    	return updateTable(model, id);
    }
    
    static boolean deleteRecord(Tables table, String id) throws SQLException {
    	IModel model = makeModel(table);
    	return updateTable(model, id);
    }
    
    static boolean deleteRecord(IModel table, String id) throws SQLException {
        
    	// NOTE: Splits by comma followed by optional space
        String[] values = id.split(",\\s*");
        String tableName = "";
        String query = String.format("UPDATE %s SET ", id);

        try {
            Connection con = connectToSQL(username, password);
            PreparedStatement pstmt = con.prepareStatement(query);
            table.insertRecord(pstmt, values);
            pstmt.executeUpdate();
            
            
            

            pstmt.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            // if the user already exists
            System.err.println("User may already exist!\n" + e.toString());
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
        	throw e;
        } catch (Exception e) {
            System.err.println("cannot insert values");
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        
        System.out.println(String.format("New tuple \"(%s)\" has been inserted.", String.join(", ", values)));
        return true;
    }
    
    
    

	public static ArrayList[] getMovieByTitle(String title) throws SQLException {
		
		
		String sql = "SELECT distinct M.Movie_name, R.MovieRating"
		+ "FROM Movie M, Starred_by S, Actor A, MovieRatings R"
		+ "WHERE M.Movie_name LIKE '%green%' OR A.First_name LIKE '%Mark%' AND S.Movie_ID = M.Movie_ID AND S.Actor_ID = A.Actor_ID AND M.Movie_ID = R.Movie_ID";
		
//		  Connection connection = getConnection();
	       java.util.Scanner scan = new java.util.Scanner(System.in);
	       
	       ArrayList<String> columnNames = new ArrayList<>();
	       ArrayList<String[]> rows = new ArrayList<>();
	       
	       Connection conn = Controller.connectToSQL(Controller.username, Controller.password);
	       Connection connection = conn;
	       
	       System.out.println("Please enter member_id:");
	       String mem_id = scan.nextLine();
	       
	       System.out.println("Please enter profile name:");
	       String prof = scan.nextLine();
	       
	       Statement stmt = connection.createStatement();
	       ResultSet rs = stmt.executeQuery(sql);
	       ResultSetMetaData rsmd = rs.getMetaData();
	       
	       int numberOfColumns =rsmd.getColumnCount();
	       
	       for(int i = 1; i <= numberOfColumns; i++) {
	           String columnName = rsmd.getColumnName(i);
	           columnNames.add(columnName);
	       }
	       
	       while(rs.next()) {
	           String[] row = new String[numberOfColumns];
	           for (int i = 1; i <= numberOfColumns; i++) {
	           String columnValue = rs.getString(i);
	           row[i - 1] = columnValue;
	       }
	       
	           rows.add(row);
	           
	       }
	       
	       stmt.close();
	       conn.close();

	       return new ArrayList[] { columnNames, rows };
	}
	
	
	public ArrayList[] showRental(String[] args) throws SQLException {
//       Connection connection = getConnection();
       java.util.Scanner scan = new java.util.Scanner(System.in);
       
       ArrayList<String> columnNames = new ArrayList<>();
       ArrayList<String[]> rows = new ArrayList<>();
       
       Connection conn = Controller.connectToSQL(Controller.username, Controller.password);
       Connection connection = conn;
       
       System.out.println("Please enter member_id:");
       String mem_id = scan.nextLine();
       
       System.out.println("Please enter profile name:");
       String prof = scan.nextLine();
       
       String sql = "SELECT P.Member_ID, P.Profile_name, M.Movie_name FROM WATCH W, Movie M WHERE Member_ID ='%"+mem_id+"%' AND Profile_name ='%"+prof+"%' AND W.Movie_ID = M.Movie_ID";
       Statement stmt = connection.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       ResultSetMetaData rsmd = rs.getMetaData();
       
       int numberOfColumns =rsmd.getColumnCount();
       
       for(int i = 1; i <= numberOfColumns; i++) {
           String columnName = rsmd.getColumnName(i);
           columnNames.add(columnName);
       }
       
       while(rs.next()) {
           String[] row = new String[numberOfColumns];
           for (int i = 1; i <= numberOfColumns; i++) {
           String columnValue = rs.getString(i);
           row[i - 1] = columnValue;
       }
       
           rows.add(row);
           
       }
       
       stmt.close();
       conn.close();

       return new ArrayList[] { columnNames, rows };
       
    }
}
