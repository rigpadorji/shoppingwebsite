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
 * Servlet implementation class DisplayCartInfo
 */
@WebServlet("/displayCartinfo")
public class displayCartinfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public displayCartinfo() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to doPost method to handle post requests
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Displaying cart information:");

        // Retrieve user ID from request
        int userId = Integer.parseInt(request.getParameter("userid"));
        //creating session to pass value to displaying cartinfo jsp
         HttpSession session = request.getSession();
         session.setAttribute("userid", userId);

        // Database connection parameters
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/kzangrigpa";
        String username = "root";
        String password = "yeshi12345";

        // Initialize database connection
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName(dbDriver);

            // Establish the database connection
            con = DriverManager.getConnection(url, username, password);

            // SQL query to retrieve cart information grouped by product ID
            String sql = "SELECT a.add_tp_cart, a.user_id, a.product_id, a.price AS cart_price, a.quantity AS cart_quantity, "
                       + "k.krchild_id, k.parentid, k.KRSIN, k.price AS product_price, k.quantity AS product_quantity, "
                       + "k.color, k.size, k.imageurl, k.date "
                       + "FROM addtocart a "
                       + "JOIN krchild k ON a.product_id = k.krchild_id "
                       + "WHERE a.user_id = ? ";
                       //+ "ORDER BY a.product_id"; // Grouping by product_id and ordering results

            // Prepare the SQL statement with parameter
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            
            List<Map<String, Object>> cartinfos = new ArrayList<>();
           
           
            while (rs.next()) {
            	 Map<String, Object> cartinfo  = new HashMap<>(); // To store total quantity of each product

                cartinfo.put("productid",rs.getInt("product_id"));
                cartinfo.put("cartPrice",rs.getInt("cart_price"));
                cartinfo.put("size",rs.getString("size"));
                cartinfo.put("color",rs.getString("color"));
                cartinfo.put("cartQuantity",rs.getInt("cart_quantity"));
                cartinfo.put("image",rs.getString("imageurl"));
                cartinfo.put("productQuantity",rs.getInt("product_quantity"));
                cartinfo.put("productPrice",rs.getInt("product_price"));
                cartinfos.add(cartinfo);
               
                
                
               /** if (productTotalQuantity.containsKey(productId)) {
                    int currentTotalQuantity = productTotalQuantity.get(productId);
                    productTotalQuantity.put(productId, currentTotalQuantity + cartQuantity);
                } else {
                    productTotalQuantity.put(productId, cartQuantity);
                }**/

                // Prepare display information
                /**StringBuilder productDetails = new StringBuilder();
                productDetails.append("Product ID: ").append(productId).append(", ");
                productDetails.append("Product Name: ").append(productName).append(", ");
                productDetails.append("Cart Price: ").append(cartPrice).append(", ");
                productDetails.append("Cart Quantity: ").append(cartQuantity);**/

                // Append product details to map, grouped by product ID
               /** if (cartinfo.containsKey(productId)) {
                    cartinfo.get(productId).append("<br>").append(productDetails.toString());
                } else {
                    cartinfo.put(productId, new StringBuilder(productDetails.toString()));
                }**/
            }

            // Output grouped product details
           /** for (Map.Entry<Integer, StringBuilder> entry : cartinfo.entrySet()) {
                out.println("<h3>Product ID: " + entry.getKey() + "</h3>");
                out.println(entry.getValue().toString() + "<br>");
            }**/
            session.setAttribute("cartinfos",cartinfos);
            RequestDispatcher rd = request.getRequestDispatcher("jsp-folder/displaycartinfo.jsp");
            rd.forward(request, response);
            
            
            
            

        } catch (ClassNotFoundException | SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
            // Example: Redirect to an error page or send an error response
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving cart information.");
        } finally {
            // Close JDBC objects in finally block
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
