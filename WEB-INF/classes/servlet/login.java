package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.logindao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
			try {
				Integer userId = logindao.check(username, password);
		        
				if(userId!=null) {
					//creating session for user to take its id to all the page
					HttpSession session = request.getSession();
					session.setAttribute("userid", userId);
			           
					
					RequestDispatcher dis = request.getRequestDispatcher("/displayingproduct");
					dis.forward(request, response);
					
				}else {
					
					out.println("check your username or password");

				  }
			} catch (IOException | SQLException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
}
	


