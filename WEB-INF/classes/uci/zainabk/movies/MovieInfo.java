package uci.zainabk.movies;

import uci.zainabk.imdb.*;
import uci.zainabk.database.*;
import uci.zainabk.tvtropes.*;
import uci.zainabk.csm.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.util.ArrayList;

public class MovieInfo {
	public static final String TROPELESS = "tropeless";
	
	
	private boolean netflix = false, tropes = false, genres=false, commonsense=false;
	private String iMDB = "";
	private String title = "";
	private String img = "";
	private String ageRating = "";
	private Movie m = null;
	private int year = 0;
	private int metaScore = 0;
	private int csrating = 0;
	private double imdbScore = 0;
	private ArrayList<String> tag = new ArrayList<String>();
	private ArrayList<Genre> gBag = new ArrayList<Genre>();
	
	/*	Takes in title and searches for the IMDB id
		Adds the movie to the database if it isn't there */
	public MovieInfo(String searchStr, Database db) {
		iMDB = IMDB.getID(searchStr);
		if (iMDB==null || iMDB.equals("")) {
			iMDB = null;
			return;
		}
		MovieDatabase mdb = new MovieDatabase(db.getConnection());
		try {
			m = mdb.getMovie(-1,iMDB);
			if (m==null) {
				mdb.addMovie(iMDB);
				m = mdb.getMovie(-1,iMDB);
			}
		} catch (Exception e) {
			System.out.println("Had some issue with  movie_chooser.movie: "+e.getMessage());
			iMDB = null;
		}
	}
	
	/*	Takes in IMDB id
		Assumes that movie is in the database */
	public MovieInfo(String imdb) {
		iMDB = imdb;
	}
	
	/* 	Takes in title and searches for the IMDB id
		Adds the movie to the database if it isn't there 
		Loads all information it can get */
	public MovieInfo(String searchStr, Database db, boolean autoLoad) {
		this(searchStr,db);
		if (iMDB==null) return;
		if (autoLoad) loadAllInfo();
	}
	
	/* Loads IMDB info, Netflix info, tropes, genres */
	public void loadAllInfo() {
		this.getInfoFromIMDB();
		this.checkNetflix();
		this.loadCommonSense();
		this.loadTropes();
		this.loadGenres();
	}
	
