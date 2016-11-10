package uci.zainabk.database;

import java.sql.*;
 
public class UserDatabase {
	

    Connection con; //connection to database    
     
    public UserDatabase(Connection connection){
		con = connection;
	}
	
	public User getUser(String identifier, boolean isID) throws SQLException {
		PreparedStatement pstmt;
		//pstmt = con.prepareStatement("SELECT * FROM user");
		pstmt = con.prepareStatement("SELECT * FROM user WHERE "+((isID)?"user_id":"username")+"=?");
		pstmt.clearParameters();
		//pstmt.setString(1,(isID)?"user_id":"username");
		pstmt.setString(1,identifier);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new User(Integer.parseInt(result.getString(2)),
					result.getString(1),
					result.getString(3));
        } return null;
	}
	
	public int editUsername(int id,String name) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM user WHERE username=?");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			if (Integer.parseInt(result.getString(2))!=id) return -2; //username taken
        } 
		pstmt = con.prepareStatement("UPDATE user SET username=? WHERE user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		pstmt.setString(2,Integer.toString(id));
		return pstmt.executeUpdate(); //sql error
	}
	
	public int editPassword(int id,String pwd) throws SQLException  {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("UPDATE user SET password=? WHERE user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,pwd);
		pstmt.setString(2,Integer.toString(id));
		return pstmt.executeUpdate(); //sql error
	}
	
	public int addUser(String name,String pwd) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM user WHERE username=?");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return -2; //username taken
        } 
		pstmt = con.prepareStatement("INSERT INTO user VALUES (?,default,?)");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		pstmt.setString(2,pwd);
		return pstmt.executeUpdate(); //sql error
	}

}
