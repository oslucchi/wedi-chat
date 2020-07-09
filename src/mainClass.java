
public class mainClass {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WSServer a = new WSServer();
		while(true)
		{
			Thread.sleep(1000);
			a.keepAlive();
		}
	}

}
