package uci.zainabk.database;

import java.sql.*;
 
public class MovieDatabase {
	

    Connection conn; //connection to database    
     
    public MovieDatabase(){
    // build a default connection to database
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/movie_chooser";
        String user = "movie";
        String password = "chooser"; 
        
        try {
            Class.forName(driver);
            //connect to database
            conn = DriverManager.getConnection(url, user, password); 
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!"); 
        } catch (SQLException e) {
            System.out.println("MySQL Error");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
     
    public void Connect(String url, String user, String password){
    //build a connection to database according to parameters
        String driver = "com.mysql.jdbc.Driver";       
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password); 
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!"); 
        } catch (SQLException e) {
            System.out.println("MySQL Error");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }   
     
    public void close() throws SQLException{
        //Close the connection to database
        conn.close();
    }
    
	public Movie getMovie(int id) {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("SELECT * FROM movie WHERE movie_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,id);
		ResultSet result = pstmt.executeQuery();
	    while (result.next()) {
			return new Movie(result.getString(1),result.getString(2));
        } return null;
	}
	
	public void editMovie(int id,String name) {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement("UPDATE movie SET name=? WHERE movie_id=?");
		pstmt.clearParameters();
		pstmt.setString(1,name)
		pstmt.setString(2,id);
		pstmt.executeQuery();
	}

}
