package uci.zainabk.database;

import java.sql.*;
import java.util.ArrayList;
 
public class FavDatabase {
	

    Connection con; //connection to database    
     
    public FavDatabase(Connection connection){
		con = connection;
    }
    
	public ArrayList<Fav> getFavs(int uid) throws SQLException  {
		ArrayList<Fav> list = new ArrayList<Fav>();
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM faves WHERE user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(uid));
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			list.add(new Fav(Integer.parseInt(result.getString(1)),
					Integer.parseInt(result.getString(2))));
        } return list;
	}
	
	public void addFav(int uid, int mid) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO faves VALUES (?,?)");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(uid));
		pstmt.setString(2,Integer.toString(mid));
		pstmt.executeUpdate();
	}
	
	public void removeFav(int uid, int mid) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("DELETE FROM faves WHERE movie_id=? AND user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(mid));
		pstmt.setString(2,Integer.toString(uid));
		pstmt.execute();
	}

}
