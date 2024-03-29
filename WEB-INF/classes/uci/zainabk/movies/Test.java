package uci.zainabk.movies;

import uci.zainabk.database.Movie;
import uci.zainabk.database.Database;

public class Test {
	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("Requires 1 argument");
			return;
		}
		MovieInfo tester = new MovieInfo(args[0],new Database(),true);
		System.out.println(tester.getTitle());
		System.out.printf("IMDB: %s\n",tester.getIMDBID());
		System.out.printf("IMDB Score: %f\n",tester.getIMDBScore());
		System.out.printf("On Netflix?: %b\n",tester.isOnNetflix());
		System.out.printf("Found tropes: %b\n",tester.foundTropes());
		System.out.printf("Found CSM Rating?: %b\n",tester.foundCSRating());
		System.out.printf("Tropes:\n");
		for (String tag : tester.getTags()) System.out.println(tag);
		System.out.printf("Movie: %s\n",tester.getMovie().toString());
		System.out.printf("Image: %s\n",tester.getImage());
		System.out.printf("Age: %s\n",tester.getAgeRating());
		System.out.printf("Year: %d\n",tester.getYear());
		System.out.printf("CS Age Rating: %d\n",tester.getCSAgeRating());
		System.out.println("Genres");
		for (Genre tag : tester.getGenres()) System.out.println(tag);
	}
}