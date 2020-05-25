package json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.json.Json;
import javax.json.JsonObject;

public class API {
	public HttpURLConnection connection;
	public String IP;
	public API(String ip) {
		this.IP=ip;
	}
	
	private void connect(String ip)  {
		try {
		connection=(HttpURLConnection) new URL("http://"+ip+":11898/json_rpc").openConnection();
		}
		catch(MalformedURLException e) {
			System.out.println("Bad URL, did you enter your IP correctly?");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void walletConnect(String ip,String dir) {
		
		
		try {
			connection=(HttpURLConnection) new URL("http://"+ip+":8070"+dir).openConnection();
			}
			catch(MalformedURLException e) {
				System.out.println("Bad URL, did you enter your IP correctly?");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public JsonObject request(JsonObject input) throws IOException, InterruptedException {
		connect(IP);
		
		Charset QUERY_CHARSET = Charset.forName("ISO8859-1");
		
		
		connection.setDoOutput(true);
		connection.getOutputStream().write(input.toString().getBytes(QUERY_CHARSET));
		
	


		try {
			connection.connect();
			Thread.sleep(100);

			BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String read="";
			StringBuilder sb=new StringBuilder();
			while((read=reader.readLine())!=null) {
				sb.append(read);
			}
			connection.disconnect();

			return Json.createReader(new StringReader(sb.toString())).readObject();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	private Request requestWallet(String path,JsonObject params,JsonObject headers,String method) throws IOException, InterruptedException {
		
		
		
		
		walletConnect(IP,path);
		System.out.println(IP+path);
		System.out.println(params);
		//Charset QUERY_CHARSET = Charset.forName("ISO8859-1");
		
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		
		for(String i:headers.keySet()) connection.setRequestProperty(i, headers.getString(i));
		

		connection.getOutputStream().write(params.toString().getBytes());
		
		

		try {
			connection.connect();
			Thread.sleep(100);
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String read="";
			StringBuilder sb=new StringBuilder();
			while((read=reader.readLine())!=null) {
				sb.append(read);
			}
			connection.disconnect();
			if(sb.length()<1) return  new Request(Json.createReader(new StringReader("{}")).readObject(),connection.getResponseCode());
			return new Request(Json.createReader(new StringReader(sb.toString())).readObject(),connection.getResponseCode());
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public JsonObject noParameterRequest(String method) throws IOException, InterruptedException {
		return request(Json.createReader(new StringReader("{\"jsonrpc\":\"2.0\", \"method\":\"putinmethodhere\", \"params\":{}}".replaceAll("putinmethodhere", method))).readObject());
	}
	public JsonObject parameterRequest(String method,String params) throws IOException, InterruptedException {
		return request(Json.createReader(new StringReader("{\"jsonrpc\":\"2.0\", \"method\":\"putinmethodhere\", \"params\":putparamshere}".replaceAll("putinmethodhere", method).replaceAll("putparamshere", params))).readObject());
	}
	
	public Request walletRequest(String method,JsonObject params,JsonObject headers,String httpMethod) throws IOException, InterruptedException {
		return requestWallet(method,params,headers,httpMethod);
	}
	
	
}
