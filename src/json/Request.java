package json;

import javax.json.JsonObject;

public class Request {
	public JsonObject RESULT;
	public int RESPONSE_CODE;
	
	public Request(JsonObject res,int respc) {
		RESULT=res;
		RESPONSE_CODE=respc;
	}
	public boolean isOk() {
		return RESPONSE_CODE==200;
	}
}
