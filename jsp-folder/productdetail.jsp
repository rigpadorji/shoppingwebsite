<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Page</title>
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
    .product-container {
         background-color: white;
        width: 500px;
        margin: 0 auto;
        text-align: center;
    }
    .product-image img {
        padding-top:20px;
        width: 200px;
        height: 200px;
        display: block;
        margin: 0 auto;
    }
    .product-details {
        margin-top: 10px;
    }
    .product-details label {
        display: block;
        margin: 10px 0 5px;
    }
    .product-details select, .product-details input {
        width: 50%;
        padding: 8px;
        margin-bottom: 10px;
    }
    #add-to-cart-btn {
    display: inline-block;
    background-color: #28a745;
    color: white;
    border: none;
    text-align: center;
    padding: 10px;
    cursor: pointer;
    width: 200px;
    text-decoration: none; /* Remove underline from link */
    font-size: 16px; /* Adjust font size if needed */
    border-radius: 5px;
    }
    .buy {
        display: inline-block;
        background-color: blue;
        margin-top: 20px;
        color: white;
        border: none;
        padding: 10px;
        font-size: 16px;
        cursor: pointer;
        width: 220px;
        border-radius: 5px;
        margin-bottom:20px;
    }
    .add-to-cart-btn:hover {
        background-color: #218838;
    }
    .error-popup {
        display: none;
        background-color: red;
        color: white;
        padding: 15px;
        position: fixed;
        top:380px;
        left: 50%;
        transform: translateX(-50%);
        border: 2px solid darkred;
        border-radius: 5px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- change the image as soon as user change the color and size -->
<script>
$(document).ready(function() {
    $('#size, #color').change(function() {
        var size = $('#size').val();
        var color = $('#color').val();
        var parentid = <%= (int)request.getAttribute("parentid") %>;

        $.ajax({
            url: 'pdisonchangesize',
            type: 'GET',
            data: {
                size: size,
                color: color,
                parentid: parentid
            },
            success: function(response) {
                var product = typeof response === "string" ? JSON.parse(response) : response;

                if (product && typeof product === "object") {
                    if (product.error) {
                        showErrorPopup(product.error);
                    } else {
                    	//with reffrence of id, new attribute can be display according the previous oage. value are changing when color and size are chage 
                    	$('#productid').text('Product Number:' + product.krchildid); 
                        $('#productImage').attr('src', 'http://localhost:8080/kzangRigpaSeller/image/' + product.imageurl);
                        $('#productPrice').text('Price: $' + product.price);                  
                        $('#quantity').attr('max', product.quantity);
                        
                        console.log("the krchild when change the color at pdisonchange servlet is :"+product.krchildid);
                        
                        
                        // Updating hidden fields with the latest data
                         $('#productid').val(product.krchildid);
                        $('#productImageURL').val(product.imageurl);
                        $('#productPriceValue').val(product.price);
                        $('#productQuantityValue').val(product.quantity);
                        
                        //passing the value to addToCatt function
                         $('#add-to-cart-btn').attr('onclick', `addToCart('<%= (Integer) session.getAttribute("userid")%>', '${product.krchildid}', '${product.imageurl}', '${product.price}', '${product.quantity}', '${size}', '${color}'); return false;`);
                        
                         console.log('Product ID:', product.krchildid);//till krchild is coming perfectly
                         console.log('Image URL:', product.imageurl);
                         console.log('Price:', product.price);
                         console.log('Quantity:', product.quantity);
                         console.log('Size:', size);
                         console.log('Color:', color);

                          }
                } else {
                    showErrorPopup('Unexpected response format.');
                }
            },
            error: function() {
                showErrorPopup('An error occurred while fetching the product details.');
            }
        });
    });

    function showErrorPopup(message) {
        var popup = $('<div class="error-popup"></div>').text(message);
        $('body').append(popup);
        popup.fadeIn().delay(3000).fadeOut(function() {
            $(this).remove();
        });
    }
});


</script>



<!-- onclick function to pass value to addto servlet -->

<script>
function addToCart(userid, krchildid, imageurl, price, quantity, size, color) {
	//var krchild = document.getElementById("krchild").value;
	//var price = document.getElementById("price").value;
	var quantity = document.getElementById("quantity").value;
	var size = document.getElementById("size").value;
	var color = document.getElementById("color").value;
    var imageurl = document.getElementById("productImageURL").Value;
	
	
	var xhr = new XMLHttpRequest();
    var url = "addToCart";
    var params = "userid=" + encodeURIComponent(userid) +
                 "&krchildid=" + encodeURIComponent(krchildid) +
                 "&imageurl=" + encodeURIComponent(imageurl) +
                 "&price=" + encodeURIComponent(price) +
                 "&quantity=" + encodeURIComponent(quantity) +
                 "&size=" + encodeURIComponent(size) +
                 "&color=" + encodeURIComponent(color);
    
                 console.log("userid:", userid);
                 console.log("krchildid at param of addtocart function :", krchildid)
                 console.log("krchildid:", krchildid);
                 console.log("imageurl:", imageurl);
                 console.log("price:", price);
                 console.log("quantity:", quantity);
                 console.log("size:", size);
                 console.log("color:", color);
    
    xhr.open("POST", url, true);

    // Set the content type to handle form data properly
    //xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");


    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Handle successful response
                var response = JSON.parse(xhr.responseText);
                if (response.status === "success") {
                    alert(response.message);
                } else {
                    alert(response.message);
                }
            } else {
                // Handle error response
                console.error("Error adding item to cart.");
                alert("Failed to add item to cart.");
            }
        }
    };

    xhr.send(params);
}

