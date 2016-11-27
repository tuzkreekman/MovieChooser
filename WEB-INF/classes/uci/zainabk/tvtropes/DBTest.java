package uci.zainabk.tvtropes;

import java.util.*;

public class DBTest{
	public static void main(String[] args) {
		String url = "http://dbtropes.org/resource/Film/Matilda";
		ArrayList<Trope> bag = DBTropeLoader.getFilmTropes(url);
		for (Trope t : bag) System.out.println(t.title);
	}
}