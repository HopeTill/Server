package servlet.booking;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.GetResult;
import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;

import com.j256.ormlite.stmt.QueryBuilder;

import entity.Booking;

/**
 * Servlet implementation class GetAllBooking
 */
@WebServlet("/Booking/GetAll")
public class GetAllBooking extends MyServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetAllBooking() {
    	
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		String begin=request.getParameter(Booking.BEGIN);
		String end=request.getParameter(Booking.END);
		
		try {
			if(!MyServlet.isEmpty(begin) && !MyServlet.isEmpty(end)){
				SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				QueryBuilder<Booking, Integer> query=manager.getBookingDao().queryBuilder();
				
				query.where().ge("begin", df.parse(begin))
						.and().lt("end", df.parse(end));
				
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, query.query()));
			}
			else{
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, manager.getBookingDao().queryForAll()));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.BAD_DATE_FORMAT);
		}		
	}

}
