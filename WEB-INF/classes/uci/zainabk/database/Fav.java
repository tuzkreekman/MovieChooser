package uci.zainabk.database;

public class Fav {
	public final int movie_id, user_id, opinion;
	// opinion: 0 hate, 1 watched, 2 love
	
	public Fav(int uid, int mid, int opine) {
		movie_id = mid;
		user_id = uid;
		opinion = opine;
	}
	
	public String toString() {
		return "User "+Integer.toString(user_id)+" likes movie "
				+Integer.toString(movie_id)+" with feeling "+Integer.toString(opinion);
	}
	
	public boolean equals(Fav oth) {
		return (oth.movie_id==this.movie_id)&&(oth.user_id==this.user_id);
	}
	
	public int getOpinion() {
		return opinion;
	}
	
}