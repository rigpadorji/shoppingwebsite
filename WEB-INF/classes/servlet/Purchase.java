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
 * Servlet implementation class Purchase
 */
@WebServlet("/Purchase")
public class Purchase extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Purchase() {
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
		out.println("this is purchase servlet");
		
		//retreving userid from productdetail jsp
		HttpSession session = request.getSession();
  		Integer userid  = (Integer)session.getAttribute("userid");
  		System.out.println("user id from purchase servlet"+userid);
			
		//creating another session of userid to pass it value to productdetail jsp
		//session.setAttribute("userid", userid);
  		
  		//retreving from the productdetail 
  		int productid = Integer.parseInt(request.getParameter("productid"));
  		String image = request.getParameter("productImageURL");
  		int  price = Integer.parseInt(request.getParameter("productPrice"));
  		int  quantity = Integer.parseInt(request.getParameter("quantity"));
  		String size = request.getParameter("size");
  		String color= request.getParameter("color");
  		
  		//creating session after receive from product detail to pass to purchase jsp
  		
  		session.setAttribute("productid", productid);
  		session.setAttribute("image", image);
  		session.setAttribute("price", price);
  		session.setAttribute("quantity", quantity);
  		session.setAttribute("size", size);
  		session.setAttribute("color", color);
  		
  		
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
            RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/purchase.jsp");
            rd.forward(request, response);

	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	   
		
	}

}
