package servlet.room;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.GetResult;
import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;

import com.j256.ormlite.stmt.QueryBuilder;

import entity.Room;

/**
 * Servlet implementation class GetAllRoom
 */
@WebServlet("/Room/GetAll")
public class GetAllRoom extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public GetAllRoom() {
    	
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		String masterRoom=request.getParameter(Room.MULTIPURPOSE_ROOM);
		
		try {
			if(!MyServlet.isEmpty(masterRoom)){
				QueryBuilder<Room, Integer> query=manager.getRoomDao().queryBuilder();
				query.where().eq("room_id", Integer.parseInt(masterRoom));
				
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, query.query()));
			}
			else{
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, manager.getRoomDao().queryForAll()));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
