package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("> ");
				String line = sc.nextLine();
				if (line.equals("exit"))
					break;
				InetAddress[] inetAddress = InetAddress.getAllByName(line);
				for (InetAddress ad : inetAddress) {
					System.out.println(ad.getHostName() + " : " + ad.getHostAddress());
				}

			} catch (UnknownHostException e) {
				System.out.println("잘못입력하셨습니다.");
			}
		}
	}
}
