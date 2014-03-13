package storage;

import com.j256.ormlite.field.DatabaseField;

import entity.Booking;
import entity.Room;

class AssBookingRoom {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=false, uniqueCombo=true)
	private Booking booking;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=false, uniqueCombo=true)
	private Room room;
	
	
	public AssBookingRoom(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
