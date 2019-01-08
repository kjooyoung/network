package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String line = "www.naver.com";
		
		try {
			InetAddress[] inetAddress = InetAddress.getAllByName(line);
			for(InetAddress ad : inetAddress) {
				System.out.println(ad);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
