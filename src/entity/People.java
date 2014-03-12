package entity;

import com.j256.ormlite.field.DatabaseField;

public class People {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false)
	private String firstName;
	@DatabaseField(canBeNull=false)
	private String lastName;
	@DatabaseField(canBeNull=true)
	private String email;
	@DatabaseField(canBeNull=true)
	private String phone;
	
	
	public static final String FIRST_NAME="first_name";
	public static final String LAST_NAME="last_name";
	public static final String EMAIL="email";
	public static final String PHONE="phone";
	
	
	public People(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
