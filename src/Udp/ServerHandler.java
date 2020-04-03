package Udp;

public class ServerHandler extends Thread {
	ServerAuthenticator serverAuthenticator;
	String password;
	public ServerHandler(String password){
		this.password = password;
	}
	@Override
	public void run(){
		serverAuthenticator = new ServerAuthenticator();
		serverAuthenticator.startAuthenticator(password);
	}
	public void end(){
		try{
			serverAuthenticator.getStartedServer().stopServer();
		}catch (NullPointerException e){
			System.out.println("No Connection Established");
		}
		serverAuthenticator.stopAuthenticator();
	}
}
