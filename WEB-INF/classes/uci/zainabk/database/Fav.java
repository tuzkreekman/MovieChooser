package uci.zainabk.database;

public class Fav {
	public final int movie_id, user_id;
	
	public Fav(int mid, int uid) {
		movie_id = mid;
		user_id = uid;
	}
	
	public String toString() {
		return "User "+Integer.toString(user_id)+" likes movie "+Integer.toString(movie_id);
	}
	
	public boolean equals(Fav oth) {
		return (oth.movie_id==this.movie_id)&&(oth.user_id==this.user_id);
	}
	
}