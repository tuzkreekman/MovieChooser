package uci.zainabk.database;


public class UserTest{
	public static void main(String[] args) {
		try {
			Database db = new Database();
			UserDatabase udb = new UserDatabase(db.getConnection());
			int id = Login.getUserID();
			User user = udb.getUser(Integer.toString(id),true);
			udb.editUsername(id,"boylton");
			user = udb.getUser(Integer.toString(id),true);
			udb.editUsername(id,"ginevera");
			System.out.println(user.equals(udb.getUser(Integer.toString(id),true)));
			udb.editPassword(id,"tomfelton");
			user = udb.getUser(Integer.toString(id),true);
			System.out.println(user.isPassword("tomfelton"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		} finally{
			;
		}
	}
	
}