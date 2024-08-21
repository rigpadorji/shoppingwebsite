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

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class addToCart
 */
@WebServlet("/addToCart")
public class addToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public addToCart() {
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
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  response.setContentType("text/html");
		  response.setContentType("application/json;charset=UTF-8"); 
	      PrintWriter out = response.getWriter();
	      JSONObject json = new JSONObject();
	   
	 // reteving session attributes from product deatail jsp
	    int userid = Integer.parseInt(request.getParameter("userid"));
	    //int krchildid = Integer.parseInt(request.getParameter("krchildid"));
	    String krchildidStr = request.getParameter("krchildid");
	   // if (krchildidStr != null && !krchildidStr.isEmpty()) {
        int krchildid = Integer.parseInt(krchildidStr);
        System.out.println("the product after changing the size and color is " +krchildid);
         
	    String image = request.getParameter("imageurl");
	    int price = Integer.parseInt(request.getParameter("price"));
	    int quantity = Integer.parseInt(request.getParameter("quantity"));
	    String size= request.getParameter("size");
	    String color = request.getParameter("color");
	    System.out.println("user id "+userid);
	    System.out.println("product id "+krchildid);
	    System.out.println(image);
	    System.out.println(size);
	    System.out.println(color);
	    
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
		 PreparedStatement pst =null;
	    
	    try {
			con = DriverManager.getConnection(url,username,pass);
			//check the user_id and product_id are exist andtocart table(with this , it will check table containing same color and size of product)
			String checkQuery= "SELECT * FROM addtocart WHERE user_id = ? AND product_id=?";
			
			pst= con.prepareStatement(checkQuery);
			pst.setInt(1, userid);
			pst.setInt(2, krchildid);
			ResultSet rs = pst.executeQuery();
			
			
			if(rs.next()) {
				System.out.println("this item is already in the cart");
				String updatequery = "UPDATE addtocart SET quantity = quantity+? WHERE user_id = ? AND product_id=?";
				 pst= con.prepareStatement(updatequery);
				 pst.setInt(1, quantity);
				 pst.setInt(2, userid);
				 pst.setInt(3, krchildid);
				 int rowAffected = pst.executeUpdate();
		            if (rowAffected > 0) {
		                json.put("status", "success");
		                json.put("message", "Item quantity updated successfully.");
		            } else {
		                json.put("status", "failure");
		                json.put("message", "Failed to update item quantity.");
		            }
		            //out.print(json.toString()); 
			}else {
				System.out.println("not availabale in the cart");
			String sql = "INSERT INTO addtocart(user_id,product_id,price,quantity) VALUES(?,?,?,?)";
		     pst= con.prepareStatement(sql);
			 pst.setInt(1, userid);
			 pst.setInt(2, krchildid);
			 pst.setInt(3, price);
			 pst.setInt(4, quantity);
			 
			 int rowaffected = pst.executeUpdate();
			 
			 //JSONObject json = new JSONObject();
	            if (rowaffected > 0) {
	                json.put("status", "success");
	                json.put("message", "Item added to cart successfully.");
	            } else {
	                json.put("status", "failure");
	                json.put("message", "Failed to add item to cart.");
	            }
	            
			 }
			 // Calculate total quantity in the cart
           /** String totalQuantityQuery = "SELECT SUM(quantity) AS totalQuantity FROM addtocart WHERE user_id = ?";
            pst = con.prepareStatement(totalQuantityQuery);
            pst.setInt(1, userid);
            rs = pst.executeQuery();
            int totalQuantity = 0;
            if (rs.next()) {
            	totalQuantity = rs.getInt("totalQuantity");
            }
         // Set totalQuantity in request scope
            request.setAttribute("totalQuantity", totalQuantity);
            System.out.println("the total quantiyt in add to cart servlet is "+totalQuantity);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
           // out.print(json.toString());**/
			out.print(json.toString());
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	           // JSONObject json = new JSONObject();
	            json.put("status", "error");
	            json.put("message", "Error: " + e.getMessage());
	            out.print(json.toString());
	        } finally {
	            // Close resources
	            try {
	                if (pst != null) {
	                    pst.close();
	                }
	                if (con!= null) {
	                    con.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	   // }
	}
}
