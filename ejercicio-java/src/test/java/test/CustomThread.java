package test;

import ar.model.Call;
import ar.service.impl.Dispatcher;

public class CustomThread extends Thread{

	Call call;
	Dispatcher dispatcher;
	private Integer runs = 0;

	
	public CustomThread(Call call,Dispatcher dispatcher){
		this.call = call;
		this.dispatcher = dispatcher;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run(){
		while (runs < 1000){
			try {
				dispatcher.dispatchCall(call);
				call.getSemaphore().acquire();
				this.sleep(call.getDuration() * 1000);
				dispatcher.endCall(call);
				runs++;
				StressTest.END_CONDITION.release();
			} catch (InterruptedException e) {
				throw new RuntimeException(" Error while sleeping thread id: " + this.getId());
			}
		}
		System.out.println("Thread with ID: " + this.getId() + " has finished ");
	}
	
	
	
}
