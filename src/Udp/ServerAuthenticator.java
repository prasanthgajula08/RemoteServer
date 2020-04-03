package Udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerAuthenticator {
	Main_Server main_server;
	public void startAuthenticator(String password){
		try {
			DatagramSocket datagramSocket = new DatagramSocket(6970);
			while(true){
				byte[] data = new byte[100];
				DatagramPacket datagramPacket = new DatagramPacket(data,data.length);
				datagramSocket.receive(datagramPacket);
				String s = new String(data);
				if(s.equals(password)){
					main_server= new Main_Server();
					main_server.startServer(datagramPacket);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Main_Server getStartedServer(){
		return main_server;
	}
}
