package uci.zainabk.movies;

import java.util.ArrayList;

public class MovieSuggestion{
	private String title = "";
	private String ageRating = "";
	private double popularity = 0;
	private int id = 0;
	private ArrayList<Genre> genres = new ArrayList<Genre>();
	public MovieSuggestion() {
	}
	
	public void addGenre(Genre g) { genres.add(g);}
	public void setPopularity(double d) {popularity = d;}
	public void setAgeRating(String a) { ageRating=a;}
	public void setTitle(String t) {title=t;}
	public void setID(int i) {id = i;}
	
	public double getPopularity() {return popularity;}
	public String getTitle() { return title;}
	public String getAgeRating() {return ageRating;}
	public ArrayList<Genre> getGenres() {return genres;}
	public int getID() {return id;}
}