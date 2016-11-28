package uci.zainabk.database;

import java.io.*;
import java.util.Arrays;

public class LoginTest{
	public static void main(String[] args) {
		System.out.println("Output "+((unitTest())?"":" not ")+"as Expected");
	}
	
	private static boolean unitTest() {
		String[] login = {"Ginevra","Weasley"};
		if (loginTest(login)!=0) return false;
		if (loadUserTest()!=1) return false;
		if (logoutTest()!=-1) return false;
		if (loginTest(login)!=0) return false;
		return true;
	}
	
	public static int loginTest(String[] args) {
		if (args.length!=2) {
			System.out.println("Must provide username and password (for user database)");
			return -1;
		}
		int id = -1;
		try{
			Database db = new Database();
			Login.login(db,args[0],args[1]);
			db.close();
			id = 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally{
			return id;
		}
	}
	
	public static int loadUserTest() {
		int id = -1;
		try{
			Database db = new Database();
			id = (Login.getUserID());
			db.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally{
			return id;
		}
	}
	
	public static int logoutTest() {
		int id = 0;
		try{
			Login.logout();
			Database db = new Database();
			id = (Login.getUserID());
			db.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally{
			return id;
		}
	}
	
}