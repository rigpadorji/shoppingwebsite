package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import bean.registerbean;

public class registerdao {
	String dbDriver = "com.mysql.cj.jdbc.Driver";
	String url="jdbc:mysql://localhost:3306/kzangrigpa";
	String root ="root";
	String pass ="yeshi12345";
	
	
	public Connection getConnection(){
		      Connection con = null;
		     try {
			      con =DriverManager.getConnection(url,root,pass);
		            } catch (SQLException e) {
			         // TODO Auto-generated catch block
		        	e.printStackTrace();
		          }
		        return con;
	        }
	
	public void loadriver(String dbdriver) throws ClassNotFoundException {
		
		Class.forName(dbdriver);
	}
	 
		public void insertuserinfor(registerbean registerbean) throws ClassNotFoundException {
			loadriver(dbDriver);
	        try {
	             
	           Connection con = getConnection();
	           String autosql = "ALTER TABLE Login MODIFY COLUMN id INT AUTO_INCREMENT";
	           Statement statement = con.createStatement();
	           statement.execute(autosql);
	        	
	         String sql = "INSERT INTO account_registration(first_name,last_name,email_phone,password) values(?,?,?,?)";
	            
	         PreparedStatement pst = con.prepareStatement(sql);
	         pst.setString(1,registerbean.getFirstname() );
	         pst.setString(2, registerbean.getLastname());
	         pst.setString(3, registerbean.getEmail());
	         pst.setString(4, registerbean.getPassword());
	         int rowaffected =pst.executeUpdate();
	         if(rowaffected>0) {
	        	 System.out.println("register successfully"); 
	        	 
	         }else {
	        	 System.out.println(con);  
	         }
	         
	        
	
	         }catch(SQLException e) {
		     e.printStackTrace();
		
	         }
		}

}
