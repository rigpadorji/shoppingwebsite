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

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class deliaddress
 */
@WebServlet("/deliaddress")
public class deliaddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public deliaddress() {
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
		out.println("This is deliaddress servlet");
		
		
		
		//retreiving address detail
		int customerid = Integer.parseInt(request.getParameter("customerid"));
		String firstname = request.getParameter("fname");
		String lastname = request.getParameter("lname");
		String postalcode= request.getParameter("postalcode");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String streetaddress = request.getParameter("streetaddress");
		
		
		
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
			String sql = "UPDATE CUSTOMER SET first_name = ?, last_name= ?, street_address = ?, city = ?, state = ?, postal_code = ? WHERE customer_id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, firstname);
            pst.setString(2, lastname);
			pst.setString(3, streetaddress);
			pst.setString(4, city);
            pst.setString(5, state);
            pst.setString(6, postalcode);
            pst.setInt(7, customerid);
            
            
            int rowaffect = pst.executeUpdate();
            
            if(rowaffect>0) {
            	System.out.println("An existing customer was updated successfully!");
            	String sql1 ="SELECT * FROM CUSTOMER WHERE customer_id=?";
            	PreparedStatement pst1 = con.prepareStatement(sql1);
            	pst1.setInt(1,customerid);
            	ResultSet rs = pst1.executeQuery();
            	
            	List<Map<String,Object>> products = new ArrayList<>();
            	
            	while(rs.next()) {
            		Map<String, Object> product = new HashMap<>();
            		
            		product.put("customerid", rs.getInt("customer_id"));
            		product.put("userid", rs.getInt("user_id"));
            		product.put("fname", rs.getString("first_name"));
 	                product.put("lname", rs.getString("last_name"));
 	                product.put("streetaddress", rs.getString("street_address"));
            		product.put("postalcode", rs.getString("postal_code"));
            		product.put("city", rs.getString("city"));
            		product.put("state", rs.getString("state"));
            		products.add(product);
            		
            	}
            	
            	request.setAttribute("products", products);

            	
            	RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/purchase.jsp");
            	rd.forward(request, response);
            	
            	
            }
            
            
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