</script>



</head>
<body>

    <%
      //retreving session of user if from productdetail  servlet
      Integer userid = (Integer) session.getAttribute("userid");

      System.out.println("the userid reached at productdetail.jsp"+userid);

      //again creating session to pass it value to purchase jsp
    session.setAttribute("userid",userid);

    %>
    



    <% 
        
        int krchildid = (int)request.getAttribute("krchildid");
        int parentid = (int)request.getAttribute("parentid");
        String imageurl = (String)request.getAttribute("imageurl");
        int price = (int)request.getAttribute("price");
        int quantity = (int)request.getAttribute("quantity");
        String size = (String)request.getAttribute("size");
        String color = (String)request.getAttribute("color");
        Date date = (Date)request.getAttribute("date");
        System.out.println("the parent and child id are:");
        System.out.println("the parent id is "+ parentid);
        System.out.println("the child id is "+ krchildid);
        
        
    %>
    
    
    <div class="product-container">
        <div class="product-image">
            <img id="productImage" src="http://localhost:8080/kzangRigpaSeller/image/<%= imageurl %>" alt="Product Image">
        </div>
        <div class="product-details">
            <p id="productPrice">Price: $<%= price %></p>
            <p id="productid">Product Number: <%= krchildid %></p>
            
            <form action="Purchase" method ="post">
            
            
               <!-- hidden form to pass the  value to purchase servlet -->
                <input type="hidden" id="productid" name="productid" value="<%= krchildid %>">
                <input type="hidden" id="productImageURL" name="productImageURL" value="<%= imageurl %>">
                <input type="hidden" id="productPriceValue" name="productPrice" value="<%= price %>">
                <input type="hidden" id="productQuantityValue" name="productQuantity" value="1">
                
                
                
                <label for="size">Size:</label>
                <select id="size" name="size">
                    <option value="S" <%= "S".equals(size) ? "selected" : "" %>>Small</option>
                    <option value="M" <%= "M".equals(size) ? "selected" : "" %>>Medium</option>
                    <option value="L" <%= "L".equals(size) ? "selected" : "" %>>Large</option>
                    <option value="XL" <%= "XL".equals(size) ? "selected" : "" %>>Extra Large</option>
                </select>
                <label for="color">Color:</label>
                <select id="color" name="color">
                    <option value="Red" <%= "Red".equalsIgnoreCase(color) ? "selected" : "" %>>Red</option>
                    <option value="Blue" <%= "Blue".equalsIgnoreCase(color) ? "selected" : "" %>>Blue</option>
                    <option value="Green" <%= "Green".equalsIgnoreCase(color) ? "selected" : "" %>>Green</option>
                    <option value="Black" <%= "Black".equalsIgnoreCase(color) ? "selected" : "" %>>Black</option>
                </select>
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" min="1" max="<%= quantity %>" value="1"><br><br>
                <a href="#" id="add-to-cart-btn"
                onclick="addToCart('<%= userid %>', '<%= krchildid %>', '<%= imageurl %>', '<%= price %>', '<%= quantity %>', '<%= size %>', '<%= color %>'); return false;">
                Add to Cart
                 </a>
                <br><br>
                <button type="submit" class="buy">BUY</button>
            </form>
            
        </div>
    </div>
</body>
</html>
