package servlet;

import java.io.IOException;



import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.JsonNode;


import org.json.JSONObject;

@WebServlet("/pdisonchangesize")
public class pdisonchangesize extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public pdisonchangesize() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//response.setContentType("application/json");
    	
        String dbdriver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/kzangrigpa";
        String username = "root";
        String pass = "yeshi12345";

        try {
            Class.forName(dbdriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, pass);
            String parentid = request.getParameter("parentid");
            String size = request.getParameter("size");
            String color = request.getParameter("color");
            System.out.println("the parent id reach at pdisonchange servlet is:"+parentid);
            System.out.println("the size is :"+size);
            System.out.print("the colour is:"+color);

            String sql = "SELECT krchild_id, price, quantity, color, size, imageurl, date FROM krchild WHERE parentid = ? AND size = ? AND color = ?";
            PreparedStatement psd = con.prepareStatement(sql);
           
            System.out.println("SQL Query: " + sql);
            System.out.println("parent ID: " + parentid);
            System.out.println("Size: " + size);
            System.out.println("Color: " + color);
            
            
            psd.setString(1, parentid);
            psd.setString(2, size);
            psd.setString(3, color);
            ResultSet rs = psd.executeQuery();

            JSONObject product = new JSONObject();
            if (rs.next()) {
            	product.put("krchildid", rs.getInt("krchild_id"));
                product.put("imageurl", rs.getString("imageurl"));
                product.put("price", rs.getInt("price"));
                product.put("quantity", rs.getInt("quantity"));
                product.put("color", rs.getString("color"));
                product.put("size", rs.getString("size"));
                product.put("date", rs.getDate("date").toString());
                
                System.out.println("Retrieving from database:");
                System.out.println("Product JSON object: " + product.toString());

                
            }  else {
            	
            	product.put("error", "Currently,not available.please choose different colour and size");

            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(product.toString());
            out.flush();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
  /**  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = mapper.readTree(request.getInputStream());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
            return;
        }

        // Extract values from JSON
        int productPrice = jsonNode.has("productPrice") ? jsonNode.get("productPrice").asInt() : 0;
        String productImageurl = jsonNode.has("productImageurl") ? jsonNode.get("productImageurl").asText() : "";
        int quantity = jsonNode.has("quantity") ? jsonNode.get("quantity").asInt() : 0;
        String productColour = jsonNode.has("color") ? jsonNode.get("color").asText() : "";
        String productSize = jsonNode.has("size") ? jsonNode.get("size").asText() : "";

        // Set session attributes
        HttpSession session = request.getSession();
        session.setAttribute("productPrice", productPrice);
        session.setAttribute("productImageurl", productImageurl);
        session.setAttribute("quantity", quantity);
        session.setAttribute("colour", productColour);
        session.setAttribute("size", productSize);

        // Forward to purchase.jsp or handle further logic
        RequestDispatcher rd = request.getRequestDispatcher("purchase.jsp");
        rd.forward(request, response);
    }**/

}
