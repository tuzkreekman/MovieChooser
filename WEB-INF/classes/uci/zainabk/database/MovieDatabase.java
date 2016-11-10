package uci.zainabk.database;

import java.sql.*;
 
public class MovieDatabase {
	

    Connection con; //connection to database    
     
    public MovieDatabase(Connection connection){
		con = connection;
    }
    
	public Movie getMovie(int id,String imdbid) throws SQLException  {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM movie WHERE "+((id>=0)?"movie_id":"imdb_id")+"=?");
		pstmt.clearParameters();
		pstmt.setString(1,(id>=0)?Integer.toString(id):imdbid);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new Movie(Integer.parseInt(result.getString(1)),
					result.getString(2));
        } return null;
	}
	
	public void addMovie(String imdb_id) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("INSERT INTO movie VALUES (default,?)");
		pstmt.clearParameters();
		pstmt.setString(1,imdb_id);
		pstmt.executeUpdate();
	}

}