	public void getInfoFromIMDB() {
		if (iMDB==null) return;
		
		JsonReader reader;
		
		try {
			String inputStr = "http://www.omdbapi.com/?i="+URLEncoder.encode(iMDB,"utf-8");
		
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
				
			reader.beginObject();
			
			try {
				while (reader.hasNext()) {
					String name = reader.nextName(); 
					if (name.equals("Response")) {
						int a;
						if (reader.nextString().equals("False")) {
							a = 0/0;
						}
					} else if (name.equals("Type")) {
						String type= (reader.nextString());
						int a;
						if (!(type.equals("movie") || type.equals("N/A"))) {
							iMDB = null;
							a = 0/0;
						}
					} else if (name.equals("Poster")) {
						img = reader.nextString();
					} else if (name.equals("Title")) {
						title = reader.nextString();
					} else if (name.equals("Metascore")) {
						String meta = reader.nextString();
						if (!meta.contains("N"))
							metaScore = Integer.parseInt(meta);
					} else if (name.equals("Year")) {
						String meta = reader.nextString();
						if (!meta.contains("N"))
							year = Integer.parseInt(meta);
					} else if (name.equals("imdbRating")) {
						String meta = reader.nextString();
						if (!meta.contains("N"))
							imdbScore = Double.parseDouble(meta);
					} else if (name.equals("Rated")) {
						ageRating = reader.nextString();
					} else {
						reader.nextString();
					}
				}
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.out.println("IMDB failed: "+e.getMessage());
		} finally {
		   ;
		}
	}
	
	public void checkNetflix() {
		if (iMDB==null) return;
		
		JsonReader reader;
		netflix = true;
		
		try {
			String inputStr = "http://netflixroulette.net/api/api.php?title="+URLEncoder.encode(title,"utf-8");
		
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
				
			reader.beginObject();
			
			try {
				while (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("errorcode")) {
						System.out.println("Error: "+reader.nextString());
					} else if (name.equals("message")) {
						System.out.println("Message: "+reader.nextString());
					} else if (name.equals("poster")) {
						String pic = reader.nextString();
						if (img.equals("")) img = pic;
					} else if (name.equals("release_year")) {
						String meta = reader.nextString();
						if (!meta.contains("N"))
							year = Integer.parseInt(meta);
					} else if (name.equals("category")) {
						String category = reader.nextString();
						if (!category.equals("N/A")) tag.add(category);
					} else reader.nextString();
				}
				reader.endObject();
			} catch (Exception e) {
				netflix = false;
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			netflix = false;
		} finally {
		   ;
		}
	}
	
	public void loadTropes() {
		if (iMDB==null) return;
		
		if (tag.indexOf(TROPELESS)!=-1) tag.remove(TROPELESS);
		tropes = true;
		String url = "";
		try {
			url = TVTropeFinder.findTropeURL(title);
		} catch (Exception e) { 
			tropes = false; 
			System.out.println("Load trope failed: "+e.getMessage());
			return;
		}
		System.out.println("Loading tropes from "+url);
		for (String t : TVTropeLoader.getTropes(url))
			tag.add(t);
	}
	
	public void loadCommonSense() {
		if (iMDB==null) return;
		
		commonsense = true;
		String url = "";
		try{
			url = CSMFinder.findCSMURL(title);
		} catch (Exception e) {
			System.out.println("CSM Finding failed: "+e.getMessage());
			commonsense = false;
			return;
		}
		System.out.println("Loading commonsense from "+url);
		csrating = CommonSenseLoader.getAgeRating(url);
	}
	
	public void loadGenres() {
		if (iMDB==null) return;
		
		JsonReader reader;
		genres = true;
					
		try {
			String inputStr = "https://api.themoviedb.org/3/find/"+iMDB+"?api_key=dbd62543493eb0c4f556525b0dd41010&language=en-US&external_source=imdb_id";
			
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("movie_results")) {
					reader.beginArray();
					while (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							name = reader.nextName();
							if (name.equals("genre_ids")) {
								reader.beginArray();
								while (reader.hasNext()) gBag.add(Genre.getGenre(reader.nextInt()));
								reader.endArray();
							} else if (name.equals("video")) reader.nextBoolean();
							else if (name.equals("adult")) reader.nextBoolean();
							else if (name.equals("id")) reader.nextInt();
							else if (name.equals("vote_count")) reader.nextInt();
							else if (name.equals("vote_average")) reader.nextDouble();
							else if (name.equals("popularity")) reader.nextDouble();
							else { reader.nextString(); }
						}
						reader.endObject();
					}
					reader.endArray();
				} else {
					reader.beginArray();
					while (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							name = reader.nextName();
							if (name.equals("genre_ids")) {
								reader.beginArray();
								while (reader.hasNext()) reader.nextInt();
								reader.endArray();
							} else if (name.equals("video")) reader.nextBoolean();
							else if (name.equals("adult")) reader.nextBoolean();
							else if (name.equals("id")) reader.nextInt();
							else if (name.equals("vote_count")) reader.nextInt();
							else if (name.equals("vote_average")) reader.nextDouble();
							else if (name.equals("popularity")) reader.nextDouble();
							else { reader.nextString(); }
						}
						reader.endObject();
					}
					reader.endArray();
				}
			}
			reader.endObject();
			reader.close();
		} catch (Exception e) {
			System.out.print(e.getMessage());
			genres = false;
		} finally {
		   ;
		}
	}
	
	/* Access methods */
	public boolean isOnNetflix() 	{ return netflix;	 	}
	public boolean hasGenres()	 	{ return genres; 		}
	public boolean foundTropes() 	{ return tropes; 		}
	public boolean foundCSRating() 	{ return commonsense; 	}
	public String getIMDBID() 		{ return iMDB;			}
	public String getTitle() 		{ return title;			}
	public String getImage() 		{ return img;			}
	public String getAgeRating() 	{ return ageRating;		}
	public int getYear()			{ return year;			}
	public int getMetaScore() 		{ return metaScore;		}
	public double getIMDBScore()	{ return imdbScore;		}
	public Movie getMovie()			{ return m;				}
	public int getCSAgeRating() 	{ return csrating;		}
	
	public ArrayList<String> getTags() {
		if (!tropes) 
			if (tag.indexOf(TROPELESS)==-1)
				tag.add(TROPELESS);
		return tag;
	}
	
	public ArrayList<Genre> getGenres() {
		return gBag;
	}
	
}