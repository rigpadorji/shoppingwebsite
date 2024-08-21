package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class producDetail
 */
@WebServlet("/producDetail")
public class producDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public producDetail() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    
	  //retreving userid from index jsp
  		HttpSession session = request.getSession();
  		Integer userid  = (Integer)session.getAttribute("userid");
  		System.out.println("user id from productdeatail servlet"+userid);
			
			//creating another session of userid to pass it value to productdetail jsp
			session.setAttribute("userid", userid);
			
	    
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
			String krparentid = request.getParameter("krparentid");
			String sql = "select krchild_id,parentid,price,quantity,color,size,imageurl,date from  krchild  where parentid =? LIMIT 1";
			
			
		    PreparedStatement psd = con.prepareStatement(sql);
		    psd.setString(1,krparentid);
		    ResultSet rs = psd.executeQuery();
		    
		    while(rs.next()) {
		    	int krchildid = rs.getInt("krchild_id");
			   request.setAttribute("krchildid",krchildid);	
		       int parentid = rs.getInt("parentid");
		       request.setAttribute("parentid",parentid);	
		    	
		    String imageurl = rs.getString("imageurl");
		    request.setAttribute("imageurl", imageurl);
		    
		    int price = rs.getInt("price");
		    request.setAttribute("price",price);
		    
		    int quantity = rs.getInt("quantity");
		    request.setAttribute("quantity",quantity);
		    
		    String color = rs.getString("color");
		    request.setAttribute("color", color);
		    
		    Date  date= rs.getDate("date");
		    request.setAttribute("date", date);
		    
		    }
		    
		    RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/productdetail.jsp");
		    rd.forward(request, response);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	   
		
	}
}

