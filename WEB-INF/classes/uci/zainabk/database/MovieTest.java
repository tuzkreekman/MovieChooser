package uci.zainabk.database;

import java.io.*;
import uci.zainabk.imdb.*;

public class MovieTest{
	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("Requires 1 argument");
			return;
		}
		try {
			String imdb = IMDB.getID(args[0]);
			System.out.println(imdb);
			Database db = new Database();
			MovieDatabase mdb = new MovieDatabase(db.getConnection());
			Movie m = mdb.getMovie(-1,imdb);
			if (m==null) mdb.addMovie(imdb);
			m = mdb.getMovie(-1,imdb);
			System.out.println(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}