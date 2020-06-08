package json;

import java.io.InputStream;

import javax.json.JsonObject;

public class Request {
	public JsonObject RESULT;
	public int RESPONSE_CODE;
	public InputStream ERROR_STREAM;
	public Request(JsonObject res,int respc,InputStream es) {
		RESULT=res;
		RESPONSE_CODE=respc;
		ERROR_STREAM=es;
	}
	public boolean isOk() {
		return RESPONSE_CODE==200;
	}
}
