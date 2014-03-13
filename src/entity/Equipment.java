package entity;

import com.j256.ormlite.field.DatabaseField;

public class Equipment {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=false)
	private MultipurposeRoom room;
	@DatabaseField(canBeNull=false)
	private String title;
	@DatabaseField(canBeNull=true)
	private String description;
	@DatabaseField(canBeNull=true)
	private boolean available;
	
	
	public static final String MULTIPURPOSE_ROOM="multipurpose_room_id";
	public static final String TITLE="title";
	public static final String DESCRIPTION="description";
	public static final String AVAILABLE="available";
	
	
	public Equipment(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public MultipurposeRoom getRoom() {
		return room;
	}

	public void setRoom(MultipurposeRoom room) {
		this.room = room;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
