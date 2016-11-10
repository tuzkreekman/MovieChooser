package uci.zainabk.database;

public class Movie { 
	public final int id; 
	public final String imdb;
	
	public Movie(int id, String imdb) {
		this.id = id;
		this.imdb = imdb;
	}
	
	public String toString() {
		return Integer.toString(id)+": "+imdb;
	}

}