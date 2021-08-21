import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DBMethods {

	public static String[] listOfTables() {
		
		return new String[] {
			Tables.MEMBERS.name(),
			Tables.MEMBERPROFILE.name(),
			Tables.MOVIE.name(),
			Tables.ACTOR.name(),
			Tables.WATCH.name(),
			Tables.MOVIE_GENRE.name(),
			Tables.LIKES.name(),
			Tables.STARRED_BY.name(),
		};
		
	}
	
	public static void showTableContent(Tables table) {
		
	}
	
	// TODO: check for primary key constraint & foreign key constraint
	// TODO: Test for trigger to calculate the average rating of a movie when new rating is inserted
	// TODO: Test for trigger to check that the number of profiles does not exceed 5
	public static boolean insertNewTuple(Tables table, String argv) {
		return false;
	}
	
	// TODO: check for primary key constraint & foreign key constraint
	public static boolean updateTuple(Tables table, String argv) {
		return false;
	}
	
	private static String _____PRIVATE_METHODS_____ = "PRIVATE_METHODS";
	
	private static int numberOfColumns(Tables table) {
		switch (table) {
			case MEMBERS:
				return 0;
			case MEMBERPROFILE:
				return 0;
			case MOVIE:
				return 0;
			case ACTOR:
				return 0;
			case WATCH:
				return 0;
			case MOVIE_GENRE:
				return 0;
			case LIKES:
				return 0;
			case STARRED_BY:
				return 0;
			default:
				return -1;
		}
	}
	
}
