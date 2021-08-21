

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowMoviesTitlePage
 */
@WebServlet("/ShowMoviesTitle")
public class ShowMoviesTitlePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMoviesTitlePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Body(response.getWriter(), "");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		
		String message = "";
		
		BodyPOST(response.getWriter(), message);
	}

	
	public static void Body(PrintWriter pW, String message) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.makeMenu(new String[][] {
			{ Utils.makeHref("/"), "Go Home" }
		}));
		sb.append("<h2>Search Movie by Title</h2>");
//		sb.append("<p>Select a table to view</p>");
		
		// Form
		sb
		.append("<form action=\"ShowMoviesTitle\" method=\"POST\">")
		.append("<input type=\"text\" placeholder=\"Movie Title\" >")
		.append("<input type=\"submit\">")
		.append("</form>");
		Utils.printDocument(pW, sb.toString());
		
	}

	public static void BodyPOST(PrintWriter pW, String title) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.makeMenu(new String[][] {
			{ Utils.makeHref("/"), "Go Home" }
		}));
		sb.append("<h2>Search Movie by Title</h2>");
	//	sb.append("<p>Select a table to view</p>");
		

		try {
			java.util.ArrayList[] movies = Controller.getMovieByTitle(title);
//			Utils.makeTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Form
//		sb.append(Utils.makeTable());
		Utils.printDocument(pW, sb.toString());
		
	}
	
}
