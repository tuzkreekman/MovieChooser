package uci.zainabk.movies;

import java.net.*;
import java.io.*;
import com.google.gson.stream.*;
import uci.zainabk.database.*;
import java.util.*;


public class MovieRecommender {
	public static final int RECOMMENDATION_SIZE = 15;
	
	public static void main(String[] args) {
		//recommend(args);
		recommendSimilar(10625,args);
	}
	
	public static MovieSuggestion recommendSimilar(int mid, String[] params) {
		Database db = new Database();
		int id = Login.getUserID();
		ArrayList<MovieSuggestion> opts = findSimilarChoices(id,mid,params,db);
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
	
	private static ArrayList<MovieSuggestion> findSimilarChoices(int id, int mid, String[] params, Database db) {
		try{
		
		int maxPage = 0;
		
		ArrayList<MovieSuggestion> movies = findSimilar(mid,params);
		
		for (int i = 0; i <movies.size(); i++) {
			if (i==0) maxPage = movies.get(i).getID();
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
		while ((size=movies.size())<RECOMMENDATION_SIZE && page<maxPage) {
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
	
	public static ArrayList<MovieSuggestion> findSimilar(int id, String[] args) {
		JsonReader reader;
		ArrayList<MovieSuggestion> suggested = new ArrayList<MovieSuggestion>();
		
		try {
			String inputStr = "https://api.themoviedb.org/3/movie/"+id+"/similar?"
								+"api_key=dbd62543493eb0c4f556525b0dd41010"
								+"&language=en-US"
								+"&include_adult=false"
								+"&page=1";
			for (int i = 0; i< args.length/2; i++) {
				inputStr = inputStr + "&"+ URLEncoder.encode(args[2*i],"utf-8");
				inputStr = inputStr + "="+ URLEncoder.encode(args[2*i+1],"utf-8");
			}
			
			System.out.println(inputStr);
		
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
			try {
				reader.beginObject();
				while (reader.hasNext()) {
					String name = reader.nextName(); 
					if (name.equals("status_message")) {
						System.out.println(reader.nextString());
						int a = 0/0;
					} else if (name.equals("results")) {
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							MovieSuggestion ms = new MovieSuggestion();
							while (reader.hasNext()) {
								name = reader.nextName();
								if (name.equals("genre_ids")) {
									reader.beginArray();
									while (reader.hasNext()) ms.addGenre(Genre.getGenre(reader.nextInt()));
									reader.endArray();
								} else if (name.equals("title")) ms.setTitle(reader.nextString());
								else if (name.equals("video")) reader.nextBoolean();
								else if (name.equals("adult")) reader.nextBoolean();
								else if (name.equals("id")) ms.setID(reader.nextInt());
								else if (name.equals("vote_count")) reader.nextInt();
								else if (name.equals("vote_average")) reader.nextDouble();
								else if (name.equals("popularity")) ms.setPopularity(reader.nextDouble());
								else {
									if (reader.peek()==JsonToken.NULL) reader.nextNull();
									else reader.nextString();
								}
							}
							suggested.add(ms);
							reader.endObject();
						}
						reader.endArray();
					} else if (name.equals("total_pages")) {
						suggested.get(0).setID(reader.nextInt()); //hack to get MAXPAGE cuz i'm lazy 
					} else {
						reader.nextString();
					}
				}
				reader.endObject();
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.out.println("TMDB failed: "+e.getMessage());
		} finally {
		   ;
		}
		
		return suggested;
		
	
	}
	
	public static int findTMDBID(String searchStr) {
		JsonReader reader;
		ArrayList<MovieSuggestion> suggested = new ArrayList<MovieSuggestion>();
		
		try {
			String inputStr = "https://api.themoviedb.org/3/search/movie?"
								+"api_key=dbd62543493eb0c4f556525b0dd41010"
								+"&language=en-US"
								+"&include_adult=false"
								+"&page=1"
								+"&query="+URLEncoder.encode(searchStr,"utf-8");
			
			System.out.println(inputStr);
		
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
			try {
				reader.beginObject();
				while (reader.hasNext()) {
					String name = reader.nextName(); 
					if (name.equals("status_message")) {
						System.out.println(reader.nextString());
						int a = 0/0;
					} else if (name.equals("results")) {
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							MovieSuggestion ms = new MovieSuggestion();
							while (reader.hasNext()) {
								name = reader.nextName();
								if (name.equals("genre_ids")) {
									reader.beginArray();
									while (reader.hasNext()) ms.addGenre(Genre.getGenre(reader.nextInt()));
									reader.endArray();
								} else if (name.equals("title")) ms.setTitle(reader.nextString());
								else if (name.equals("video")) reader.nextBoolean();
								else if (name.equals("adult")) reader.nextBoolean();
								else if (name.equals("id")) ms.setID(reader.nextInt());
								else if (name.equals("vote_count")) reader.nextInt();
								else if (name.equals("vote_average")) reader.nextDouble();
								else if (name.equals("popularity")) ms.setPopularity(reader.nextDouble());
								else {
									if (reader.peek()==JsonToken.NULL) reader.nextNull();
									else reader.nextString();
								}
							}
							suggested.add(ms);
							reader.endObject();
						}
						reader.endArray();
					} else {
						reader.nextString();
					}
				}
				reader.endObject();
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.out.println("TMDB failed: "+e.getMessage());
		} finally {
		   ;
		}
		
		return suggested.get(0).getID();
		
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
