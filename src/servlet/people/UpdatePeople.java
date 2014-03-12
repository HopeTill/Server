package servlet.people;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.People;

/**
 * Servlet implementation class UpdatePeople
 */
@WebServlet("/People/Update")
public class UpdatePeople extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public UpdatePeople() {
    	super(ID);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			People people = manager.getPeopleDao().queryForId(
					Integer.parseInt(request.getParameter(ID)));
			
			if(people==null){
				ServletResult.sendResult(response, ServletResult.NOT_FOUND);
				return;
			}
			
			String firstName=request.getParameter(People.FIRST_NAME);
			String lastName=request.getParameter(People.LAST_NAME);
			String email=request.getParameter(People.EMAIL);
			String phone=request.getParameter(People.PHONE);
			
			if(!MyServlet.isEmpty(firstName)){
				people.setFirstName(firstName);
			}
			
			if(!MyServlet.isEmpty(lastName)){
				people.setLastName(lastName);
			}
			
			if(!MyServlet.isEmpty(email)){
				people.setEmail(email);
			}
			
			if(!MyServlet.isEmpty(phone)){
				people.setPhone(phone);
			}
			
			ServletResult.sendResult(response, 
					manager.getPeopleDao().update(people)==1 ?
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
