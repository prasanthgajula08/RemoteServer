package Udp;

import com.rollout.pcremoteclient.udptest.Data_Object;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerAuthenticator {
	Main_Server main_server;
	DatagramSocket datagramSocket;
	public void startAuthenticator(String password){
		try {
			datagramSocket = new DatagramSocket(6970);
			while(true){
				byte[] data = new byte[64*1024];
				DatagramPacket datagramPacket = new DatagramPacket(data,data.length);
				datagramSocket.receive(datagramPacket);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				Data_Object data_object =(Data_Object) objectInputStream.readObject();
				String received_data = new String(data_object.getData());
				System.out.println(new String(data_object.getData()));

				if(received_data.equals(password)){
					Data_Object reply_object = new Data_Object(null,null,null,null,null,null,"Success".getBytes());
					ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
					objectOutputStream.writeObject(reply_object);
					byte[] reply = byteArrayOutputStream.toByteArray();
					DatagramPacket reply_packet = new DatagramPacket(reply,reply.length,datagramPacket.getAddress(),datagramPacket.getPort());
					datagramSocket.send(reply_packet);
					main_server= new Main_Server();
					main_server.startServer(datagramPacket);
				}
			}
		} catch (SocketException e) {
			System.out.println("Server Authenticator closed on clicking 'STOP'");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Main_Server getStartedServer(){
		return main_server;
	}
	public void stopAuthenticator(){
		if(datagramSocket!=null){
			datagramSocket.close();
		}
	}
}
