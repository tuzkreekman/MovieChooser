package uci.zainabk.database;

import java.io.*;

public class Test{
	public static void main(String[] args) {
		try{
			Database db = new Database();
			System.out.println(Login.login(db,"ginny","guineapig"));
			db.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		} finally{
			;
		}
	}
}