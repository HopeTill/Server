package servlet.equipment;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.CreateResult;
import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.Equipment;
import entity.MultipurposeRoom;

/**
 * Servlet implementation class SubscribeEquipment
 */
@WebServlet("/Equipment/Subscribe")
public class SubscribeEquipment extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public SubscribeEquipment() {
        super(Equipment.MULTIPURPOSE_ROOM, Equipment.TITLE);
    }
    
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try{
			String title=request.getParameter(Equipment.TITLE);
			String masterRoom=request.getParameter(Equipment.MULTIPURPOSE_ROOM);
			String description=request.getParameter(Equipment.DESCRIPTION);
			String available=request.getParameter(Equipment.AVAILABLE);
			
			MultipurposeRoom room=manager.getMultipurposeRoomDao().queryForId(
					Integer.parseInt(masterRoom));
			boolean isAvailable = !MyServlet.isEmpty(available) ? 
					Boolean.parseBoolean(available)
					: true;
					
			if(room==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			
			Equipment equipment=new Equipment();
			equipment.setRoom(room);
			equipment.setTitle(title);
			equipment.setAvailable(isAvailable);
			
			if(MyServlet.isEmpty(description)) equipment.setDescription(description);
				
			if(manager.getEquipmentDao().create(equipment)==1){
				ServletResult.sendResult(response, new CreateResult(
						ServletResult.SUCCESS, equipment.getId()));
				
				response.setStatus(HttpServletResponse.SC_CREATED);
			}
			else{
				ServletResult.sendResult(response, ServletResult.ERROR);
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.BAD_NUMBER_FORMAT);
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
