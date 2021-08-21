

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RootPage
 */
@WebServlet("/")
public class RootPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter pW = response.getWriter();
		String[][] menuItems = new String[][] {
			new String[] { Utils.makeHref("/ListTables"), "List Tables" },
			new String[] { Utils.makeHref("/ViewTables"), "View Tables" },
			new String[] { Utils.makeHref("/InsertTuple"), "Add Record" },
			new String[] { Utils.makeHref("/UpdateTuple"), "Update Record" },
			new String[] { Utils.makeHref("/DeleteRecord"), "Delete Record" },
			new String[] { Utils.makeHref("/InsertValue"), "Search for Movies Based on Title" },
			new String[] { Utils.makeHref("/InsertValue"), "Search for Movies Based on Actor" },
			new String[] { Utils.makeHref("/InsertValue"), "Show Rental History" },
			
			// (movie name, year, and average rating)
			// You should use partial matching instead of exact matching
			// for strings.
		};
		
		ServletContext context = request.getServletContext();
		String[] uAP = Utils.getUsernameAndPassword(context);
		String username = uAP[0], password = uAP[1];
		
		
		StringBuilder sb = new StringBuilder();
		
		sb
		.append("<code>")
		.append("<b>Username: </b>")
		.append(username)
		.append("<br />")
		.append("<b>Password: </b>")
		.append(password)
		.append("<br />")
		.append("<a href=" + Utils.makeHref("/login") + ">Login</a>")
		.append("</code>")
		.append(Utils.makeMenu(menuItems));
		
		Utils.printDocument(pW, sb.toString());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
