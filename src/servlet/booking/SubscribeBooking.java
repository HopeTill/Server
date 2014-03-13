package servlet.booking;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.CreateResult;
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
 * Servlet implementation class SubscribeBooking
 */
@WebServlet("/Booking/Subscribe")
public class SubscribeBooking extends MyServlet {
	private static final long serialVersionUID = 1L;

    
    public SubscribeBooking() {
    	super(Booking.BEGIN, Booking.END, Booking.ROOM,
    			Booking.TITLE, Booking.OWNER, Booking.PRICE);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String title=request.getParameter(Booking.TITLE);
		String description=request.getParameter(Booking.DESCRIPTION);
		String begin=request.getParameter(Booking.BEGIN);
		String end=request.getParameter(Booking.END);
		String owner=request.getParameter(Booking.OWNER);
		String roomValue=request.getParameter(Booking.ROOM);
		String price=request.getParameter(Booking.PRICE);
		String confirmed=request.getParameter(Booking.CONFIRMED);
		String equipmentValue=request.getParameter(Booking.EQUIPMENT);
		
		try {
			DatabaseManager manager=DatabaseManager.getManager();
			Booking booking=new Booking();
			
			QueryBuilder<Room, Integer> queryRoom=manager.getRoomDao().queryBuilder();
			queryRoom.where().in("id", Tools.toIntList(roomValue, ";"));
			
			List<Room> rooms=queryRoom.query();
			List<Equipment> equipments=null;
			
			if(!MyServlet.isEmpty(equipmentValue)){
				QueryBuilder<Equipment, Integer> queryEquipment=manager.getEquipmentDao().queryBuilder();
				queryEquipment.where().in("id", Tools.toIntList(equipmentValue, ";"));
				
				equipments=queryEquipment.query();
			}
			
			SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			booking.setBegin(df.parse(begin));
			booking.setEnd(df.parse(end));
			booking.setTitle(title);
			booking.setPrice(Float.parseFloat(price));
			
			People people=manager.getPeopleDao().queryForId(Integer.parseInt(owner));
			if(people==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			else{
				booking.setOwner(people);				
			}
			
			booking.setConfimed(!MyServlet.isEmpty(confirmed) ?
					Boolean.parseBoolean(confirmed)
					: false);
			
			if(!MyServlet.isEmpty(description)){
				booking.setDescription(description);
			}
			
			if(manager.getBookingDao().create(booking)!=1){
				ServletResult.sendResult(response, ServletResult.ERROR);
				return;
			}
			
			for(Room room : rooms){
				manager.addRoomTo(booking, room);
			}
			
			if(equipments!=null){
				for(Equipment equipment : equipments){
					manager.addEquipmentTo(booking, equipment);
				}				
			}
			
			ServletResult.sendResult(response, new CreateResult(
					ServletResult.SUCCESS, booking.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.BAD_DATE_FORMAT);
		}
	}

}
