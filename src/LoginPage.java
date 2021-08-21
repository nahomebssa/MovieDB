

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginPage
 */
@WebServlet("/login")
public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Utils.printDocument(
			response.getWriter(),
			new StringBuilder()
			.append("<form action=" + Utils.makeHref("/login") + " method=\"POST\">")
			.append("<input type=text name=USERNAME />")
			.append("<input type=password name=PASSWORD />")
			.append("<input type=submit value=Login />")
			.append("</form>")
			.toString()
		);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String USERNAME = request.getParameter("USERNAME");
		String PASSWORD = request.getParameter("PASSWORD");
		
		Utils.setUsernameAndPassword(request.getServletContext(), USERNAME, PASSWORD);
		Utils.doLogin(request.getServletContext());
		
		response.sendRedirect(Utils.makeHref("/"));
		
	}

}
