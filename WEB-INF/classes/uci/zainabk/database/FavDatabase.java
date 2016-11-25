package uci.zainabk.database;

import java.sql.*;
import java.util.ArrayList;
 
public class FavDatabase {
	public static int HATE = 0, WATCHED = 1, LOVE = 2;
	

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
					Integer.parseInt(result.getString(2)),
					Integer.parseInt(result.getString(3))));
        } return list;
	}
	
	public void addFav(int uid, int mid) throws SQLException {
		if (this.hasFav(uid,mid)) {
			this.editOpinion(uid,mid,2);
			return;
		}
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO faves VALUES (?,?,2)");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(uid));
		pstmt.setString(2,Integer.toString(mid));
		pstmt.executeUpdate();
	}
	
	public void addWatch(int uid, int mid) throws SQLException {
		if (this.hasFav(uid,mid)) {
			this.editOpinion(uid,mid,1);
			return;
		}
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO faves VALUES (?,?,1)");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(uid));
		pstmt.setString(2,Integer.toString(mid));
		pstmt.executeUpdate();
	}
	
	public void addHate(int uid, int mid) throws SQLException {
		if (this.hasFav(uid,mid)) {
			this.editOpinion(uid,mid,0);
			return;
		}
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO faves VALUES (?,?,0)");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(uid));
		pstmt.setString(2,Integer.toString(mid));
		pstmt.executeUpdate();
	}
	
	public boolean hasFav(int uid, int mid) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM faves WHERE movie_id=? AND user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(mid));
		pstmt.setString(2,Integer.toString(uid));
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	public void editOpinion(int uid, int mid, int opine) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("UPDATE faves SET opinion=? WHERE movie_id=? AND user_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(opine));
		pstmt.setString(2,Integer.toString(mid));
		pstmt.setString(3,Integer.toString(uid));
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
