package entity;

import com.j256.ormlite.field.DatabaseField;

public class User {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(unique=true, canBeNull=false)
	private String login;
	@DatabaseField(canBeNull=false)
	private String password;
	@DatabaseField(canBeNull=true)
	private String firstName;
	@DatabaseField(canBeNull=true)
	private String lastName;

	
	public static final String LOGIN="login";
	public static final String PASSWORD="password";
	public static final String FIRST_NAME="first_name";
	public static final String LAST_NAME="last_name";
	
	
	public User(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
