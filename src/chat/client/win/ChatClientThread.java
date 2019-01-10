package chat.client.win;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class ChatClientThread extends Thread{
	private BufferedReader br;
	private ChatWindow cw;
	
	public ChatClientThread(BufferedReader br, ChatWindow cw) {
		this.br = br;
		this.cw = cw;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String data = br.readLine();
				if(data == null) {
					System.out.println("서버 종료");
				}
				cw.getTextArea().append("\n"+data);
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
