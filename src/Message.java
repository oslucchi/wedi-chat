import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
	static final int MSG_LOGON = 1;
	static final int MSG_LOGON_CONFIRM = 2;
	static final int MSG_CHG_NICK = 3;
	static final int MSG_PRIVATE = 4;
	static final int MSG_BROADCAST = 5;
	
	int msgType;
	String auth;
	String from;
	String to;
	String text;
	
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String toJsonString()
	{
	    ObjectMapper mapper = new ObjectMapper();
	    try 
	    {
	      String json = mapper.writeValueAsString(this);
	      System.out.println("ResultingJSONstring = " + json);
	      return json;
	    }
	    catch (JsonProcessingException e) 
	    {
	       return "";
	    }
	}
	
	public static Message fromJsonString(String json)
	{
	    ObjectMapper mapper = new ObjectMapper();
	    Message m;
	    try 
	    {
	      m = mapper.readValue(json, Message.class);
	      System.out.println("ResultingObject: ");
	      System.out.println(m);
	      return m;
	    }
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
