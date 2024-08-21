<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>



<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <style>
       .product-container {
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

.product-image {
    flex: 0 0 200px; /* Fixed width for the image */
    margin-right: 20px;
}

.product-image img {
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
    
    <script>
    function increaseQuantity(productId, inputId, maxValue, userId) {
        var input = document.getElementById(inputId);
        var currentValue = parseInt(input.value);
        if (currentValue < maxValue) {
            input.value = currentValue + 1;
            updateCart(productId, input.value, userId);//call updateCart function
        }
    }

    function decreaseQuantity(productId, inputId, userId) {
        var input = document.getElementById(inputId);
        var currentValue = parseInt(input.value);
        if (currentValue > 1) {
            input.value = currentValue - 1;
            updateCart(productId, input.value, userId);//call updateCart function
        }
    }

    function updateCart(productId, newQuantity, userId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "cartUpdate", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                console.log("Cart updated successfully");
                // Optionally handle the response from the server here
            }
        };
        xhr.send("userId=" + userId + "&productId=" + productId + "&quantity=" + newQuantity);
    }
   
    </script>
</head>
<body>

<%
//retreving user from displaycartinfo servlet
Integer userid = (Integer) session.getAttribute("userid");
System.out.println("user id at displaycartinfo jsp "+userid);


%>

    <div class="product-container">
    <% 
    List<Map<String, Object>> cartinfos = (List<Map<String, Object>>) session.getAttribute("cartinfos");
    int totalQuantity = 0;
    if (cartinfos != null && !cartinfos.isEmpty()) {
        for (Map<String, Object> cartinfo : cartinfos) {
            int productid = (int) cartinfo.get("productid");
            int cartPrice = (int) cartinfo.get("cartPrice");
            int cartQuantity = (int) cartinfo.get("cartQuantity");
            int productPrice = (int) cartinfo.get("productPrice");
            int productQuantity = (int) cartinfo.get("productQuantity");
            String color = (String) cartinfo.get("color");
            String size = (String) cartinfo.get("size");
            String image = (String) cartinfo.get("image");
            totalQuantity += cartQuantity;
        }
    %>
    <p>Total Number of your order(<%= totalQuantity %>)</p>
    <hr class="product-separator">
    <% 
        for (Map<String, Object> cartinfo : cartinfos) {
            int productid = (int) cartinfo.get("productid");
            int cartPrice = (int) cartinfo.get("cartPrice");
            int cartQuantity = (int) cartinfo.get("cartQuantity");
            int productPrice = (int) cartinfo.get("productPrice");
            int productQuantity = (int) cartinfo.get("productQuantity");
            String color = (String) cartinfo.get("color");
            String size = (String) cartinfo.get("size");
            String image = (String) cartinfo.get("image");
    %>
    <div class="product-details">
        <hr class="product-separator">
        <div class="product-image">
            <img src="http://localhost:8080/kzangRigpaSeller/image/<%= image %>" alt="Product Image">
        </div>
        <div class="product-text">
            <p>Product Number: <%= productid %></p>
            <p>Price: $<%= cartPrice %></p>
            <p>Size: <%= size %></p>
            <p>Color: <%= color %></p>
            
            <div class="quantity-input">
             <p>Quantity:</p>
            <button type="button" onclick="decreaseQuantity('<%= productid %>', 'quantityInput_<%= productid %>', '<%= userid %>')">-</button>
            <input type="text" id="quantityInput_<%= productid %>" name="quantity_<%= productid %>" min="1" max="<%= productQuantity %>" value="<%= cartQuantity %>" readonly>
            <button type="button" onclick="increaseQuantity('<%= productid %>', 'quantityInput_<%= productid %>', <%= productQuantity %>, '<%= userid %>')">+</button>
             </div>
      
            <form action ="DeleteCartitem" method="post">
            <input type="hidden" name="productid" value="<%=productid%>"/>
            <input type="hidden" name="userid" value="<%=userid%>"/>
            <Button type="submit" style="background-color:gray;" ><b>Delete</b></Button>
            </form>
        </div>
    </div>
    <hr class="product-separator">
    <% 
        }
    } else { 
    %>
    <p>No items in the cart.</p>
    <% } %>
    
   <% if (cartinfos != null && !cartinfos.isEmpty()) { %>
    <form action="Checkout" method="post">
        <% 
        // Add hidden fields for cart information
        for (Map<String, Object> cartinfo : cartinfos) {
            int productid = (int) cartinfo.get("productid");
            int cartPrice = (int) cartinfo.get("cartPrice");
            int cartQuantity = (int) cartinfo.get("cartQuantity");
            int productPrice = (int) cartinfo.get("productPrice");
            int productQuantity = (int) cartinfo.get("productQuantity");
            String color = (String) cartinfo.get("color");
            String size = (String) cartinfo.get("size");
            String image = (String) cartinfo.get("image");
        %>
        <input type="hidden" name="productid_<%= productid %>" value="<%= productid %>"/>
        <input type="hidden" name="cartPrice_<%= productid %>" value="<%= cartPrice %>"/>
        <input type="hidden" name="cartQuantity_<%= productid %>" value="<%= cartQuantity %>"/>
        <input type="hidden" name="productPrice_<%= productid %>" value="<%= productPrice %>"/>
        <input type="hidden" name="productQuantity_<%= productid %>" value="<%= productQuantity %>"/>
        <input type="hidden" name="color_<%= productid %>" value="<%= color %>"/>
        <input type="hidden" name="size_<%= productid %>" value="<%= size %>"/>
         <input type="hidden" name="image_<%= productid %>" value="<%= image %>"/>
        <% 
        } 
        %>
        <input type="hidden" name ="userid" value="<%=userid%>"/>
        <button type="submit" class="buy">BUY</button>
    </form>
    <% } %>
</div>
</body>
</html>
