package uci.zainabk.imdb;

public class Test{
	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("Requires 1 argument");
			return;
		}
		System.out.println(IMDB.getID(args[0]));
	}
}