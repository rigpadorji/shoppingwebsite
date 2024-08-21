package servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import bean.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checkoutServlet
 */
@WebServlet("/checkoutServlet")
public class checkoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public checkoutServlet() {
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
		Map<Integer, CartItem> cartItems = new HashMap<>();
	        
	        Enumeration<String> parameterNames = request.getParameterNames();
	        while (parameterNames.hasMoreElements()) {
	            String paramName = parameterNames.nextElement();
	            if (paramName.startsWith("productid_")) {
	                int productId = Integer.parseInt(paramName.substring("productid_".length()));
	                int cartPrice = Integer.parseInt(request.getParameter("cartPrice_" + productId));
	                int cartQuantity = Integer.parseInt(request.getParameter("cartQuantity_" + productId));
	                String color = request.getParameter("color_" + productId);
	                String size = request.getParameter("size_" + productId);
	                String image = request.getParameter("image_" + productId);
	                
	                // Create CartItem object or process as needed
	                CartItem cartItem = new CartItem(productId, cartPrice, cartQuantity, color, size,image);
	               cartItems.put(productId, cartItem);
	            }
	        }
	        
	        // Example: Redirect to checkout.jsp or process further
	       // request.setAttribute("cartItems", cartItems);
	        request.getRequestDispatcher("jsp-folder/checkout.jsp").forward(request, response);
	    }
	}