package uci.zainabk.csm;

public class CSMTest {
	public static void main(String[] args) {
		String url = "";
		try{
			url = CSMFinder.findCSMURL("Home Alone");
		} catch (Exception e) {
			System.out.println("CSM Finding failed: "+e.getMessage());
			url = "https://www.commonsensemedia.org/movie-reviews/home-alone";
		}
		System.out.println(url);
		int rating = CommonSenseLoader.getAgeRating(url);
		System.out.printf("Home Alone is recommoned for ages %d+\n",rating);
	}
}