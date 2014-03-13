package servlet.booking;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;

/**
 * Servlet implementation class DeleteBooking
 */
@WebServlet("/Booking/Delete")
public class DeleteBooking extends MyServlet {
	private static final long serialVersionUID = 1L;

	public DeleteBooking() {
        super(ID);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			ServletResult.sendResult(response, manager.deleteBooking(Integer.parseInt(request.getParameter(ID))) ?
							ServletResult.SUCCESS
							: ServletResult.ERROR);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.BAD_NUMBER_FORMAT);
		}
	}

}
