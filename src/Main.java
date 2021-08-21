import java.sql.*;
import java.util.*;

public class Main {
    public static ArrayList[] showRental(String[] args) throws SQLException {
        Connection connection = getConnection(args[0], args[1], args[2]);
        Scanner scan = new Scanner(System.in);

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String[]> rows = new ArrayList<>();

        Connection conn = Controller.connectToSQL(Controller.username, Controller.password);

        System.out.println("Please enter member_id:");
        String mem_id = scan.nextLine();

        System.out.println("Please enter profile name:");
        String prof = scan.nextLine();

        String sql = "SELECT P.Member_ID, P.Profile_name, M.Movie_name FROM WATCH W, Movie M WHERE Member_ID ='%"
                + mem_id + "%' AND Profile_name ='%" + prof + "%' AND W.Movie_ID = M.Movie_ID";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        int numberOfColumns = rsmd.getColumnCount();

        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = rsmd.getColumnName(i);
            columnNames.add(columnName);
        }

        while (rs.next()) {
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

    private static Connection getConnection(String connectionUrl, String username, String password) {
        return Controller.connectToSQL(connectionUrl, username, password);
    }

    public static void main(String[] args) throws SQLException {

        if (args.length < 3) {
            System.out.println("Usage: ./moviedb <connection_url> <username> <password>");
        }

        showRental(args);

    }
}
