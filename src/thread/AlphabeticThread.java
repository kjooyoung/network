package thread;

public class AlphabeticThread extends Thread {

	@Override
	public void run() {
		Thread digitThread = new DigitThread();

		digitThread.start();
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
