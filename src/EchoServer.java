import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
 
import javax.swing.Timer;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/echo") 
public class EchoServer {
   
	private int counter = 0;
	
    @OnOpen
    public void onOpen(Session session)
    {
        System.out.println(session.getId() + " has opened a connection"); 
        try 
        {
            session.getBasicRemote().sendText("Connection Established");
            Timer timer = new Timer(3000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					onMessage(null,session);
				}
			});
            timer.setRepeats(true); 
            timer.start();
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
 
    @OnMessage
    public void onMessage(String message, Session session)
    {
        try 
        {
        	counter++;
        	int mod = counter % 3;
        	Message objMessage = new Message();
        	objMessage.setCount(1);
        	objMessage.setMessage("Message " + counter);
        	if(mod == 0)
        	{
        		objMessage.setNotificationType("Assigned tasks");
        		//retValue = "{notificationType:'Assigned tasks',count:1,message:'Message '" + counter + "}";
        	}
        	else if(mod == 1)
        	{
        		objMessage.setNotificationType("Reminders");
        		//retValue = "{notificationType:'Reminders',count:1,message:'Message '" + counter + "}";
        	}
        	else 
        	{
        		objMessage.setNotificationType("Notifications");
        		//retValue = "{notificationType:'Notifications',count:1,message:'Message '" + counter + "}";
        	}
            //session.getBasicRemote().sendText(retValue);
        	System.out.println("Message sent from " + session.getId() + ": " + objMessage.toString());
        	session.getBasicRemote().sendText(objMessage.toString());
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
 
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
    }
}
