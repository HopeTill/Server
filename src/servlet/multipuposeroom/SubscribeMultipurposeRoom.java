package servlet.multipuposeroom;

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

/**
 * Servlet implementation class AddMultipurposeRoom
 */
@WebServlet("/MultipurposeRoom/Subscribe")
public class SubscribeMultipurposeRoom extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	
    public SubscribeMultipurposeRoom() {
        super(MultipurposeRoom.NAME, MultipurposeRoom.LOCATION);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name=request.getParameter(MultipurposeRoom.NAME);
		String location=request.getParameter(MultipurposeRoom.LOCATION);
		
		MultipurposeRoom room=new MultipurposeRoom();
		room.setName(name);
		room.setLocation(location);
		
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			if(manager.getMultipurposeRoomDao().create(room)==1){
				ServletResult.sendResult(response, new CreateResult(
						ServletResult.SUCCESS, room.getId()));
				
				response.setStatus(HttpServletResponse.SC_CREATED);
			}
			else{
				ServletResult.sendResult(response, ServletResult.ERROR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
