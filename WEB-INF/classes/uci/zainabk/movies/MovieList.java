package uci.zainabk.movies;

import java.net.*;
import java.io.*;
import com.google.gson.stream.*;
import java.util.ArrayList;


public class MovieList {
	public static ArrayList<MovieSuggestion> getMovies(String[] args) {
		JsonReader reader;
		ArrayList<MovieSuggestion> suggested = new ArrayList<MovieSuggestion>();
		
		try {
			String inputStr = "https://api.themoviedb.org/3/discover/movie?"
								+"api_key=dbd62543493eb0c4f556525b0dd41010"
								+"&language=en-US"
								+"&sort_by=popularity.desc"
								+"&include_adult=false"
								+"&include_video=false"
								+"&page=1";
			for (int i = 0; i< args.length/2; i++) {
				inputStr = inputStr + "&"+ URLEncoder.encode(args[2*i],"utf-8");
				inputStr = inputStr + "="+ URLEncoder.encode(args[2*i+1],"utf-8");
			}
			
			System.out.println(inputStr);
		
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
			try {
				reader.beginObject();
				while (reader.hasNext()) {
					String name = reader.nextName(); 
					if (name.equals("status_message")) {
						System.out.println(reader.nextString());
						int a = 0/0;
					} else if (name.equals("results")) {
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							MovieSuggestion ms = new MovieSuggestion();
							while (reader.hasNext()) {
								name = reader.nextName();
								if (name.equals("genre_ids")) {
									reader.beginArray();
									while (reader.hasNext()) ms.addGenre(Genre.getGenre(reader.nextInt()));
									reader.endArray();
								} else if (name.equals("title")) ms.setTitle(reader.nextString());
								else if (name.equals("video")) reader.nextBoolean();
								else if (name.equals("adult")) reader.nextBoolean();
								else if (name.equals("id")) ms.setID(reader.nextInt());
								else if (name.equals("vote_count")) reader.nextInt();
								else if (name.equals("vote_average")) reader.nextDouble();
								else if (name.equals("popularity")) ms.setPopularity(reader.nextDouble());
								else {
									if (reader.peek()==JsonToken.NULL) reader.nextNull();
									else reader.nextString();
								}
							}
							suggested.add(ms);
							reader.endObject();
						}
						reader.endArray();
					} else {
						reader.nextString();
					}
				}
				reader.endObject();
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.out.println("TMDB failed: "+e.getMessage());
		} finally {
		   ;
		}
		
		return suggested;
		
	}
}