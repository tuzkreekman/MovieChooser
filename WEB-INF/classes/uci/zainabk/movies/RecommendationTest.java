package uci.zainabk.movies;

import java.net.*;
import java.io.*;
import com.google.gson.stream.*;
import uci.zainabk.database.*;
import java.util.*;


public class RecommendationTest {
	
	public static void main(String[] args) {
		MovieRecommender.recommend(args);
		MovieRecommender.recommendSimilar(10625,args);
		System.out.println(MovieRecommender.findTMDBID("Mean girls"));
	}
}