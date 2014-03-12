package servlet.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.User;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/User/Update")
public class UpdateUser extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public UpdateUser() {
    	super(ID);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			User user = manager.getUserDao().queryForId(
					Integer.parseInt(request.getParameter(ID)));
			
			if(user==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			
			String password=request.getParameter(User.PASSWORD);
			String firstName=request.getParameter(User.FIRST_NAME);
			String lastName=request.getParameter(User.LAST_NAME);
			
			if(!MyServlet.isEmpty(password)){
				user.setPassword(password);
			}
			
			if(!MyServlet.isEmpty(firstName)){
				user.setFirstName(firstName);
			}
			
			if(!MyServlet.isEmpty(lastName)){
				user.setLastName(lastName);
			}
			
			
			ServletResult.sendResult(response, 
					manager.getUserDao().update(user)==1 ?
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
