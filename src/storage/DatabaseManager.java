package storage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

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
}
