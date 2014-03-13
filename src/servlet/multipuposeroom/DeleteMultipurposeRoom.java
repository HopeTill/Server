package servlet.multipuposeroom;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;

/**
 * Servlet implementation class DeleteMultipurposeRoom
 */
@WebServlet("/MultipurposeRoom/Delete")
public class DeleteMultipurposeRoom extends MyServlet {
	private static final long serialVersionUID = 1L;
	
    public DeleteMultipurposeRoom() {
        super(ID);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			ServletResult.sendResult(response, manager.getMultipurposeRoomDao()
					.deleteById(Integer.parseInt(request.getParameter(ID)))==1 ?
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
