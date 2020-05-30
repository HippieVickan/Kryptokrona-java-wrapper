package shared;

import javax.json.JsonObject;

public class Node {
	public String HOST;
	public int PORT;
	public int FEE;
	public String ADDRESS;
	
	public Node(JsonObject input) {
		HOST=input.getString("daemonHost");
		PORT=input.getInt("daemonPort");
		FEE=input.getInt("nodeFee");
		ADDRESS=input.getString("nodeAddress");
	}
	
}
