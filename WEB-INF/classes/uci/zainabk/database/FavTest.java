package uci.zainabk.database;

import java.io.*;
import uci.zainabk.imdb.*;

public class FavTest{
	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("Requires 1 argument");
			return;
		}
		try {
			int id = Login.getUserID();
			if (id<0) {
				System.out.println("Not logged in");
				return;
			}
			String imdb = IMDB.getID(args[0]);
			System.out.println(imdb);
			Database db = new Database();
			MovieDatabase mdb = new MovieDatabase(db.getConnection());
			Movie m = mdb.getMovie(-1,imdb);
			if (m==null) mdb.addMovie(imdb);
			m = mdb.getMovie(-1,imdb);
			System.out.println(m);
			Fav myFav = new Fav(id,m.id);
			FavDatabase fdb = new FavDatabase(db.getConnection());
			boolean found = false;
			for (Fav f : fdb.getFavs(id)) {
				if (f.equals(myFav)) found = true;
			} if (!found) {
				fdb.addFav(id,m.id);
			}
			fdb = new FavDatabase(db.getConnection());
			for (Fav f : fdb.getFavs(id)) {
				System.out.println(f);
				myFav = f;
			}
			fdb.removeFav(myFav.user_id,myFav.movie_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}