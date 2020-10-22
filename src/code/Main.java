package code;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Fregadero fregadero = new Fregadero();
		Thread fregadorThread = new Thread(new Fregador(fregadero), "Aaron");
		Thread secadorThread = new Thread(new Secador(fregadero), "Antonio");
		Thread organizadorThread = new Thread(new Organizador(fregadero), "Ezequiel");
		
		fregadorThread.start();
		secadorThread.start();
		organizadorThread.start();
		
		TimeUnit.SECONDS.sleep(60);
		
		fregadorThread.interrupt();
		secadorThread.interrupt();
		organizadorThread.interrupt();
		
		System.out.printf("\n%s: Estoy molío\n", fregadorThread.getName());
		System.out.printf("%s: Mucho plato\n", secadorThread.getName());
		System.out.printf("%s: Donde está el cumpleañero?\n\n", organizadorThread.getName());
		
		fregadorThread.join();
		secadorThread.join();
		organizadorThread.join();
		
		System.out.println("Feliz cumpleaños!!!!!!");
	}

}
