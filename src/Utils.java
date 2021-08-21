import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import java.io.*;

public class Utils {
	
	private static String RESOURCE_FILE_PATH = "/db_connection_creds.txt";
	private static String HREF_PREFIX = "http://localhost:8080/MovieDBv3";

	public static void doLogin(javax.servlet.ServletContext context) {
		
		String[] uap = getUsernameAndPassword(context);
		Controller.login(uap[0], uap[1]);
		
	}
	
	public static String[] setUsernameAndPassword(ServletContext context, String username, String password) {
		
		String[] usernameAndPassword = new String[] { username, password };
		if (username == null || password == null) {
			return usernameAndPassword;
		}
		
		try {
			String resourcePath = context.getRealPath(RESOURCE_FILE_PATH);
			PrintWriter entriesPrintWriter = new PrintWriter(new FileWriter(resourcePath, false), true);
			entriesPrintWriter.println(String.format("%s %s", username, password));
		    entriesPrintWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
			e.printStackTrace();
		}
		
		Controller.login(usernameAndPassword[0], usernameAndPassword[1]);
		return usernameAndPassword;
	}
	
	public static String[] getUsernameAndPassword(javax.servlet.ServletContext context) {

		String username = null;
		String password = null;
		String[] usernameAndPassword = new String[] { username, password };
		String VALUE_SEPARATOR = " ";
		
		try {
			String resourcePath = context.getRealPath(RESOURCE_FILE_PATH);
			File file = new File(resourcePath);
			if (!file.exists()) {
				return usernameAndPassword;
			}

			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line = bufferedReader.readLine();
			if (line != null) {
				usernameAndPassword = line.split(VALUE_SEPARATOR);
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
		return usernameAndPassword;
	}

	public static void respondWithMessage(PrintWriter pW, String message) {
		pW.append("<!DOCTYPE html>").append("<html>").append("<head>").append("<title>MovieDB</title>")
				.append("</head>").append("<body>")

				// Result
				.append("<div>").append(String.format("	<code>%s</code>", message)).append("</div>")

				// Menu
				.append("<ul>");
		String[][] menuItems = new String[][] { new String[] { makeHref("/MainPage"), "Go Home" } };
		for (String[] menuItem : menuItems)
			pW.append(String.format("	<li><a href=\"%s\">%s</a></li>", menuItem[0], menuItem[1]));
		pW.append("</ul>")

				.append("</body>").append("</html>");
	}

	public static String makeHref(String path) {
		return Utils.HREF_PREFIX + path;
	}

	public static String globalCssStyles() {
		return (new StringBuilder().append("<style>").append("	table, th, td { border: 1px solid; }")
				.append("	li { margin: 10px; padding: 5px; }").append("	input, select { zoom: 1.25; }").append("</style>").toString());
	}

	public static String tableToString(Tables table) {
		switch (table) {
		case MEMBERS:
			return "Members";
		case MEMBERPROFILE:
			return "MemberProfile";
		case MOVIE:
			return "Movie";
		case ACTOR:
			return "Actor";
		case WATCH:
			return "Watch";
		case MOVIE_GENRE:
			return "Movie_genre";
		case LIKES:
			return "Likes";
		case STARRED_BY:
			return "Starred_by";
		default:
			return null;
		}
	}

	private static ArrayList[] getTableContent(Tables table) throws SQLException {

		ArrayList<String> columnNames = new ArrayList<>();
		ArrayList<String[]> rows = new ArrayList<>();

			Connection conn = Controller.connectToSQL(Controller.username, Controller.password);
			String query = String.format("SELECT * FROM %s", tableToString(table));
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();
			//System.out.println("numberOfColumns: " + numberOfColumns);

			for (int i = 1; i <= numberOfColumns; i++) {
				String columnName = rsmd.getColumnName(i);
				columnNames.add(columnName);
			}
			while (rs.next()) {
				String[] row = new String[numberOfColumns];
				for (int i = 1; i <= numberOfColumns; i++) {
					String columnValue = rs.getString(i);
					row[i - 1] = columnValue;
					//System.out.print(i + " " + columnValue + " ");
				}
				rows.add(row);
				//System.out.println("");
			}

			stmt.close();
			conn.close();

			return new ArrayList[] { columnNames, rows };

	}

	public static String makeTable(String sTable) {
		Tables table;
		switch (sTable.toUpperCase()) {
		case "MEMBERS":
		    table = Tables.MEMBERS;
		    break;
		case "MEMBERPROFILE":
		    table = Tables.MEMBERPROFILE;
		    break;
		case "ACTOR":
		    table = Tables.ACTOR;
		    break;
		case "MOVIE":
		    table = Tables.MOVIE;
		    break;
		case "MOVIE_GENRE":
		    table = Tables.MOVIE_GENRE;
		    break;
		case "WATCH":
		    table = Tables.WATCH;
		    break;
		case "LIKES":
		    table = Tables.LIKES;
		    break;
		case "STARRED_BY":
		    table = Tables.STARRED_BY;
		    break;
		default:
			table = null;
			break;
		}
		
		return makeTable(table);
	}
	
	public static String makeTable(Tables table) {

		ArrayList[] tableContent;
		ArrayList<String> columnNames;
		ArrayList<String[]> rows;
		
		//Table t = Controller.fetchTable(table);

		try {
			tableContent = getTableContent(table);
			if (tableContent == null)
				return null;
			columnNames = tableContent[0];
			rows = tableContent[1];
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			e.printStackTrace();
			return e.toString();
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<table>");
		sb.append("	<thead>");
		sb.append("<tr>");

		for (String columnName : columnNames)
			sb.append("<td>" + columnName + "</td>");
		sb.append("</tr>");

		sb.append("	</thead>");
		sb.append("	<tbody>");

		for (String[] row : rows) {
			sb.append("<tr>");
			for (String columnValue : row)
				sb.append("<td>" + columnValue + "</td>");
			sb.append("</tr>");
		}

		sb.append("	</tbody>");
		sb.append("</table>");

		return sb.toString();
	}
	
	public static Tables tablesFromString(String sTable) {
		Tables table;
		switch (sTable.toUpperCase()) {
		case "MEMBERS":
		    table = Tables.MEMBERS;
		    break;
		case "MEMBERPROFILE":
		    table = Tables.MEMBERPROFILE;
		    break;
		case "ACTOR":
		    table = Tables.ACTOR;
		    break;
		case "MOVIE":
		    table = Tables.MOVIE;
		    break;
		case "MOVIE_GENRE":
		    table = Tables.MOVIE_GENRE;
		    break;
		case "WATCH":
		    table = Tables.WATCH;
		    break;
		case "LIKES":
		    table = Tables.LIKES;
		    break;
		case "STARRED_BY":
		    table = Tables.STARRED_BY;
		    break;
		default:
			table = null;
			break;
		}
		
		return table;
	}

	public static void printDocument(PrintWriter pW) {
		printDocument(pW, "");
	}

	public static void printDocument(PrintWriter pW, String content) {

		pW.append("<!DOCTYPE html>").append("<html>").append("<head>").append("<title>MovieDB</title>")
				.append("</head>").append(Utils.globalCssStyles()).append("<body>")

				.append("<div>").append("	<h1>Welcome to the Movie Database System</h1>")
				.append("	<h3>Nahom Ebssa & Karina Hernandez</h3>").append("</div>")

				.append(content == null ? "oops somethings wrong..." : content)

				.append("</body>").append("</html>");

	}

	public static String makeMenu(String[][] menuItems) {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		for (String[] menuItem : menuItems) {
			sb.append(String.format("<li><a href=\"%s\">%s</a></li>", menuItem[0], menuItem[1]));
		}
		sb.append("</ul>");
		return sb.toString();
	}
	
	public static String makeList(String[] listItems) {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		for (String listItem : listItems) {
			sb.append(String.format("<li>%s</li>", listItem));
		}
		sb.append("</ul>");
		return sb.toString();
	}

	public static String getTableColumns() {
		return "<table>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Members</b></td>\r\n"
				+ "    <td>member_id</td>\r\n"
				+ "    <td>first_name</td>\r\n"
				+ "    <td>last_name</td>\r\n"
				+ "    <td>card_number</td>\r\n"
				+ "    <td>exp_data</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>MemberProfile</b></td>\r\n"
				+ "    <td>member_id</td>\r\n"
				+ "    <td>profile_name</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Movie</b></td>\r\n"
				+ "    <td>movie_id</td>\r\n"
				+ "    <td>movie_name</td>\r\n"
				+ "    <td>movie_year</td>\r\n"
				+ "    <td>producer</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>MovieRating</b></td>\r\n"
				+ "    <td>movie_id</td>\r\n"
				+ "    <td>movie_rating</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Actor</b></td>\r\n"
				+ "    <td>actor_id</td>\r\n"
				+ "    <td>first_name</td>\r\n"
				+ "    <td>last_name</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Watch</b></td>\r\n"
				+ "    <td>member_id</td>\r\n"
				+ "    <td>profile_name</td>\r\n"
				+ "    <td>movie_id</td>\r\n"
				+ "    <td>rating</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Movie_Genre</b></td>\r\n"
				+ "    <td>movie_id</td>\r\n"
				+ "    <td>movie_genre</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Likes</b></td>\r\n"
				+ "    <td>member_id</td>\r\n"
				+ "    <td>profile_name</td>\r\n"
				+ "    <td>movie_genre</td>\r\n"
				+ "  </tr>\r\n"
				+ "  <tr>\r\n"
				+ "    <td><b>Starred_By</b></td>\r\n"
				+ "    <td>movie_id</td>\r\n"
				+ "    <td>actor_id</td>\r\n"
				+ "  </tr>\r\n"
				+ "</table>";
	}
}
