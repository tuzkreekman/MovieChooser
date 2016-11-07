package uci.zainabk.tvtropes;

import java.io.*;

public class Test{
	public static void main(String[] args) {
		try{
			InputStream is = TVTropeLoader.loadAndCutFront(TVTropeFinder.findTropeURL(args[0]));
			//TVTropeLoader.spitOutTropes(is);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		}
	}
}