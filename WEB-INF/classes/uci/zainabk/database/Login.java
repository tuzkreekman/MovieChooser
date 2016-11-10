package uci.zainabk.database;

import java.util.*;
import java.io.*;
//import java.time.format.*;
import java.text.*;

public class Login {
	
	public static boolean login(Database db, String username,String password) {
		try {
			UserDatabase udb = new UserDatabase(db.getConnection());
			User u = udb.getUser(username,false);
			if (u.isPassword(password)) {
				saveLogin(u.id);
				return true;
			}
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			return false; 
		} finally {
		;
		} return false;
	}
	
	private static void saveLogin(int id) throws LoginException{
		try{
			PrintWriter writer = new PrintWriter("WEB-INF/login.log", "UTF-8");
			writer.printf("%d\t\t");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			writer.println(dateFormat.format(date));
			writer.close();
		} catch(Exception e) { throw new LoginException(e.getClass().toString()+e.getMessage()); } 
		finally {;}
	}
	
	public static void logout() {
		try{ 
			PrintWriter writer = new PrintWriter("WEB-INF/login.log", "UTF-8");
			writer.printf("-1\t\t");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			writer.println(dateFormat.format(date));
			writer.close();
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		} finally {;}
	}
	
	public static int getUserID() {
		try{ 
			FileReader fileReader = new FileReader("WEB-INF/login.log");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringTokenizer st = new StringTokenizer(bufferedReader.readLine(),"\t");
			return Integer.parseInt(st.nextToken());
		} catch (Exception e) {
			return -1;
		} finally {
			;
		} 
	}
	
	/*public User getUser(Database db) {
		try {
			UserDatabase udb = new UserDatabase(db);
			return udb.getUser(id);
		} finally {
			;
		} return null;
	}*/
}