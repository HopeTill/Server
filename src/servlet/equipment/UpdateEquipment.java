package servlet.equipment;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.Equipment;
import entity.MultipurposeRoom;

/**
 * Servlet implementation class UpdateEquipment
 */
@WebServlet("/Equipment/Update")
public class UpdateEquipment extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public UpdateEquipment() {
    	super(ID);
    }
    
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			Equipment equipment = manager.getEquipmentDao().queryForId(
					Integer.parseInt(request.getParameter(ID)));
			
			String title=request.getParameter(Equipment.TITLE);
			String masterRoom=request.getParameter(Equipment.MULTIPURPOSE_ROOM);
			String description=request.getParameter(Equipment.DESCRIPTION);
			String available=request.getParameter(Equipment.AVAILABLE);
			
			if(!MyServlet.isEmpty(title)){
				equipment.setTitle(title);
			}
			
			if(!MyServlet.isEmpty(masterRoom)){
				MultipurposeRoom room=manager.getMultipurposeRoomDao().queryForId(
						Integer.parseInt(masterRoom));
				
				if(room==null){
					ServletResult.sendResult(response, ServletResult.NOT_FOUND);
					return;
				}
				
				equipment.setRoom(room);
			}
			
			if(!MyServlet.isEmpty(description)){
				equipment.setDescription(description);
			}
			
			if(!MyServlet.isEmpty(available)){
				equipment.setAvailable(Boolean.parseBoolean(available));
			}
			
			ServletResult.sendResult(response, 
					manager.getEquipmentDao().update(equipment)==1 ?
							ServletResult.SUCCESS
							: ServletResult.ERROR);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.BAD_NUMBER_FORMAT);
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
