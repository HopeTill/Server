package servlet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateResult extends ServletResult {
	
	@JsonProperty("id")
	public int id;
	
	public CreateResult(int result, int id){
		super(result);
		
		this.id=id;
	}
}
