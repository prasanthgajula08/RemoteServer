package Udp;

import com.rollout.pcremoteclient.udptest.Data_Object;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Timer;

public class Main_Server {
	static int fps_count=0;
	DatagramSocket datagramSocket;
	public  void startServer(DatagramPacket shake_datagramPacket){
		try{
			System.out.println("ello");
			datagramSocket = new DatagramSocket(6969);
			datagramSocket.setSoTimeout(1000);
			Robot robot = new Robot();
			int max_buffer_size = 1024*59;
			Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			InetAddress addr= shake_datagramPacket.getAddress();
			int port = shake_datagramPacket.getPort();

			while(true){
				BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
				byte[] image_byte_arr = byteArrayOutputStream.toByteArray();
//				byte[] image_byte_arr = getCompressedImage(bufferedImage);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image_byte_arr);
				System.out.println(byteArrayInputStream.available()/1000);
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
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public Data_Object getdata_Object(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
		byte[] data = datagramPacket.getData();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		Data_Object data_Object = (Data_Object) objectInputStream.readObject();
		return data_Object;
	}
	public void stopServer(){
		if(datagramSocket!=null){
			datagramSocket.close();
		}
	}
	public byte[] getCompressedImage(BufferedImage image) throws IOException {
		ByteArrayOutputStream compressed = new ByteArrayOutputStream();
		ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);
		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

		ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
		jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		//30% of original
		jpgWriteParam.setCompressionQuality(0.3f);
		jpgWriter.setOutput(outputStream);
		jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);
		jpgWriter.dispose();
		byte[] jpegData = compressed.toByteArray();
		return jpegData;
	}
}
