package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.http.HttpSession;

public class logindao {
	public static Integer check(String username, String password) throws IOException,SQLException{
	Integer userId = null;	
	//Boolean st = false;
	String dbdriver ="com.mysql.cj.jdbc.Driver";
	String url ="jdbc:mysql://localhost:3306/kzangrigpa";
	String uname = "root";
	String pwd = "yeshi12345";
	
	String sql = "select * from account_registration where email_phone=? and password=?";
	
	 try {
		Class.forName(dbdriver);
		Connection con = DriverManager.getConnection(url,uname,pwd);
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1,username);
		pst.setString(2, password);
		ResultSet rs = pst.executeQuery();
		//st= rs.next();
		if (rs.next()) {
            userId = rs.getInt("registration_id");
        }
		
		
		
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	 //return st;
	 return userId;
	}

}
