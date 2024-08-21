package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteCartitem
 */
@WebServlet("/DeleteCartitem")
public class DeleteCartitem extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DeleteCartitem() {
       
    	
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
		//PrintWriter out = response.getWriter();
		//out.println("This is deleteling the cart item servlet");
	   int userid = Integer.parseInt(request.getParameter("userid"));
	   int productid = Integer.parseInt(request.getParameter("productid"));
	   
	   System.out.println("the uuser id and product id at deletecart servlet");
	   System.out.print(userid);
	   System.out.print("and"+productid);
	   
	   
	   String dbDriver = "com.mysql.cj.jdbc.Driver";
       String url = "jdbc:mysql://localhost:3306/kzangrigpa";
       String username = "root";
       String password = "yeshi12345";

       // Initialize database connection
       Connection con = null;
       PreparedStatement pst = null;
       

           // Load the MySQL JDBC driver
           try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           try {
			con = DriverManager.getConnection(url,username,password);
			String deleteQuery="DELETE FROM addtocart WHERE user_id = ? AND product_id=?";
			pst= con.prepareStatement(deleteQuery);
			pst.setInt(1,userid);
			pst.setInt(2, productid);
			int rowaffected = pst.executeUpdate();
			if(rowaffected>0) {
				System.out.println("deleted successfully");
				//RequestDispatcher dis = request.getRequestDispatcher("jsp-folder/displaycartinfo.jsp");
						//dis.forward(request,response);
				
			}
			
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		   

	   
		
		
	}

}
