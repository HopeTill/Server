package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MyServlet extends HttpServlet {
	private static final long serialVersionUID = -570326162982700170L;
	private String[] requiredParameter;
	
	public static final String ID="id";
	
	public MyServlet(String... requiredParameter){
		if(requiredParameter==null) throw new IllegalArgumentException("Can't be null");
		
		this.requiredParameter=requiredParameter;
	}

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkRequest(request, response);
	}
	
	private void checkRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		boolean well=true;
		
		for(String key : requiredParameter){
			well &= !MyServlet.isEmpty(request.getParameter(key));
		}
		
		if(!well){
			ServletResult.sendResult(response, ServletResult.MISSING_PARAMETER);
		}
		else processRequest(request, response);
	}
	
	abstract protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public static boolean isEmpty(String value){
		return value==null || value.isEmpty();
	}
}
