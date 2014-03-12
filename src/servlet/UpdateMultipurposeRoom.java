package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage.DatabaseManager;
import entity.MultipurposeRoom;

/**
 * Servlet implementation class UpdateMultipurposeRoom
 */
@WebServlet("/UpdateMultipurposeRoom")
public class UpdateMultipurposeRoom extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	public UpdateMultipurposeRoom(){
		super(ID);
	}
	
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		MultipurposeRoom room;
		try {
			room = manager.getMultipurposeRoomDao().queryForId(
					Integer.parseInt(request.getParameter(ID)));
			
			if(room==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			
			String name=request.getParameter(MultipurposeRoom.NAME);
			String location=request.getParameter(MultipurposeRoom.LOCATION);
			
			if(!MyServlet.isEmpty(name)){
				room.setName(name);
			}
			
			if(!MyServlet.isEmpty(location)){
				room.setLocation(location);
			}
			
			ServletResult.sendResult(response, 
					manager.getMultipurposeRoomDao().update(room)==1 ?
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
