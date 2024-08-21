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
import java.util.Map;
import java.util.List;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class displayingproduct
 */
@WebServlet("/displayingproduct")
public class displayingproduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public displayingproduct() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//retreving userid from loginservlet 
		HttpSession session = request.getSession();
		Integer userid  = (Integer)session.getAttribute("userid");
		//int userid;
		//if(useridstr !=null && !useridstr.isEmpty()) {
			//userid = Integer.parseInt(useridstr);
			System.out.println(userid);
			
			//creating another session of userid to pass it value to index.jsp
			session.setAttribute("userid", userid);
			
		//}
		
		
		
		
	    String dbdriver = "com.mysql.cj.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/kzangrigpa";
	    String username = "root";
	    String pwd = "yeshi12345";
	    
	    Connection con = null;
	    
	    try {
			Class.forName(dbdriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    	try {
				con =DriverManager.getConnection(url, username,pwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	 
	    	String sql = "SELECT krparent_id,title,description,category,price,mainimage FROM krparent ";
	    	
	    	try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet result = pst.executeQuery();
			
			 List<Map<String, Object>> products = new ArrayList<>();

	            while (result.next()) {
	                Map<String, Object> product = new HashMap<>();
	                product.put("krparentid", result.getInt("krparent_id"));
	                product.put("title", result.getString("title"));
	                product.put("description", result.getString("description"));
	                product.put("category", result.getString("category"));
	                product.put("price", result.getInt("price"));
	                product.put("mainimage", result.getString("mainimage"));
	                //product.put("krchildid", result.getInt("krchild_id"));
	                
	                products.add(product);
	            }
	            request.setAttribute("products", products);
	        
	           
	            //displaying the cart item in cart
	            String totalQuantityQuery = "SELECT SUM(quantity) AS totalQuantity FROM addtocart WHERE user_id = ?";
	            pst = con.prepareStatement(totalQuantityQuery);
	            pst.setInt(1, userid);
	            ResultSet rs1 = pst.executeQuery();
	            int totalQuantity = 0;
	            if (rs1.next()) {
	            	totalQuantity = rs1.getInt("totalQuantity");
	            }
	            // Set totalQuantity in request scope
	            request.setAttribute("totalQuantity", totalQuantity);
	            System.out.println("the total quantiyt in add to cart servlet is "+totalQuantity);
	        
	            
	            RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/index.jsp");
	            rd.forward(request, response);
	            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
