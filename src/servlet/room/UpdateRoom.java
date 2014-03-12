package servlet.room;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.Room;

/**
 * Servlet implementation class UpdateRoom
 */
@WebServlet("/Room/Update")
public class UpdateRoom extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public UpdateRoom() {
        super(ID);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			Room room = manager.getRoomDao().queryForId(
					Integer.parseInt(request.getParameter(ID)));
			
			String title=request.getParameter(Room.TITLE);
			String capacity=request.getParameter(Room.CAPACITY);
			
			if(!MyServlet.isEmpty(title)){
				room.setTitle(title);
			}
			
			if(!MyServlet.isEmpty(capacity)){
				room.setCapacity(Integer.parseInt(capacity));
			}
			
			ServletResult.sendResult(response, 
					manager.getRoomDao().update(room)==1 ?
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
