package uci.zainabk.movies;

import java.net.*;
import java.io.*;
import com.google.gson.stream.*;
import uci.zainabk.database.*;
import java.util.*;


public class MovieRecommender {
	public static final int RECOMMENDATION_SIZE = 15;
	
	public static void main(String[] args) {
		recommend(args);
	}
	
	public static MovieSuggestion recommend(String[] params) {
		Database db = new Database();
		int id = Login.getUserID();
		ArrayList<MovieSuggestion> opts = getChoices(id,params,db);
		ArrayList<String> ids = getLoved(id,db);
		HashSet<String> tropes = getTropes(ids);
		//for (String trope : getTropes(ids)) System.out.println(trope);
		ArrayList<Integer> score = new ArrayList<Integer>();
		for (MovieSuggestion m : opts) {
			MovieInfo mi = new MovieInfo(m.getTitle(),db);
			mi.getInfoFromIMDB();
			mi.loadTropes();
			ArrayList<String> nT = mi.getTags();
			nT.retainAll(tropes);
			score.add(nT.size());
			System.out.println(m.getTitle()+": "+nT.size());
		}
		int max = 0;
		int index = 0;
		for (int i = 0; i< opts.size(); i++) {
			if (score.get(i)>max) {
				max = score.get(i);
				index = i;
			}
		}
		
		System.out.println(opts.get(index).getTitle()+": "+score.get(index));
		return opts.get(index);
	}
	
	private static boolean foundTrope(String trope, ArrayList<String> tropes) {
		for (String t: tropes)
			if (trope.equals(t)) return true;
		return false;
	}
	
	private static HashSet<String> getTropes(ArrayList<String> imdbIDs) {
		HashSet<String> tropes = new HashSet<String>();
		for (String id : imdbIDs) {
			MovieInfo mi = new MovieInfo(id);
			mi.getInfoFromIMDB();
			mi.loadTropes();
			tropes.addAll(mi.getTags());
		}
		return tropes;
	}
	
	private static ArrayList<String> getLoved(int id, Database db) {
		MovieDatabase mdb = new MovieDatabase(db.getConnection());
		ArrayList<String> imdbs = new ArrayList<String>();
		ArrayList<Fav> favs = getWatched(id,db);
		for (Fav f : favs) {
			if (f.getOpinion()==FavDatabase.LOVE) {
				try{
					imdbs.add(mdb.getMovie(f.movie_id,null).imdb);
				} catch (Exception e) {
					System.out.println("Error getting movie from database");
				}
				
			}
		}
		return imdbs;
	}
	
	private static ArrayList<Fav> getWatched(int id, Database db) {
		FavDatabase fdb = new FavDatabase(db.getConnection());
		try {
			return fdb.getFavs(id);
		} catch (Exception e) {
			System.out.println("Error getting favs from database");
			return null;
		}
	}
	
	private static ArrayList<MovieSuggestion> getChoices(int id, String[] params, Database db) {
		try{
		
		ArrayList<MovieSuggestion> movies = MovieList.getMovies(params);
		for (int i = 0; i <movies.size(); i++) {
			MovieSuggestion current = movies.get(i);
			MovieInfo mi = new MovieInfo(current.getTitle(),db);
			if (mi.getIMDBID()==null) {
				movies.remove(i);
				continue;
			}
			Movie m = mi.getMovie();
			FavDatabase fdb = new FavDatabase(db.getConnection());
			if (fdb.hasFav(id,m.id)) {
				movies.remove(i);
			} else if (i > RECOMMENDATION_SIZE) movies.remove(i);
		}
		int page = 1;
		String[] nParams = new String[params.length+2];
		int j;
		for (j = 0 ; j < params.length; j++) {
			nParams[j] = params[j];
		}
		nParams[j] = "page";
		j++;
		nParams[j] = Integer.toString(page);
		int size = movies.size();
		while ((size=movies.size())<RECOMMENDATION_SIZE) {
			page++;
			nParams[j] = Integer.toString(page);
			movies.addAll(MovieList.getMovies(nParams));
			for (int i = size; i <movies.size(); i++) {
				MovieSuggestion current = movies.get(i);
				MovieInfo mi = new MovieInfo(current.getTitle(),db);
				Movie m = mi.getMovie();
				FavDatabase fdb = new FavDatabase(db.getConnection());
				if (fdb.hasFav(id,m.id)) {
					movies.remove(i);
				} else if (i > RECOMMENDATION_SIZE) movies.remove(i);
			}
		}
		
		return movies;
		
		} catch (Exception e) { System.out.println(e.getClass()+e.getMessage()); return null;}
	}
}
