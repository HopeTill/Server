package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.j256.ormlite.field.DatabaseField;

public class Booking {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false)
	private String title;
	@DatabaseField(canBeNull=true)
	private String description;
	@DatabaseField(canBeNull=false)
	private Date begin;
	@DatabaseField(canBeNull=false)
	private Date end;
	@DatabaseField(canBeNull=false, foreign=true, foreignAutoRefresh=true)
	private People owner;
	@DatabaseField
	private float price;
	@DatabaseField
	private boolean confimed;
	private List<Room> rooms=new ArrayList<Room>();
	private List<Equipment> equipments=new ArrayList<Equipment>();
	
	
	public static final String TITLE="title";
	public static final String DESCRIPTION="description";
	public static final String BEGIN="begin_date";
	public static final String END="end_date";
	public static final String OWNER="people_id";
	public static final String PRICE="price";
	public static final String CONFIRMED="is_confirmed";
	public static final String ROOM="room_list";
	public static final String EQUIPMENT="equipment_list";
	
	
	public Booking(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	@JsonFormat(shape=Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	@JsonFormat(shape=Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public People getOwner() {
		return owner;
	}

	public void setOwner(People owner) {
		this.owner = owner;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isConfimed() {
		return confimed;
	}

	public void setConfimed(boolean confimed) {
		this.confimed = confimed;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}
}
