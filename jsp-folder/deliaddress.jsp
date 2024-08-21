<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
    .form-container {
        background-color: white;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        width: 400px;
    }
    .form-container h2 {
         
        text-align: center;
        margin-bottom: 20px;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
       
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }
    .form-group input {
        padding-right: 40px;
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .form-group input:focus {
        border-color: #4CAF50;
    }
    .form-group button {
        width: 100%;
        padding: 10px;
        background-color: #4CAF50;
        border: none;
        border-radius: 4px;
        color: white;
        font-weight: bold;
        cursor: pointer;
    }
    .form-group button:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>


   
   
   <%
    // Retrieve data from session attributes purchase jsp
    String imageUrl = (String) session.getAttribute("imageUrl");
    String price = (String) session.getAttribute("price");
    String size = (String) session.getAttribute("size");
    String color = (String) session.getAttribute("color");
    String quantity = (String) session.getAttribute("quantity");
    //System.out.println(price);
    
%>
 
     <%
    //receiving userid and name froom purchase jsp
    String fname = (String) session.getAttribute("fname");
    String lname = (String) session.getAttribute("lname");
    String postalcode= (String) session.getAttribute("postalcode");
    String state = (String) session.getAttribute("state");
    String city = (String) session.getAttribute("city");
    String streetaddress = (String) session.getAttribute("streetaddress");
    Integer customerid = (Integer) session.getAttribute("customerid");



    %>


    <div class="form-container">
        <h2>Add your delivery address</h2>
        <form action="../deliaddress" method="post">
        
           <input type ="hidden" name = "productImageURL" value=<%= imageUrl %>>
            <input type ="hidden" name = "productPrice" value=<%= price %>>
            <input type ="hidden" name = "size" value=<%= size %>>
            <input type ="hidden" name = "color" value=<%= color %>>
             <input type ="hidden" name = "quantity" value=<%= quantity %>>
             
             <!-- hidden form to pass the customer id to deliaddress servelt -->
              <input type ="hidden" name = "customerid" value=<%= customerid %>>
           
        
            <div class="form-group">
                <label for="name">first name:</label>
                <input type="text" id="fname" name="fname" value='<%= fname %>'required>
            </div>
             <div class="form-group">
                <label for="name">last name:</label>
                <input type="text" id="lname" name="lname" value='<%= lname %>' required>
            </div>
            <div class="form-group">
                <label for="address">postal code:</label>
                <input type="text" id="postal" name="postalcode" value='<%= postalcode %>'required>
            </div>
            <div class="form-group">
                <label for="address">state:</label>
                <input type="text" id="state" name="state"value='<%= state%>' required>
            </div>
            <div class="form-group">
                <label for="address">city:</label>
                <input type="text" id="city" name="city" value='<%= city %>'required>
            </div>
            <div class="form-group">
                <label for="apartment">street address:</label>
                <input type="text" id="streetaddress" name="streetaddress" value='<%= streetaddress %>'required>
            </div>
            <div class="form-group">
                <button type="submit">Register</button>
            </div>
        </form>
    </div>
</body>
</html>