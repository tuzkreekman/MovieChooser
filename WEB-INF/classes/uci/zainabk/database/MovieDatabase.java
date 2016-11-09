package uci.zainabk.database;

import java.sql.*;
 
public class MovieDatabase {
	

    Connection con; //connection to database    
     
    public MovieDatabase(Connection connection){
		con = connection;
    }
    
	public Movie getMovie(int id) throws SQLException  {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM movie WHERE movie_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,Integer.toString(id));
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new Movie(Integer.parseInt(result.getString(1)),
					result.getString(2),
					result.getString(3));
        } return null;
	}
	
	public void editMovie(int id,String name) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("UPDATE movie SET name=? WHERE movie_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		pstmt.setString(2,Integer.toString(id));
		pstmt.executeQuery();
	}
	
	public void addMovie(String name,String imdb_id) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO movie VALUES (default,?,?)");
		pstmt.clearParameters();
		pstmt.setString(1,name);
		pstmt.setString(2,imdb_id);
		pstmt.executeQuery();
	}

}
