package pl.pg.mif.androVote.WCFConnector;

/**
 * Class represents user in system.
 * @author Erdk
 *
 */
public class UserInfo {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public String getUserFirstAndLastName() {
		return userFirstAndLastName;
	}
	public void setUserFirstAndLastName(String userFirstAndLastName) {
		this.userFirstAndLastName = userFirstAndLastName;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * User id in database, used in voting!
	 */
	int id;
	
	/**
	 * User username used at login time.
	 */
	String username;
	
	/**
	 * User's password.
	 */
	String password;
	
	/**
	 * IMPORTANT! Determines wheater user is admin.
	 */
	boolean isAdmin;
	
	
	public boolean isLogged() {
		return isLogged;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	boolean isLogged;
	
	/**
	 * Real first and lastname from database
	 */
	String userFirstAndLastName;
}
