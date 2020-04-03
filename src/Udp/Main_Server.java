package Udp;

import com.rollout.pcremoteclient.udptest.Data_Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main_Server {
	public static void main(String args[]){
		try{
			System.out.println("ello");
			DatagramSocket datagramSocket = new DatagramSocket(6969);
			Robot robot = new Robot();
			int max_buffer_size = 1024*59;
			Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			byte[] shake = new byte[6];
			DatagramPacket shake_datagramPacket = new DatagramPacket(shake,shake.length);
			datagramSocket.receive(shake_datagramPacket);
			InetAddress addr= shake_datagramPacket.getAddress();
			int port = shake_datagramPacket.getPort();
			while(true){
				BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
				byte[] image_byte_arr = byteArrayOutputStream.toByteArray();
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image_byte_arr);

				int count = 0;
				while(byteArrayInputStream.available()!=0){
					byte[] byte_buffer = new byte[max_buffer_size];
					if(byteArrayInputStream.available()>max_buffer_size){
						byteArrayInputStream.read(byte_buffer);
						count++;
					}else{
						byte_buffer = new byte[byteArrayInputStream.available()];
						byteArrayInputStream.read(byte_buffer);
						count = -1;
					}
					Data_Object data_object = new Data_Object("file","Aditya","yes",String.valueOf(count),null,null,byte_buffer);
					ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream1);
					objectOutputStream.writeObject(data_object);
					byte[] dp_buffer = byteArrayOutputStream1.toByteArray();
					DatagramPacket datagramPacket = new DatagramPacket(dp_buffer,dp_buffer.length,addr,port);
					datagramSocket.send(datagramPacket);

					//receiving acknowledgement
					byte[] ack_data = new byte[1024];
					DatagramPacket datagramPacket_ack = new DatagramPacket(ack_data,ack_data.length);
					datagramSocket.receive(datagramPacket_ack);
					System.out.println(datagramPacket_ack.getLength());
					Data_Object ack_object = getdata_Object(datagramPacket_ack);
					if(data_object.getType().equals("ack")){
					}else{
						System.out.println("Mismatch Occured !"+data_object.getType());
					}
				}


			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static Data_Object getdata_Object(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
		byte[] data = datagramPacket.getData();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		Data_Object data_Object = (Data_Object) objectInputStream.readObject();
		return data_Object;
	}
}
