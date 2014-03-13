package servlet.multipuposeroom;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.GetResult;
import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;

/**
 * Servlet implementation class GetAllMultipurposeRoom
 */
@WebServlet("/MultipurposeRoom/GetAll")
public class GetAllMultipurposeRoom extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public GetAllMultipurposeRoom() {
    	
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			ServletResult.sendResult(response, new GetResult(
					ServletResult.SUCCESS, manager.getMultipurposeRoomDao().queryForAll()));
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
		
	}

}
