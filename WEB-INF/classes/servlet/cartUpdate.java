package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class cartUpdate
 */
@WebServlet("/cartUpdate")
public class cartUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public cartUpdate() {
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
		
		String userId = request.getParameter("userId");
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");
        
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
				 String updateQuery = "UPDATE addtocart SET quantity = ? WHERE user_id = ? AND product_id = ?";
		            pst = con.prepareStatement(updateQuery);
		            pst.setInt(1, Integer.parseInt(quantity));
		            pst.setString(2, userId);
		            pst.setString(3, productId);

		            int rowsAffected = pst.executeUpdate();
		            if (rowsAffected > 0) {
		                response.getWriter().write("Cart updated successfully");
		            } else {
		                response.getWriter().write("Failed to update cart");
		            }
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		
		
		
		
	}

}
