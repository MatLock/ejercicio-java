package test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.model.Call;
import ar.model.Director;
import ar.model.Operator;
import ar.model.Supervisor;
import ar.service.impl.Dispatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:configuration.xml"})
public class StressTest{

	public static Semaphore END_CONDITION = new Semaphore(0);
	@Autowired
	Dispatcher dispatcher;	
	
	@Before
	public void setUp(){
		dispatcher.setEmployees(Arrays.asList(new Director(1),
				new Supervisor(2),new Supervisor(3),new Supervisor(4),new Supervisor(5),
				new Operator(6),new Operator(7),new Operator(8),new Operator(9),new Operator(10)));
		dispatcher.setOnHoldCalls(new ArrayDeque<Call>());
	}
	
	/**
	 * each Thread runs 1000 times before ending the test.
	 * If you want a minor quantity of runs, please change the 15000 value for a minor value and go to CustomThread.java and
	 * replace the condition in the while closure
	 * 
	 * For Example:
	 * Permissions / threads = total of runs per thread
	 * 15000 / 15 = 1000 -> each thread runs 1000 times before ending the test (you will see this value in the while closure).
	 */
	@Test
	public void stressTest(){
		for(int x =0;x<15;x++){
			new CustomThread(new Call(x), dispatcher).start();
		}
		END_CONDITION.acquireUninterruptibly(15000);
		System.out.println("Tests ended flawlessly... Yay!!");
	}
	
}
