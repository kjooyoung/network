package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Calendar;

public class UDPTimeServer {
	public static final int PORT = 5000;
	public static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			//1. 소켓 생성
			socket = new DatagramSocket(PORT);
			while(true) {
				//2. 데이터 수신
				DatagramPacket receivePacket = 
						new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0, length,"UTF-8");
				
				Calendar cal = Calendar.getInstance();
				String date = cal.get(Calendar.YEAR)+"-"
							+ (cal.get(Calendar.MONTH)+1)+"-"
							+ cal.get(Calendar.DAY_OF_MONTH)+" "
							+ cal.get(Calendar.HOUR_OF_DAY)+":"
							+ cal.get(Calendar.MINUTE)+":"
							+ cal.get(Calendar.SECOND);
				byte[] sendData = date.getBytes("UTF-8");
				
				DatagramPacket sendPacket =
						new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
				socket.send(sendPacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null & socket.isClosed()) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
