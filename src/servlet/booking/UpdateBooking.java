package servlet.booking;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import app.Tools;

import com.j256.ormlite.stmt.QueryBuilder;

import entity.Booking;
import entity.Equipment;
import entity.People;
import entity.Room;

/**
 * Servlet implementation class UpdateBooking
 */
@WebServlet("/Booking/Update")
public class UpdateBooking extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ADD_ROOM="add_room_list";
	private static final String REMOVE_ROOM="remove_room_list";
	private static final String ADD_EQUIPMENT="add_equipment_list";
	private static final String REMOVE_EQUIPMENT="remove_equipment_list";

    /**
     * Default constructor. 
     */
    public UpdateBooking() {
        super(ID);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		String title=request.getParameter(Booking.TITLE);
		String description=request.getParameter(Booking.DESCRIPTION);
		String begin=request.getParameter(Booking.BEGIN);
		String end=request.getParameter(Booking.END);
		String owner=request.getParameter(Booking.OWNER);
		String price=request.getParameter(Booking.PRICE);
		String confirmed=request.getParameter(Booking.CONFIRMED);
		String addRoomValue=request.getParameter(ADD_ROOM);
		String removeRoomValue=request.getParameter(REMOVE_ROOM);
		String addEquipmentValue=request.getParameter(ADD_EQUIPMENT);
		String removeEquipmentValue=request.getParameter(REMOVE_EQUIPMENT);
		
		try {
			Booking booking=manager.getBookingDao().queryForId(
					Integer.parseInt(ID));
			
			if(booking==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			
			if(!MyServlet.isEmpty(title)){
				booking.setTitle(title);
			}
			
			if(!MyServlet.isEmpty(description)){
				booking.setDescription(description);
			}
			
			SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			if(!MyServlet.isEmpty(begin)){
				booking.setBegin(df.parse(begin));
			}
			
			if(!MyServlet.isEmpty(end)){
				booking.setEnd(df.parse(end));
			}
			
			People people=manager.getPeopleDao().queryForId(Integer.parseInt(owner));
			if(people==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			else{
				booking.setOwner(people);
			}
			
			if(!MyServlet.isEmpty(price)){
				booking.setPrice(Float.parseFloat(price));
			}
			
			booking.setConfimed(!MyServlet.isEmpty(confirmed) ?
					Boolean.parseBoolean(confirmed)
					: false);
			
			if(!MyServlet.isEmpty(addRoomValue)){
				QueryBuilder<Room, Integer> query=manager.getRoomDao().queryBuilder();
				query.where().in("id", Tools.toIntList(addRoomValue, ";"));
				
				for(Room room : query.query()){
					manager.addRoomTo(booking, room);
				}
			}
			
			if(!MyServlet.isEmpty(removeRoomValue)){
				QueryBuilder<Room, Integer> query=manager.getRoomDao().queryBuilder();
				query.where().in("id", Tools.toIntList(addRoomValue, ";"));
				
				for(Room room : query.query()){
					manager.removeRoomTo(booking, room);
				}
			}
			
			if(!MyServlet.isEmpty(addEquipmentValue)){
				QueryBuilder<Equipment, Integer> query=manager.getEquipmentDao().queryBuilder();
				query.where().in("id", Tools.toIntList(addEquipmentValue, ";"));
				
				for(Equipment equipment : query.query()){
					manager.addEquipmentTo(booking, equipment);
				}
			}
			
			if(!MyServlet.isEmpty(removeEquipmentValue)){
				QueryBuilder<Equipment, Integer> query=manager.getEquipmentDao().queryBuilder();
				query.where().in("id", Tools.toIntList(addEquipmentValue, ";"));
				
				for(Equipment equipment : query.query()){
					manager.removeEquipmentTo(booking, equipment);
				}
			}
			
			ServletResult.sendResult(response, 
					manager.getBookingDao().update(booking)==1 ?
							ServletResult.SUCCESS
							: ServletResult.ERROR);
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.BAD_DATE_FORMAT);
		}		
	}

}
