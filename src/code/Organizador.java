package code;

public class Organizador implements Runnable{

private final Fregadero fregadero;
	
	public Organizador(Fregadero fregadero) {
		this.fregadero = fregadero;
	}
	
	@Override
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			try{
				fregadero.guardarPlato();
			}
			catch(InterruptedException e){
				return;
			}
		}
		
	}
	

}
