package servlet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetResult extends ServletResult {
	@JsonProperty("value")
	public Object value;
	
	public GetResult(int result, Object value){
		super(result);
		
		this.value=value;
	}
}
