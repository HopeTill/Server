package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage.DatabaseManager;
import entity.User;

/**
 * Servlet implementation class SubscribeUser
 */
@WebServlet("/SubscribeUser")
public class SubscribeUser extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIN="login";
	private static final String PASSWORD="password";
	private static final String FIRST_NAME="first_name";
	private static final String LAST_NAME="last_name";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscribeUser() {
        super(LOGIN, PASSWORD);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String login=request.getParameter(LOGIN);
		String password=request.getParameter(PASSWORD);
		String firstName=request.getParameter(FIRST_NAME);
		String lastName=request.getParameter(LAST_NAME);
		
		User user=new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		DatabaseManager manager=DatabaseManager.getManager();
		
		try {
			if(manager.getUserDao().create(user)==1){
				ServletResult.sendResult(response, new CreateResult(
						ServletResult.SUCCESS, user.getId()));
				
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
