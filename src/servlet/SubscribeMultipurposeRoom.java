package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage.DatabaseManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.MultipurposeRoom;

/**
 * Servlet implementation class AddMultipurposeRoom
 */
@WebServlet("/SubscribeMultipurposeRoom")
public class SubscribeMultipurposeRoom extends MyServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String NAME="name";
	private static final String LOCATION="location";
	
	
    public SubscribeMultipurposeRoom() {
        super(NAME, LOCATION);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name=request.getParameter(NAME);
		String location=request.getParameter(LOCATION);
		
		MultipurposeRoom room=new MultipurposeRoom();
		room.setName(name);
		room.setLocation(location);
		
		DatabaseManager manager=DatabaseManager.getManager();
		ObjectMapper oMap=new ObjectMapper();
		
		try {
			if(manager.getMultipurposeRoomDao().create(room)==1){
				response.getWriter().append(
						oMap.writeValueAsString(new Result(
								ServletResult.SUCCESS, room.getId())));
				
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
	
	private class Result extends ServletResult{
		@JsonProperty("id")
		public int id;
		
		public Result(int result, int id){
			super(result);
			
			this.id=id;
		}
	}

}
