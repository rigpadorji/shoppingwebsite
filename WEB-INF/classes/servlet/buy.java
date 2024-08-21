package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class buy
 */
@WebServlet("/buy")
public class buy extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public buy() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("this is buy servlet");
	    
	    int productid= Integer.parseInt(request.getParameter("productid"));
	    String image = request.getParameter("imageurl");
	    int price = Integer.parseInt(request.getParameter("price"));
	    String size =  request.getParameter("size");
	    String color = request.getParameter("color");
	    int quantity = Integer.parseInt(request.getParameter("quantity"));
	    
	    String dbdriver="com.mysql.cj.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/kzangrigpa";
	    String username = "root";
	    String pass = "yeshi12345";
	    
	       
	    
	    try {
			Class.forName(dbdriver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    Connection con = null;
	    
	    try {
			con = DriverManager.getConnection(url,username,pass);
			//String sql = INSERT INTO ordes() VALUE("parentid");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
		
		
	}

}
