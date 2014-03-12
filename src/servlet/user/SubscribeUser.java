package servlet.user;

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
import entity.User;

/**
 * Servlet implementation class SubscribeUser
 */
@WebServlet("/User/Subscribe")
public class SubscribeUser extends MyServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscribeUser() {
        super(User.LOGIN, User.PASSWORD);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String login=request.getParameter(User.LOGIN);
		String password=request.getParameter(User.PASSWORD);
		String firstName=request.getParameter(User.FIRST_NAME);
		String lastName=request.getParameter(User.LAST_NAME);
		
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
