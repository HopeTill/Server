package storage;

import com.j256.ormlite.field.DatabaseField;

import entity.Booking;
import entity.Equipment;

class AssBookingEquipment {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=false, uniqueCombo=true)
	private Booking booking;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=false, uniqueCombo=true)
	private Equipment equipment;
	
	
	public AssBookingEquipment(){
		
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

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
}
