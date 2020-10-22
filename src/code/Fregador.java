package code;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Fregador implements Runnable{
	
	private final Fregadero fregadero;
	private int nPlato = 0;
	
	public Fregador(Fregadero fregadero) {
		this.fregadero = fregadero;
	}

	@Override
	public void run() {
		Plato plato;
		while(!Thread.currentThread().isInterrupted()) {
			try {
				plato = cogerPlato();
			} catch (InterruptedException e) {
				return;
			}
			try {
				fregadero.añadirPlatoLimpio(plato);
				nPlato++;
				TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4,9));
			} catch (InterruptedException e) {
				return;
			}
		}
		
	}
	
	private Plato cogerPlato() throws InterruptedException{
		return new Plato(nPlato);
	}

}
