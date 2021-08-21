

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertTuplePage
 */
@WebServlet("/InsertTuple")
public class InsertTuplePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertTuplePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//Utils.doLogin(request.getServletContext());
		Body(response.getWriter(), "");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		String tableName = request.getParameter("TABLE_NAME");
		String argv = request.getParameter("argv");
		Tables table = Utils.tablesFromString(tableName);
		
		try {
			Controller.insertRecord(table, argv);
			Body(response.getWriter(), "<p>Success!</p>");
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Body(response.getWriter(), "<p>Constraint violation!</p>" + "<br />" + e.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String message = "uh-oh";
			if (e.getMessage().contains("TOOMANYPROFILES"))
				message = "<p>Too many profiles for that member!</p>" + "<br />" + e.toString();
			else if (e.getMessage().contains("Missing IN or OUT"))
				message = "<p>Incorrect values!</p>" + "<br />" + e.toString();
			Body(response.getWriter(), message);
		}
			
//		response.sendRedirect(Utils.makeHref("/InsertTuple"));
	}

	
	private static void Body(PrintWriter pW, String message) {		
		String[][] menuItems = new String[][] {
			new String[] { Utils.makeHref("/ViewTables"), "View Tables" },
			new String[] { Utils.makeHref("/"), "Go Back Home" }
		};
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div style=\"display: flex;\">");
		sb.append("<div style=\"flex: 1;\">");
		sb.append(Utils.getTableColumns());
		sb.append("</div>");
		
		sb.append("<div style=\"flex: 1;\">");
		sb.append(message);
		sb
		.append(Utils.makeMenu(menuItems))
		.append("<div>")
		.append("Enter values separated by commas, for example: <br />")
		.append("<i>value1, value2, ...</i>")
		.append("</div>")
		.append("<form action=" + Utils.makeHref("/InsertTuple") + " method=\"POST\">")
		.append("<select name=\"TABLE_NAME\">");
		
		for (String tableName : DBMethods.listOfTables()) {
			sb.append(String.format("<option value=\"%s\">%s</option>", tableName, tableName));
		}
		sb
		.append("</select>")
		.append("<input type=\"text\" name=\"argv\" placeholder=\"Name\" />")
		.append("<input type=\"submit\" />")
		.append("</form>");
		sb.append("</div>");
		sb.append("</div>");
		
		Utils.printDocument(pW, sb.toString());
	}
}
