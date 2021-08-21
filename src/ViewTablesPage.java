

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * Servlet implementation class ViewTables
 */
@WebServlet("/ViewTables")
public class ViewTablesPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTablesPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Utils.doLogin(request.getServletContext());
		
		//Utils.printDocument(response.getWriter(), Utils.makeTable(Tables.MEMBERS));

		
		System.out.println(Controller.username + " " + Controller.password);
		
		Body(response.getWriter(), "");
		
		// Utils.viewTableContent(Table.MEMBERS);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		StringBuilder sb = new StringBuilder();
		
		String[][] menuItems = new String[][] {
			new String[] { Utils.makeHref("/ViewTables"), "View Tables" },
			new String[] { Utils.makeHref("/InsertTuple"), "Insert Tuple" },
			new String[] { Utils.makeHref("/"), "Return to Main Menu" },
		};
		
		sb.append(Utils.makeMenu(menuItems));
		sb.append(Utils.makeTable(request.getParameter("TABLE_NAME")));
		
		Utils.printDocument(response.getWriter(), sb.toString());
	}
	
	public static void Body(PrintWriter pW, String message) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.makeMenu(new String[][] {
			{ Utils.makeHref("/"), "Go Home" }
		}));
		sb.append("<h2>View Table</h2>");
		sb.append("<p>Select a table to view</p>");
		
		// Form
		sb
		.append("<form action=\"ViewTables\" method=\"POST\">")
		.append("<select name=\"TABLE_NAME\">")
		.append("	<option value=\"MEMBERS\">Members</option>")
		.append("	<option value=\"MEMBERPROFILE\">MemberProfile</option>")
		.append("	<option value=\"ACTOR\">Actor</option>")
		.append("	<option value=\"MOVIE\">Movie</option>")
		.append("	<option value=\"MOVIE_GENRE\">Movie_genre</option>")
		.append("	<option value=\"WATCH\">Watch</option>")
		.append("	<option value=\"LIKES\">Likes</option>")
		.append("	<option value=\"STARRED_BY\">Starred_by</option>")
		.append("	<option value=\"MOVIERATINGS\">MovieRatings</option>")
		.append("</select>")
		.append("<input type=\"submit\">")
		.append("</form>");
		Utils.printDocument(pW, sb.toString());
		
	}
	

}
