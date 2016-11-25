package uci.zainabk.tvtropes;

import java.util.*;

public class DBTest{
	public static void main(String[] args) {
		String url = "http://dbtropes.org/resource/Film/Matilda";
		ArrayList<String> bag = DBTropeLoader.getFilmTropes(url);
	}
}