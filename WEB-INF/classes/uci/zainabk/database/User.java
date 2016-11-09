package uci.zainabk.database;

public class User { 
	public final int id; 
	public final String name; 
	private String password;
	
	public User(int id, String name, String imdb) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public boolean isPassword(String password) { return this.password.equals(password); }

}