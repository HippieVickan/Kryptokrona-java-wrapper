package daemon_api;

import javax.json.JsonObject;

public class BlockTemplate {
	public String blob;
	public int difficulty;
	public int height;
	public int reservedOffset;
	public JsonObject generator;
	
	
	public BlockTemplate(String blob,int diff,int height,int res,String stat) {
		this.blob=blob;
		this.difficulty=diff;
		this.height=height;
		this.reservedOffset=res;

	}
	
	public BlockTemplate(JsonObject input) {
		this.generator=input;
		parse(input);
	}
	
	public void parse(JsonObject input) {
		blob=input.getString("blocktemplate_blob");
		difficulty=input.getInt("difficulty");
		height=input.getInt("height");
		reservedOffset=input.getInt("reserved_offset");
	}
}
