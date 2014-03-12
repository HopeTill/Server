package entity;

import com.j256.ormlite.field.DatabaseField;

public class Room {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=false)
	private MultipurposeRoom room;
	@DatabaseField
	private int capacity;
	@DatabaseField
	private String title;
	
	
	public static final String TITLE="title";
	public static final String MULTIPURPOSE_ROOM="multipurpose_room_id";
	public static final String CAPACITY="capacity";
	
	
	public Room(){
		
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
