package servlet.equipment;

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

import entity.Equipment;

/**
 * Servlet implementation class GetAllEquipment
 */
@WebServlet("/Equipment/GetAll")
public class GetAllEquipment extends MyServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetAllEquipment() {
    	
    }

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatabaseManager manager=DatabaseManager.getManager();
		
		String masterRoom=request.getParameter(Equipment.MULTIPURPOSE_ROOM);
		
		try {
			if(!MyServlet.isEmpty(masterRoom)){
				QueryBuilder<Equipment, Integer> query=manager.getEquipmentDao().queryBuilder();
				query.where().eq("room_id", Integer.parseInt(masterRoom));
				
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, query.query()));
			}
			else{
				ServletResult.sendResult(response, new GetResult(
						ServletResult.SUCCESS, manager.getEquipmentDao().queryForAll()));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ServletResult.sendResult(response, ServletResult.ERROR);
		}
	}

}
