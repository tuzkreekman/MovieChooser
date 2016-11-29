package uci.zainabk.tvtropes;

import java.util.*;

public class TVTropeTest{
	public static void main(String[] args) {
		String url = "http://tvtropes.org/pmwiki/pmwiki.php/Film/HomeAlone";
		ArrayList<String> bag = TVTropeLoader.getTropes(url);
		for (String t : bag) System.out.println(t);
	}
}