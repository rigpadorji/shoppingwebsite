<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name ="viewport" content ="width= device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="./css-folder/style.css" >
<style>
    .cart-icon {
        position: relative;
        display: inline-block;
        margin-right: 10px; /* Adjust margin as needed */
    }
    .cart-count {
        position: absolute;
        top: -5px; /* Adjust top position as needed */
        right: -5px; /* Adjust right position as needed */
        background-color: red;
        color: white;
        border-radius: 50%;
        padding: 2px 5px;
        font-size: 12px;
       display: <%= request.getAttribute("totalQuantity") != null && (int) request.getAttribute("totalQuantity") > 0 ? "inline-block" : "none" %>;
       }
</style>
<title>KzangRigpa Shopping Website</title>
</head>
<body>  

<%
//retreving session of user if from displayingproduct servlet
Integer userid1 = (Integer) session.getAttribute("userid");

System.out.println("the userid1 in index.jsp"+userid1);

//again creating session to pass it value to productdetail servlet
session.setAttribute("userid",userid1);

%>


<% 
//receiving session attribute from add to cart servlet
// Retrieve cart quantity from session
int cartQty = (request.getAttribute("totalQuantity") != null) ? (int) request.getAttribute("totalQuantity") : 0;
System.out.println("total quantity int index jsp :"+cartQty);
%>
<div class= "container">

    <div class ="bar-container">
        <button class="btn"><i class="fa fa-home"></i>menu</button>
    </div>
    
    
    <div class="web-name">
    <b>KzangRigpa</b>
    </div>
    <div class="head-icon">
     <button class="btn"><i class="fa fa-user"></i></button>
    </div>
    <form action="displayCartinfo" method="post">
    <input type="hidden" name="userid" value="<%= userid1%>">
     <div class="cart-icon">
        <button type="submit" class="btn">
            <i class="fa fa-shopping-cart"></i>
            <span id="cart-count" class="cart-count"><%= cartQty %></span>
        </button>
    </div>
    </form>
    
</div> 

<%
//retreving session of user if from displayingproduct servlet
Integer userid = (Integer) session.getAttribute("userid");

System.out.println("the userid reached at index.jsp"+userid);

//again creating session to pass it value to productdetail servlet
session.setAttribute("userid",userid);

%>
   

<form  action ="./Productsearch" method = "post">
<input type="hidden" name="userid" value="<%= userid %>">
<div class="search-div">
<input class="search-text" type ="text" name ="searchid" placeholder ="Search kelzangRigpa"/>
<button class="btn"><i class="fa fa-search"></i>Search</button>
</div>
</form>


 <%
        List<Map<String, Object>> products = (List<Map<String, Object>>) request.getAttribute("products");
        //System.out.println("Products received in JSP: " + products);

        if (products != null && !products.isEmpty()) {
            for (Map<String, Object> product : products) {
            	int krparentid = (int) product.get("krparentid");
                String title = (String) product.get("title");
                String description = (String) product.get("description");
                String category = (String) product.get("category");
                int price = (int) product.get("price");
                String mainimage = (String) product.get("mainimage");
                //int krchildid = (int) product.get("krchildid");
                
                System.out.println("parentid "+krparentid);
                //System.out.println("krchildid "+krchildid);
    %>
                <div  style="display: inline-block; margin-right: 30px;">
                <table style="width:250px; height:300px; background-color:white;">
                 <tr>
                   <td>
                   <a href="producDetail?krparentid=<%= krparentid %>">
                   <img src="http://localhost:8080/kzangRigpaSeller/image/<%= mainimage %>" width="200" height="200" style="display: block; margin: 0 auto;">
                   </a>
                   </td>
                 </tr>
                   <tr>
                   <td style ="text-align:center;">
                   <%= title%>
                   </td>
                   </tr>
                   <tr>
                   <td style ="text-align:center;">
                   <%= description %>
                   </td>
                   </tr>
                   
                  <tr>
                   <td style ="text-align:center;">
                   <b>PRICE: $<%= price %></b>
                   </td>
                   </tr>
                 
                </table>
                </div>
                
    <%
            }
        } else {
            out.println("No products found.");
        }
    %>

</body>
</html>