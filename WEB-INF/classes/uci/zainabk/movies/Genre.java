package uci.zainabk.movies;
public enum Genre {

	ACTION(28,"Action"),
	ADVENTURE(12,"Adventure"),
	ANIMATION(16,"Animation"),
	COMEDY(35,"Comedy"),
	CRIME(80,"Crime"),
	DOCUMENTARY(99,"Documentary"),
	DRAMA(18,"Drama"),
	FAMILY(10751,"Family"),
	FANTASY(14,"Fantasy"),
	HISTORY(36,"History"),
	HORROR(27,"Horror"),
	MUSIC(10402,"Music"),
	MYSTERY(9648,"Mystery"),
	ROMANCE(10749,"Romance"),
	SCIFI(878,"Science Fiction"),
	TVMOVIE(10770,"TV Movie"),
	THRILLER(53,"Thriller"),
	WAR(10752,"War"),
	WESTERN(37,"Western");

	private final int id;
	private final String name;

	Genre(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public static Genre getGenre(int id) {
		for (Genre g : Genre.values()) {
			if (g.getID()==id) return g;
		}
		return null;
	}
	
	public String getName() { return name; }
	public int getID() { return id; }
}
