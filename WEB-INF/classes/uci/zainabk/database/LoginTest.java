package uci.zainabk.database;

import java.io.*;
import java.util.Arrays;

public class LoginTest{
	public static void main(String[] args) {
		if (args.length==0) {
			System.out.println("Missing inputs");
			return;
		} else if (args[0].equals("1")) loginTest(Arrays.copyOfRange(args,1,3));
		else if (args[0].equals("2")) loadUserTest();
		else if (args[0].equals("3")) logoutTest();
		else {
			System.out.println("Unexpected inputs");
			return;
		}
		
	}
	
	public static void loginTest(String[] args) {
		if (args.length!=2) {
			System.out.println("Must provide username and password (for user database)");
			return;
		}
		try{
			Database db = new Database();
			System.out.println(Login.login(db,args[0],args[1]));
			db.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		} finally{
			;
		}
	}
	
	public static void loadUserTest() {
		try{
			Database db = new Database();
			System.out.println(Login.getUserID());
			db.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		} finally{
			;
		}
	}
	
	public static void logoutTest() {
		try{
			Login.logout();
			Database db = new Database();
			System.out.println(Login.getUserID());
			db.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		} finally{
			;
		}
	}
	
}