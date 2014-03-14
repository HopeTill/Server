package servlet.people;

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

import entity.People;

/**
 * Servlet implementation class GetAllPeople
 */
@WebServlet("/People/GetAll")
public class GetAllPeople extends MyServlet {
	private static final long serialVersionUID = 1L;

	
	public GetAllPeople() {
    	
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		String fistName=request.getParameter(People.FIRST_NAME);
		
		try {
			if(!MyServlet.isEmpty(fistName)){
				QueryBuilder<People, Integer> query=manager.getPeopleDao().queryBuilder();
				
				query.where().like("firstName", fistName);
				
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, query.query()));	
			}
			else{
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, manager.getPeopleDao().queryForAll()));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
