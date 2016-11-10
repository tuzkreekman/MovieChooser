package uci.zainabk.database;

public class User { 
	public final int id; 
	public final String name; 
	private String password;
	
	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public boolean isPassword(String password) { return this.password.equals(password); }
	
	public boolean equals(User x) {
		return (this.id==x.id && this.name==x.name && x.isPassword(password));
	}

}