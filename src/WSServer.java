import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint("/chat")
public class WSServer {
	  final Logger log = Logger.getLogger(this.getClass());
	  Users users = Users.getInstance();

	  @OnOpen
	  public void onOpen(Session session) 
	  {
		  System.out.println("open");
		  users.addSession(session);
	  }

	  @OnClose
	  public void onClose(CloseReason reason, Session session) 
	  {
		  System.out.println("close");
		  users.removeSession(session.getId());
	  }

	  @OnMessage
	  public void onMessage(String jsonIn, Session session) throws IOException 
	  {
		  System.out.println("message");
		  Message incoming = Message.fromJsonString(jsonIn);
		  switch(incoming.getMsgType())
		  {
		  case Message.MSG_LOGON:
			  Message reply = new Message();
			  String nick = incoming.getFrom();
			  String auth = users.newUserNic(session.getId(), incoming.getText());
			  reply.setText(auth);
			  reply.setFrom("central");
			  reply.setTo(nick);
			  reply.setMsgType(Message.MSG_LOGON_CONFIRM);
			  session.getBasicRemote().sendText(reply.toJsonString());
			  break;

		  case Message.MSG_PRIVATE:
			  for(Users.UserData item : users.getUserList())
			  {
				  if (item.getNick().compareTo(incoming.getTo()) == 0)
				  {
					  item.getSession().getBasicRemote().sendText(jsonIn);
					  break;
				  }
			  }
			  break;
			  
		  case Message.MSG_BROADCAST:
			  for(Users.UserData item : users.getUserList())
			  {
				  if (item.getAuth().compareTo(incoming.getAuth()) != 0)
				  {
					  item.getSession().getBasicRemote().sendText(jsonIn);
				  }
			  }
			  break;
		  }
	  }

	  @OnError
	  public void onError(Session session, Throwable t) {
		  log.error(String.format("Error in WebSocket session %s%n", session == null ? "null" : session.getId()), t);
	  }
	  
	  public void keepAlive()
	  {
		  System.out.print(".");
	  }
}
