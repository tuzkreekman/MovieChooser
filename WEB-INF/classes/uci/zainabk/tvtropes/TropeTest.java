package uci.zainabk.tvtropes;

public class TropeTest {
	public static void main(String[] args) {
		String original = "http://dbtropes.org/resource/Main/ImpairmentShot";
		Trope t = new Trope(DBTropeLoader.convertDBURI(original));
		//Trope t = new Trope("http://tvtropes.org/pmwiki/pmwiki.php/Main/ImpairmentShot");
		System.out.println(t.title);
	}
}