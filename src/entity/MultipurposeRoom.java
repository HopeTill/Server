package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="multipurpose_room")
public class MultipurposeRoom {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false)
	private String name;
	@DatabaseField(canBeNull=false)
	private String location;
	
	public MultipurposeRoom(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
