package uci.zainabk.imdb;

public class Test{
	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("Requires 1 argument");
			return;
		}
		String id = IMDB.getID(args[0]);
		System.out.println(id);
		System.out.println(IMDB.getTitle(id));
	}
}