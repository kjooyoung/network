package thread;

public class MultiThreadEx03 {
	public static void main(String[] args) {
		Thread thread1 = new AlphabeticThread();
		Thread thread2 = new DigitThread();
		Thread thread3 = new Thread(new UppercaseAlphabeticRunnableImpl());
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
