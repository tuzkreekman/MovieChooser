package uci.zainabk.database;

import java.sql.*;
 
public class UserDatabase {
	

    Connection con; //connection to database    
     
    public UserDatabase(Connection connection){
		con = connection;
	}
         
	/*public User getUserFromName(String name) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM user WHERE username=?");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new User(Integer.parseInt(result.getString(2)),
					result.getString(1),
					result.getString(3));
        } return null;
	}
	
	public User getUserFromID(String id) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM user WHERE id=?");
		pstmt.clearParameters();
		pstmt.setString(1,id);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new User(Integer.parseInt(result.getString(2)),
					result.getString(1),
					result.getString(3));
        } return null;
	}*/
	
	public User getUser(String identifier, boolean isID) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM user WHERE ?=?");
		pstmt.clearParameters();
		pstmt.setString(1,(isID)?"user_id":"username");
		pstmt.setString(2,identifier);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new User(Integer.parseInt(result.getString(2)),
					result.getString(1),
					result.getString(3));
        } return null;
	}
	
	public void editUsername(int id,String name) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("UPDATE user SET username=? WHERE user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		pstmt.setString(2,Integer.toString(id));
		pstmt.executeQuery();
	}
	
	public void editPassword(int id,String pwd) throws SQLException  {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("UPDATE user SET password=? WHERE user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,pwd);
		pstmt.setString(2,Integer.toString(id));
		pstmt.executeQuery();
	}
	
	public void addUser(String name,String pwd) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO user VALUES (?,default,?)");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		pstmt.setString(2,pwd);
		pstmt.executeQuery();
	}

}
