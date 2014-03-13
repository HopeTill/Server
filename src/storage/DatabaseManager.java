package storage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import entity.Booking;
import entity.Equipment;
import entity.MultipurposeRoom;
import entity.People;
import entity.Room;
import entity.User;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseManager {
	private static final int DATABASE_VERSION = 2;
	private static final String HOST="host";
	private static final String LOGIN="login";
	private static final String PASSWD="passwd";
	private static final String DATABASE_VERSION_KEY="database_version";
	
	private static DatabaseManager manager;
    private static Logger log=LogManager.getLogger(DatabaseManager.class);
	
	private ConnectionSource source;
	private Dao<Info, Integer> infoDao;

	private Dao<User, Integer> userDao;
	private Dao<People, Integer> peopleDao;
	private Dao<Room, Integer> roomDao;
	private Dao<MultipurposeRoom, Integer> multipurposeRoomDao;
	private Dao<Equipment, Integer> equipmentDao;
	private Dao<Booking, Integer> bookingDao;
	private Dao<AssBookingRoom, Integer> assBookingRoomDao;
	private Dao<AssBookingEquipment, Integer> assBookingEquipmentDao;
	
	
	private DatabaseManager(ConnectionSource source){
		this.source=source;
		
		try {
			TableUtils.createTableIfNotExists(source, Info.class);
			QueryBuilder<Info, Integer> queryEvent=getInfoDao().queryBuilder();
			queryEvent.where().eq("key", DATABASE_VERSION_KEY);
			Info info=queryEvent.queryForFirst();
			
			if(info==null){
				init();
				
				info=new Info();
				info.key=DATABASE_VERSION_KEY;
				info.value=Integer.toString(DATABASE_VERSION);
			}
			else{
				int lastVersion=Integer.parseInt(info.value);
				
				if(lastVersion < DATABASE_VERSION) upgrade(lastVersion, DATABASE_VERSION);
				
				info.value=Integer.toString(DATABASE_VERSION);
				getInfoDao().createOrUpdate(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DatabaseManager getManager(){
		if(manager==null){
			Properties properties=new Properties();
			
			try {
				properties.load(DatabaseManager.class.getResourceAsStream("database.properties"));
				
				JdbcConnectionSource source=new JdbcConnectionSource(
						properties.getProperty(HOST),
						properties.getProperty(LOGIN),
						properties.getProperty(PASSWD),
						DatabaseTypeUtils.createDatabaseType(properties.getProperty(HOST)));
				
				manager=new DatabaseManager(source);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return manager;
	}
	
	private void init() throws SQLException{
		log.info(this.getClass().getSimpleName(), "Init database");
		
		TableUtils.createTable(source, User.class);
		TableUtils.createTable(source, People.class);
		TableUtils.createTable(source, Room.class);
		TableUtils.createTable(source, MultipurposeRoom.class);
		TableUtils.createTable(source, Equipment.class);
	}
	
	private void upgrade(int lastVerison, int newVersion) throws SQLException{
		log.info(this.getClass().getSimpleName(), "Upgrade database from "+lastVerison+" to "+newVersion);
		
		TableUtils.dropTable(source, User.class, true);	
		TableUtils.dropTable(source, People.class, true);
		TableUtils.dropTable(source, Equipment.class, true);
		TableUtils.dropTable(source, Room.class, true);
		TableUtils.dropTable(source, MultipurposeRoom.class, true);
		
		TableUtils.createTable(source, User.class);
		TableUtils.createTable(source, People.class);
		TableUtils.createTable(source, Room.class);
		TableUtils.createTable(source, MultipurposeRoom.class);
		TableUtils.createTable(source, Equipment.class);
	}

	public Dao<User, Integer> getUserDao() throws SQLException {
		if(userDao==null){
			userDao=DaoManager.createDao(source, User.class);
		}
		
		return userDao;
	}

	public Dao<People, Integer> getPeopleDao() throws SQLException {
		if(peopleDao==null){
			peopleDao=DaoManager.createDao(source, People.class);
		}
		
		return peopleDao;
	}

	public Dao<Room, Integer> getRoomDao() throws SQLException {
		if(roomDao==null){
			roomDao=DaoManager.createDao(source, Room.class);
		}
		
		return roomDao;
	}
	
	public Dao<MultipurposeRoom, Integer> getMultipurposeRoomDao() throws SQLException {
		if(multipurposeRoomDao==null){
			multipurposeRoomDao=DaoManager.createDao(source, MultipurposeRoom.class);
		}
		
		return multipurposeRoomDao;
	}
	
	public Dao<Equipment, Integer> getEquipmentDao() throws SQLException {
		if(equipmentDao==null){
			equipmentDao=DaoManager.createDao(source, Equipment.class);
		}
		
		return equipmentDao;
	}
	
	public Dao<Booking, Integer> getBookingDao() throws SQLException {
		if(bookingDao==null){
			bookingDao=DaoManager.createDao(source, Booking.class);
		}
		
		return bookingDao;
	}
	
	protected Dao<AssBookingEquipment, Integer> getAssBookingEquipmentDao() throws SQLException {
		if(assBookingEquipmentDao==null){
			assBookingEquipmentDao=DaoManager.createDao(source, AssBookingEquipment.class);
		}
		
		return assBookingEquipmentDao;
	}
	
	protected Dao<AssBookingRoom, Integer> getAssBookingRoomDao() throws SQLException {
		if(assBookingRoomDao==null){
			assBookingRoomDao=DaoManager.createDao(source, AssBookingRoom.class);
		}
		
		return assBookingRoomDao;
	}
	
	public Dao<Info, Integer> getInfoDao() throws SQLException {
		if(infoDao==null){
			infoDao=DaoManager.createDao(source, Info.class);
		}
		
		return infoDao;
	}

	private static class Info{
		@DatabaseField(generatedId=true)
		public int id;
		@DatabaseField
		public String key;
		@DatabaseField
		public String value;
	}
	
	public List<Room> getRoom(Booking booking){
		try {
			QueryBuilder<AssBookingRoom, Integer> queryAss=getAssBookingRoomDao().queryBuilder();
			QueryBuilder<Room, Integer> query=getRoomDao().queryBuilder();
			
			queryAss.where().eq("booking_id", booking);
			query.join(queryAss);
			
			return query.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Room>();
	}
	
	public List<Equipment> getEquipment(Booking booking){
		try {
			QueryBuilder<AssBookingEquipment, Integer> queryAss=getAssBookingEquipmentDao().queryBuilder();
			QueryBuilder<Equipment, Integer> query=getEquipmentDao().queryBuilder();
			
			queryAss.where().eq("booking_id", booking);
			query.join(queryAss);
			
			return query.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Equipment>();
	}
	
	public boolean addRoomTo(Booking booking, Room room){
		AssBookingRoom ass=new AssBookingRoom();
		
		ass.setBooking(booking);
		ass.setRoom(room);
		
		try {
			return getAssBookingRoomDao().create(ass)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeRoomTo(Booking booking, Room room){
		try {
			DeleteBuilder<AssBookingRoom, Integer> delete=getAssBookingRoomDao().deleteBuilder();
			delete.where().eq("booking_id", booking)
					.and().eq("room_id", room);
			
			return delete.delete()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addEquipmentTo(Booking booking, Equipment equipment){
		AssBookingEquipment ass=new AssBookingEquipment();
		
		ass.setBooking(booking);
		ass.setEquipment(equipment);
		
		try {
			return getAssBookingEquipmentDao().create(ass)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeEquipmentTo(Booking booking, Equipment equipment){
		try {
			DeleteBuilder<AssBookingEquipment, Integer> delete=getAssBookingEquipmentDao().deleteBuilder();
			delete.where().eq("booking_id", booking)
					.and().eq("equipoment_id", equipment);
			
			return delete.delete()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Booking getFullBooking(int id){
		Booking booking = null;
		
		try {
			booking = getBookingDao().queryForId(id);
			
			if(booking!=null){
				booking.setRooms(getRoom(booking));
				booking.setEquipments(getEquipment(booking));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return booking;
	}
	
	public boolean deleteBooking(int id){
		Booking booking = null;
		
		try {
			booking = getBookingDao().queryForId(id);
			
			if(booking!=null){
				for(Room room : getRoom(booking)){
					removeRoomTo(booking, room);
				}
				
				for(Equipment equipment : getEquipment(booking)){
					removeEquipmentTo(booking, equipment);
				}
			}
			
			return getBookingDao().delete(booking)>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
