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

import com.j256.ormlite.stmt.QueryBuilder;

import entity.User;

/**
 * Servlet implementation class TryConnectionUser
 */
@WebServlet("/User/TryConnection")
public class TryConnectionUser extends MyServlet {
	private static final long serialVersionUID = 1L;

	
    public TryConnectionUser() {
    	super(User.LOGIN, User.PASSWORD);
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String login=request.getParameter(User.LOGIN);
		String password=request.getParameter(User.PASSWORD);
		
		DatabaseManager manager=DatabaseManager.getManager();
		QueryBuilder<User, Integer> query;
		try {
			query = manager.getUserDao().queryBuilder();
			query.where().eq("login", login)
					.and().eq("password", password);
			
			User user=query.queryForFirst();
			
			ServletResult.sendResult(response, new GetResult(ServletResult.SUCCESS, user!=null));
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
