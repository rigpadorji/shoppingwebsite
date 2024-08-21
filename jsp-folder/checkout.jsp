<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.HashMap,java.util.Map,java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<style>
     .purchase-container {
    max-width: 500px; /* Adjust maximum width of the container */
    margin: 20px auto;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    background-color: white;
}

.product-separator {
    border: none;
    border-top: 1px solid #ccc;
    margin: 10px 0;
}

.product-details {
    display: flex;
    align-items: center;
}

.purchase-image {
    flex: 0 0 200px; /* Fixed width for the image */
    margin-right: 20px;
}

.purchase-image img {
    max-width: 100%; /* Ensure images resize proportionally within their container */
    height: auto;
    display: block;
}

.product-text {
    flex: 1; /* Take remaining space */
}

.quantity-input {
    display: flex;
    align-items: center;
}

.quantity-input button {
    cursor: pointer;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    padding: 5px 10px;
    font-size: 16px;
    margin: 0 5px;
}

.quantity-input input {
    width: 50px;
    text-align: center;
    border: 1px solid #ccc;
    padding: 5px;
    font-size: 16px;
}

.buy {
    display: block;
    margin: 20px auto;
    background-color: blue;
    color: white;
    border: none;
    padding: 10px;
    font-size: 16px;
    cursor: pointer;
    width: 220px;
    border-radius: 5px;
}

.buy:hover {
    background-color: #218838;
}
</style>

</head>
<body>



        <%
        //int userid = Integer.parseInt(request.getParameter("userid"));
        //session.setAttribute("userid", userid);
        //System.out.println("user id at check out jsp: "+userid);
        %>
        
        <%

// Retrieving from checkout servlet 
    List<Map<String, Object>> products = (List<Map<String, Object>>) request.getAttribute("products");

    // Initialize variables to store the product details
    Integer customerid=null;
    Integer userid =null;
    String fname = "";
    String lname = "";
    String streetaddress = "";
    String city = "";
    String state = "";
    String postalcode = "";

    if (products != null && !products.isEmpty()) {
        for (Map<String, Object> product : products) {
        	fname = (String) product.get("fname");
        	lname = (String) product.get("lname");
            streetaddress = (String) product.get("streetaddress");
            city = (String) product.get("city");
            state = (String) product.get("state");
            postalcode = (String) product.get("postalcode");
            customerid = (Integer)product.get("customerid");
            customerid= (Integer)product.get("customerid");
            // Exit the loop after processing the first product
            break;
        }
    }
%>
<%
   //session to pass userid and to to cartdeliveryjsp
    session.setAttribute("fname",fname);
    session.setAttribute("lname",lname);
    session.setAttribute("postalcode",postalcode);
    session.setAttribute("state",state);
    session.setAttribute("city",city);
    session.setAttribute("streetaddress",streetaddress);
    session.setAttribute("customerid",customerid);

%>
        

<div class="purchase-container">
    <div class="section">
            <span class="section-title">Delivering to:</span>&nbsp&nbsp<%=fname%>&nbsp&nbsp<%=lname%>
            <p>〒<%=postalcode%>,<%=state%>,<%=city%>,<%=streetaddress%>  </p>
             <a href="jsp-folder/cartdelivery.jsp?userid=<%=userid%>" class="edit-link">EDIT</a>
        </div>
         <hr class="product-separator">
    
    <div class="section purchase-details">
        <span class="section-title">Total numbers of your order: (<%= request.getParameterMap().size() / 7 %>)</span><br><br>
         <hr class="product-separator">
        
        <% 
        //retreving cart item from checkout servlet and Editdelivery servlet
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) session.getAttribute("cartItems");
	    if (cartItems == null) {//ensure it is initialized
	        cartItems = new ArrayList<>();
	    }
	    // Check if the request contains any new cart items
	    Map<String, String[]> paramMap = request.getParameterMap();
	    if (!paramMap.isEmpty()) {
	        // Clear existing cart items if the request contains new cart items
	        cartItems.clear();
        
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
                //System.out.println("before editdelivery"+cartItem);
                System.out.println("after  editdelivery"+cartItem);
            }
            //setting session to pass its value to cartdelivery jsp
            session.setAttribute("cartItems",cartItems);
            //System.out.println("session before editdelivery"+cartItems);
            
        }
	    }
        for (Map<String, Object> cartItem : cartItems) {
            int productid = (int) cartItem.get("productid");
            int cartPrice = (int) cartItem.get("cartPrice");
            int cartQuantity = (int) cartItem.get("cartQuantity");
            int productPrice = (int) cartItem.get("productPrice");
            int productQuantity = (int) cartItem.get("productQuantity");
            String color = (String) cartItem.get("color");
            String size = (String) cartItem.get("size");
            String image = (String) cartItem.get("image");
            System.out.println("the image of all the product at checkout jsp: "+image);
        %>
        <div class="purchase-details">
            <div class="purchase-image">
                <img src="http://localhost:8080/kzangRigpaSeller/image/<%= image %>" alt="Product Image">
            </div>
            <div class="purchase-details">
                <p>Price: $<%= cartPrice %></p>
                <p>Product Number: <%= productid %></p>
                <p>Size: <%= size %></p>
                <p>Color: <%= color %></p>
                <p>Quantity: <%= cartQuantity %></p>
            </div>
            <hr class="product-separator">
        </div>
        <% 
        }
        %>
    </div>

    <form action="buy" method="post">
        <!-- Hidden form fields to pass attributes to buy servlet -->
        <input type="hidden" name="customerid" value="<%= request.getAttribute("userid") %>">
        
        <% 
        //hidden form to pass value to confirm
        for (Map<String, Object> cartItem : cartItems) {
            int productid = (int) cartItem.get("productid");
            int cartPrice = (int) cartItem.get("cartPrice");
            int cartQuantity = (int) cartItem.get("cartQuantity");
            int productPrice = (int) cartItem.get("productPrice");
            int productQuantity = (int) cartItem.get("productQuantity");
            String color = (String) cartItem.get("color");
            String size = (String) cartItem.get("size");
            String image = (String) cartItem.get("image");
        %>
        <input type="hidden" name="productid_<%= productid %>" value="<%= productid %>">
        <input type="hidden" name="cartPrice_<%= productid %>" value="<%= cartPrice %>">
        <input type="hidden" name="cartQuantity_<%= productid %>" value="<%= cartQuantity %>">
        <input type="hidden" name="productPrice_<%= productid %>" value="<%= productPrice %>">
        <input type="hidden" name="productQuantity_<%= productid %>" value="<%= productQuantity %>">
        <input type="hidden" name="color_<%= productid %>" value="<%= color %>">
        <input type="hidden" name="size_<%= productid %>" value="<%= size %>">
        <input type="hidden" name="image_<%= productid %>" value="<%= image %>">
        <% 
        }
        %>
        <button type="submit" class="confirm-button">Confirm Order</button>
    </form>
</div>

</body>
</html>
