package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Checkout() {
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
		PrintWriter out = response.getWriter();
		out.println("this is checkout servlet");
		
		
		//retreving hidden value from displaycartinfo jsp and pass as session attrribute to checkout jsp
		 List<Map<String, Object>> cartItems = new ArrayList<>();
	        Map<String, String[]> paramMap = request.getParameterMap();
	        
	        for (String key : paramMap.keySet()) {
	            if (key.startsWith("productid_")) {
	                int productid = Integer.parseInt(paramMap.get(key)[0]);
	                int cartPrice = Integer.parseInt(request.getParameter("cartPrice_" + productid));
	                int cartQuantity = Integer.parseInt(request.getParameter("cartQuantity_" + productid));
	                int productPrice = Integer.parseInt(request.getParameter("productPrice_" + productid));
	                int productQuantity = Integer.parseInt(request.getParameter("productQuantity_" + productid));
	                String color = request.getParameter("color_" + productid);
	                String size = request.getParameter("size_" + productid);
	                String image = request.getParameter("image_" + productid);
	                
	                Map<String, Object> cartItem = new HashMap<>();
	                cartItem.put("productid", productid);
	                cartItem.put("cartPrice", cartPrice);
	                cartItem.put("cartQuantity", cartQuantity);
	                cartItem.put("productPrice", productPrice);
	                cartItem.put("productQuantity", productQuantity);
	                cartItem.put("color", color);
	                cartItem.put("size", size);
	                cartItem.put("image", image);
	                
	                cartItems.add(cartItem);
	            }
	            HttpSession session = request.getSession();
	            session.setAttribute("cartItems",cartItems);
	        }
	        //retreving userid from displaycartinfo jsp to select the delivary address from customer table using user id
	        int  userid = Integer.parseInt(request.getParameter("userid"));
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
		    	String sql = "SELECT * from CUSTOMER WHERE user_id=?";
		    	
		    	PreparedStatement pst = con.prepareStatement(sql);
		    	pst.setInt(1,userid);
		    	ResultSet rs = pst.executeQuery();
		    	
		    	 List<Map<String, Object>> products = new ArrayList<>();
		    	while(rs.next()) {
		    		
		    		 Map<String, Object> product = new HashMap<>();
		    		    product.put("customerid", rs.getInt("customer_id"));
		                product.put("userid", rs.getInt("user_id"));
		                product.put("fname", rs.getString("first_name"));
		                product.put("lname", rs.getString("last_name"));
		               // product.put("email", rs.getString("email"));
		                //product.put("phone", rs.getString("phone"));
		                product.put("streetaddress", rs.getString("street_address"));
		                product.put("city", rs.getString("city"));
		                product.put("state", rs.getString("state"));
		                product.put("postalcode", rs.getString("postal_code"));
		                product.put("country", rs.getString("country"));
		               // product.put("created_at", rs.getTimestamp("created_at"));
		               // product.put("updated_at", rs.getTimestamp("updated_at"));

		                // Add the product map to the list
		                products.add(product);
		                
		    	}
		    	
		    	 // Set the products list as a request attribute
	            request.setAttribute("products", products);

	            // Forward the request to the JSP
	            RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/checkout.jsp");
	            rd.forward(request, response);

		    	
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }
		    
		   
			
		}
}