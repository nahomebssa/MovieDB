

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateTuplePage
 */
@WebServlet("/UpdateTuple")
public class UpdateTuplePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTuplePage() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		BodyPOST(response.getWriter(), request.getParameter("TABLE_NAME"));
	}
	
	private void BodyPOST(PrintWriter pW, String tableName) {
		
		StringBuilder sb = new StringBuilder();
		// Form
		sb
		.append("<h3>Insert the values</h3>")
		.append("Ex: <i>value1, value2, ...</i>")
		.append("<form action=\"UpdateHandler\" method=\"POST\">")
		.append("<input type=\"hidden\" name=\"TABLE_NAME\" value="+ tableName +">")
		.append("<input type=\"text\" name=\"argv\" />")
		.append("<input type=\"submit\">")
		.append("</form>")
		.append(Utils.makeTable(tableName));
		
		//Utils.printDocument(pW, sb.toString());
		pW.append(sb.toString());
		
	}

	private void Body(PrintWriter pW, String message) {
	
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.makeMenu(new String[][] {
			{ Utils.makeHref("/"), "Go Home" }
		}));
		sb.append("<h2>Update Table</h2>");
		sb.append("<p>Select a table to update</p>");
		
		// Form
		sb
		.append("<form action=\"UpdateTuple\" method=\"POST\">")
		.append("<select name=\"TABLE_NAME\">")
		.append("	<option value=\"MEMBERS\">Members</option>")
		.append("	<option value=\"MEMBERPROFILE\">MemberProfile</option>")
		.append("	<option value=\"ACTOR\">Actor</option>")
		.append("	<option value=\"MOVIE\">Movie</option>")
		.append("	<option value=\"MOVIE_GENRE\">Movie_genre</option>")
		.append("	<option value=\"WATCH\">Watch</option>")
		.append("	<option value=\"LIKES\">Likes</option>")
		.append("	<option value=\"STARRED_BY\">Starred_by</option>")
		.append("</select>")
		.append("<input type=\"submit\">")
		.append("</form>");
		Utils.printDocument(pW, sb.toString());
		
	}

}
