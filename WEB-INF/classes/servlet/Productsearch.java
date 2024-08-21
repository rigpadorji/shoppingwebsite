package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Productsearch
 */
@WebServlet("/Productsearch")
public class Productsearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Productsearch() {
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
		  
		    String dbdriver="com.mysql.cj.jdbc.Driver";
		    String url = "jdbc:mysql://localhost:3306/kzangrigpa";
		    String username = "root";
		    String pass = "yeshi12345";
		    
		    String searchid = request.getParameter("searchid");
	        String message = "";

	        if (searchid != null && !searchid.trim().isEmpty()) {
	            try {
	                Class.forName(dbdriver);
	                Connection conn = DriverManager.getConnection(url, username, pass);

	                String sql = "SELECT * FROM krparent WHERE krparent_id = ? OR Description LIKE ? OR title LIKE ?";
	                PreparedStatement stmt = conn.prepareStatement(sql);
	                stmt.setString(1, searchid); // Assuming ID is stored as a string (or you can convert it if needed)
	                stmt.setString(2, "%" + searchid + "%");
	                stmt.setString(3, "%" + searchid + "%");

	                ResultSet rs = stmt.executeQuery();
	                
	                List<Map<String, Object>> products = new ArrayList<>();
	                while (rs.next()) {
	                    Map<String, Object> product = new HashMap<>();
		                product.put("krparentid", rs.getInt("krparent_id"));
		                product.put("title", rs.getString("title"));
		                product.put("description", rs.getString("description"));
		                product.put("price", rs.getInt("price"));
		                product.put("mainimage", rs.getString("mainimage"));
		                
		                products.add(product);
	                }
	                
	                request.setAttribute("products", products);
		            RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/index.jsp");
		            rd.forward(request, response);

	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	                message = "Error searching for product.";
	            }
	        } else {
	            message = "Please enter a product name or ID.";
	        }

	       // request.setAttribute("products", products);
	       // request.setAttribute("message", message);
	        //request.getRequestDispatcher("search.jsp").forward(request, response);

		    
		
	}

}
