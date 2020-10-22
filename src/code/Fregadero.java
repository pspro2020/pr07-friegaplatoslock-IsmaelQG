package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Fregadero {
	
	private final ReentrantLock lock = new ReentrantLock(true);
	private final Condition platosLimpiosEmpty = lock.newCondition();
    private final Condition platosSecosEmpty = lock.newCondition();
    
	ArrayList<Plato> platosLimpios = new ArrayList<>();
	ArrayList<Plato> platosSecos = new ArrayList<>();
	ArrayList<Plato> alacena = new ArrayList<>();
	
	DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public void añadirPlatoLimpio(Plato plato) throws InterruptedException {
		lock.lock();
		
			platosLimpios.add(plato);
			System.out.printf("%s Lavando plato nº %d\n",LocalTime.now().format(format), plato.getId());
			platosLimpiosEmpty.signal();
		lock.unlock();
	}
	
	public void añadirPlatoSeco() throws InterruptedException {
		lock.lock();
		
			while(platosLimpios.isEmpty()) {
				System.out.printf("%s Esperando platos empapados\n", LocalTime.now().format(format));
				platosLimpiosEmpty.await();
			}
			platosSecos.add(platosLimpios.get(0));
			System.out.printf("%s Secando plato nº %d\n",LocalTime.now().format(format), platosSecos.get(0).getId());
			platosLimpios.remove(0);
			platosSecosEmpty.signal();
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,4));
		lock.unlock();
	}
	
	public void guardarPlato() throws InterruptedException {
		lock.lock();
		
			while(platosSecos.isEmpty()) {
				System.out.printf("%s Esperando platos secos\n", LocalTime.now().format(format));
				platosSecosEmpty.await();
			}
			alacena.add(platosSecos.get(0));
			System.out.printf("%s Guardando plato nº %d\n",LocalTime.now().format(format), platosSecos.get(0).getId());
			platosSecos.remove(0);
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,3));
		lock.unlock();
	}

}
