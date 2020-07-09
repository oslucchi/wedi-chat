import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.websocket.Session;

import org.apache.log4j.Logger;

public class Users {
	final Logger log = Logger.getLogger(this.getClass());

	public class UserData {
		private Session session;
		private String nick;
		private String auth;
		
		public Session getSession() {
			return session;
		}
		public void setSession(Session session) {
			this.session = session;
		}
		public String getNick() {
			return nick;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public String getAuth() {
			return auth;
		}
		public void setAuth(String auth) {
			this.auth = auth;
		}
	}
    private static Users instance;
	private Map<String, UserData> userList = null;	
	
	private Users() 
    {
		userList = new HashMap<String, UserData>(); 
    } 
  
    // static method to create instance of Singleton class 
    public static Users getInstance() 
    { 
        if (instance == null) 
        	instance = new Users(); 
  
        return instance; 
    }
    
    public void addSession(Session session)
    {
    	UserData ud = new UserData();
    	ud.setSession(session);
    	ud.setNick("");
    	try 
    	{
			ud.setAuth(new String(UUID.randomUUID().toString().getBytes("UTF-8")));
		}
    	catch (UnsupportedEncodingException e) 
    	{
			// TODO Auto-generated catch block
			log.error(e);
		}
    	userList.put(session.getId(), ud);
    }

    public String newUserNic(String sessionId, String nick)
    {
    	userList.get(sessionId).setNick(nick);
    	return(userList.get(sessionId).getNick());
    }

    public void removeSession(String sessionId)
    {
    	userList.remove(sessionId);
    }
    
    public ArrayList<UserData> getUserList()
    {
    	return new ArrayList<UserData>(userList.values());
    }
}