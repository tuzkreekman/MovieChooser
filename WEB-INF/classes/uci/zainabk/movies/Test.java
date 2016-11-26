package uci.zainabk.movies;

import uci.zainabk.database.Movie;

public class Test {
	public static void main(String[] args) {
		MovieInfo tester = new MovieInfo("Finding Nemo",true);
		System.out.println(tester.getTitle());
		System.out.printf("IMDB: %s\n",tester.getIMDBID());
		System.out.printf("IMDB Score: %f\n",tester.getIMDBScore());
		System.out.printf("On Netflix?: %b\n",tester.isOnNetflix());
		System.out.printf("Found tropes: %b\n",tester.foundTropes());
		System.out.printf("Tropes:\n");
		for (String tag : tester.getTags()) System.out.println(tag);
		System.out.printf("Movie: %s\n",tester.getMovie().toString());
		System.out.printf("Image: %s\n",tester.getImage());
		System.out.printf("Age: %s\n",tester.getAgeRating());
		System.out.printf("Year: %d\n",tester.getYear());
	}
}