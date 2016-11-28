package uci.zainabk.database;

import java.sql.*;
 
public class Database {
	

    private final Connection conn; //connection to database    
     
    public Database(){
    // build a default connection to database
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/movie_chooser";
        String user = "termproject";
        String password = "eecs118tp"; 
		Connection con = null;
        
        try {
            Class.forName(driver);
            //connect to database
            con = DriverManager.getConnection(url, user, password); 
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!"); 
        } catch (SQLException e) {
            System.out.println("MySQL Error");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			conn = con;
		}
    }
	
	public void close() throws SQLException{
        //Close the connection to database
        conn.close();
    }

     
	public Connection getConnection() { return conn; }
}
