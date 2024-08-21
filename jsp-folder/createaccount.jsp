<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type ="text/css" href="./css-folder/createaccount.css">
<title>Welcome to KzangRigpa</title>
</head>
<body>
<h1>WELCOME to KzangRIgpa Shopping</h1>

<div class="div-head">
      <div class= "create-account">
      <input type="radio" name="checkaccount" onclick ="toggleForm()"/>&nbsp &nbsp &nbsp &nbsp CREATE ACCOUNT<br> &nbsp  &nbsp &nbsp &nbsp &nbsp  &nbsp new to KzangRigpa?
      </div>
     
            <div  id="option1" class="hidden">
               <form class="form1"  action ="create_acc_servlet" method="post">
                  <label>First Name <br>
                      <input class ="input1" type ="text" placeholder="Enter yor first name" name ="firstname" required>
        
                  </label><br>
                  
                   <label>Last Name <br>
                      <input class ="input1" type ="text" placeholder="Enter your last name" name ="lastname" required>
        
                  </label><br>
                  <label>Email or Phone Number <br>
                       <input class ="input1"type ="text" placeholder=" Enter email or phone number" name="email" required >
        
                  </label><br>
                  <label>Create password<br>
                        <input class ="input1" type ="text" placeholder="create password" name="password" required >
        
                  </label><br>
                         <input type ="checkbox" />Show password<br><br>
                         
                         <button style= "width:30%; height:30px; border:1px solid brown;background-color:yellow;font-size:20px;" type="submit"> <b>continue</b></button>
               </form>
               <p style="padding-left:36%">By creating an account, you agree to KzangRigpa<br> <a style="color:blue;" href="">condition of use </a> and  <a style="color:blue;" href="">privacy</a></p>
                         <div class ="business-account">
                         <p>Do you want to sale your goods at KzangRigpa<br>&nbsp &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="">Create Business ccount</a></p>
                    
                         </div>
            </div>
            
      <div class="login-account">
     <input type="radio"name="checkaccount" onclick="showForm()"/>&nbsp &nbsp &nbsp &nbsp LOGIN TO ACOOUNT<br>&nbsp  &nbsp &nbsp &nbsp &nbsp  &nbsp already have account?
     </div>
     
            <div  id="option2" class="hidden">
               <form  action="login" method= "post" class="form2">
      
                    <label style ="font-size: 30px; color:blue;padding-left:20px;">Email or Phone number<br>
                         <input  name ="username" style="width:50%;height:30px;border:1px solid black;padding-left:10px;" type="text" placeholder="Enter your email or phone number" required/>
                    </label><br>
                    <label style ="font-size: 30px; color:blue;padding-left:20px;">password<br>
                         <input  name ="password" style="width:50%;height:30px;border:1px solid black;padding-left:10px;" type="text" placeholder="Enter your password" required/>
                    </label><br>
                         <div class="btn11-div">
                         <button style="background-color:yellow;width:40%; height:30px;"type="submit">continue</button>
                         </div>
               </form>
                    
                    <p style="padding-left:40%">By creating an account, you agree to KzangRigpa<br> <a style="color:blue;" href="">condition of use </a> and  <a style="color:blue;" href="">privacy</a></p>
                         <div style="padding-left:40%">
                         <p>Do you want to sale your goods at KzangRigpa<br>&nbsp &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="">Create Business ccount</a></p>
                     
                         </div>
     
           </div>

</div>

<script type="text/javascript">
 
 function toggleForm(){
	        	var form = document.getElementById("option1");
	        	form.style.display =(form.style.display === 'none' || form.style.display === '')? 'block':'none';      	
	 }
 function showForm(){
 	var form = document.getElementById("option2");
 	form.style.display =(form.style.display === 'none' || form.style.display === '')? 'block':'none';      	
}
</script>

</body>
</html>