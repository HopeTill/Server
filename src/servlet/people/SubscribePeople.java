package servlet.people;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.CreateResult;
import servlet.MyServlet;
import servlet.ServletResult;
import storage.DatabaseManager;
import entity.People;

/**
 * Servlet implementation class SubscribePeople
 */
@WebServlet("/People/Subscribe")
public class SubscribePeople extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscribePeople() {
        super(People.FIRST_NAME, People.LAST_NAME);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String firstName=request.getParameter(People.FIRST_NAME);
		String lastName=request.getParameter(People.LAST_NAME);
		String email=request.getParameter(People.EMAIL);
		String phone=request.getParameter(People.PHONE);
		
		People people=new People();
		people.setFirstName(firstName);
		people.setLastName(lastName);
		people.setEmail(email);
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
