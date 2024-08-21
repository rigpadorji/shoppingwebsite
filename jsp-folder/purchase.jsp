<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .purchase-container {
        margin-top: 230px;
        padding: 20px;
        width: 700px;
        background-color: white;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: center;
    }
    .purchase-image img {
        
        width: 350px;
        height: 300px;
        display: block;
        margin: 0 auto;
        border-radius: 5px;
    }
    .purchase-details, .payment-method {
        margin-top: 20px;
    }
    .section {
        margin-top: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid #ccc;
    }
     .section1 {
        margin-top: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid #ccc;
        }
    .section-title {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 10px;
        color: #333;
    }
    .edit-link {
        color: #007bff;
        text-decoration: none;
        margin-left: 10px;
        font-size: 14px;
    }
    .edit-link:hover {
        text-decoration: underline;
    }
    .confirm-button {
        margin-top: 20px;
        padding: 10px 20px;
        background-color: #28a745;
        color: white;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        font-size: 16px;
        transition: background-color 0.3s;
    }
    .confirm-button:hover {
        background-color: #218838;
    }
</style>
<title>Order Confirmation</title>
</head>
<body>

<%
    // reeceiving from purchase servvlet and  set an session attribute
    session.setAttribute("parentid", request.getParameter("productid"));
    session.setAttribute("imageUrl", request.getParameter("productImageURL"));
    session.setAttribute("price", request.getParameter("productPrice"));
    session.setAttribute("size", request.getParameter("size"));
    session.setAttribute("color", request.getParameter("color"));
    session.setAttribute("quantity", request.getParameter("quantity"));
%>

<%
    // Retrieve data from session attributes from above
    String parentidStr = (String) session.getAttribute("parentid");
    Integer parentid = null;
    if (parentidStr != null) {
        try {
            parentid = Integer.parseInt(parentidStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    String imageUrl = (String) session.getAttribute("imageUrl");
    String price = (String) session.getAttribute("price");
    String size = (String) session.getAttribute("size");
    String color = (String) session.getAttribute("color");
    String quantity = (String) session.getAttribute("quantity");
%>

  

<%
    // Retrieving from purchase servlet 
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
   //session to pass userid and to to deliaddress jsp
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
            <span class="section-title">Delivering to:</span>&nbsp&nbsp<%= fname %>&nbsp&nbsp<%= lname %>
            <p>〒<%= postalcode %>,<%= state %>,<%= city %>,<%= streetaddress %>  </p>
            <a href="jsp-folder/deliaddress.jsp" class="edit-link">EDIT</a>
        </div>
        <div class="section1">
            <span class="section-title">Expected Deliver</span>
            <br>
        </div>

        <div class="section purchase-details">
            <span class="section-title">Number of your order(<%= quantity%>)</span><br><br>
            <div class="purchase-image">
                <img src="http://localhost:8080/kzangRigpaSeller/image/<%= imageUrl %>" alt="Product Image">
            </div>
            <div class="purchase-details">
                <p>Price: $<%= price %></p>
                <p>Product Number: <%= parentid %></p>
                <p>Size: <%= size %></p>
                <p>Color: <%= color %></p>
                <p>Quantity: <%= quantity %></p>
            </div>
        </div>
        

        <div class="section payment-method">
            <span class="section-title">Payment Method</span>
            <br>
            <a href="" class="edit-link"><b>Credit Card</b></a>
        </div>
        
        <form action="buy" method="post">
            <!-- Hidden form fields to pass attributes to buy servlet -->
            <input type="hidden" name="customerid" value="<%= customerid %>">
            <input type="hidden" name="productid" value="<%= parentid %>">
            <input type="hidden" name="image" value="<%= imageUrl %>">
            <input type="hidden" name="price" value="<%= price %>">
            <input type="hidden" name="size" value="<%= size %>">
            <input type="hidden" name="color" value="<%= color %>">
            <input type="hidden" name="quantity" value="<%= quantity %>">
            <button type="submit" class="confirm-button">Confirm Order</button>
        </form>
    </div>
</body>
</html>
