package uci.zainabk.database;


public class UserTest{
	public static void main(String[] args) {
		try {
			System.out.println("Begin USERTEST: Expect (LOGGED IN), F, T")
			Database db = new Database();
			UserDatabase udb = new UserDatabase(db.getConnection());
			int id = Login.getUserID();
			User user = udb.getUser(Integer.toString(id),true);
			System.out.println(user);
			id = 1; // always only play with USER1
			user = udb.getUser(Integer.toString(id),true);
			udb.editUsername(id,"ginevera");
			System.out.println(user.equals(udb.getUser(Integer.toString(id),true)));
			udb.editPassword(id,"tomfelton");
			user = udb.getUser(Integer.toString(id),true);
			System.out.println(user.isPassword("tomfelton"));
			udb.editUsername(id,"Ginevra"); //revert
			udb.editPassword(id,"Weasley"); //revert
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("OH WELL");
		} finally{
			;
		}
	}
	
}