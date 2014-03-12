package servlet.room;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.CreateResult;
import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.MultipurposeRoom;
import entity.Room;

/**
 * Servlet implementation class SubscribeRoom
 */
@WebServlet("/Room/Subscribe")
public class SubscribeRoom extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public SubscribeRoom() {
        super(Room.MULTIPURPOSE_ROOM, Room.CAPACITY);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String title=request.getParameter(Room.TITLE);
			int masterRoom=Integer.parseInt(request.getParameter(Room.MULTIPURPOSE_ROOM));
			int capacity=Integer.parseInt(request.getParameter(Room.CAPACITY));
			
			DatabaseManager manager=DatabaseManager.getManager();
			
			MultipurposeRoom multipurposeRoom=manager.getMultipurposeRoomDao().queryForId(masterRoom);
		
			if(multipurposeRoom==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			
			Room room=new Room();
			room.setTitle(title);
			room.setRoom(multipurposeRoom);
			room.setCapacity(capacity);
			
			if(manager.getRoomDao().create(room)==1){
				ServletResult.sendResult(response, new CreateResult(
						ServletResult.SUCCESS, room.getId()));
				
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
