package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage.DatabaseManager;
import entity.People;

/**
 * Servlet implementation class SubscribePeople
 */
@WebServlet("/SubscribePeople")
public class SubscribePeople extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FIRST_NAME="first_name";
	private static final String LAST_NAME="last_name";
	private static final String EMAIL="email";
	private static final String PHONE="phone";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscribePeople() {
        super(FIRST_NAME, LAST_NAME);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String firstName=request.getParameter(FIRST_NAME);
		String lastName=request.getParameter(LAST_NAME);
		String email=request.getParameter(EMAIL);
		String phone=request.getParameter(PHONE);
		
		People people=new People();
		people.setFirstName(firstName);
		people.setLastName(lastName);
		people.setMail(email);
		people.setPhone(phone);
		
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			if(manager.getPeopleDao().create(people)==1){
				ServletResult.sendResult(response, new CreateResult(
						ServletResult.SUCCESS, people.getId()));
				
				response.setStatus(HttpServletResponse.SC_CREATED);
				
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
