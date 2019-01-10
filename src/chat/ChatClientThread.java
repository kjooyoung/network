package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class ChatClientThread extends Thread{
	private BufferedReader br;
	
	public ChatClientThread(BufferedReader br) {
		this.br = br;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String data = br.readLine();
				if(data == null) {
					System.out.println("서버 종료");
				}
				System.out.println(data);
			}
		} catch (SocketException e) {
			System.out.println("서버 종료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
