package servlet.user;

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
 * Servlet implementation class GetUser
 */
@WebServlet("/User/Get")
public class GetUser extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public GetUser() {
    	super(ID);
    }
    
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			ServletResult.sendResult(response,
					new GetResult(ServletResult.SUCCESS, manager.getUserDao().queryForId(
							Integer.parseInt(request.getParameter(ID)))));
		} catch (NumberFormatException e) {
			ServletResult.sendResult(response, ServletResult.BAD_NUMBER_FORMAT);
			e.printStackTrace();
		} catch (SQLException e) {
			ServletResult.sendResult(response, ServletResult.ERROR);
			e.printStackTrace();
		}
	}

}
