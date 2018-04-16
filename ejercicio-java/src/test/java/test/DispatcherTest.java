package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Arrays;

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
import ar.service.DispatcherService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:configuration.xml"})
public class DispatcherTest{

	@Autowired
	DispatcherService dispatcher;	
	Call call0,call1,call2,call3,call4,call5,call6,call7,call8,call9,call10;	
	
	@Before
	public void setUp (){
		dispatcher.setEmployees(Arrays.asList(new Director(1),
				new Supervisor(2),new Supervisor(3),new Supervisor(4),new Supervisor(5),
				new Operator(6),new Operator(7),new Operator(8),new Operator(9),new Operator(10)));
		dispatcher.setOnHoldCalls(new ArrayDeque<Call>());
		call0 = new Call(0);
		call1 = new Call(1);
		call2 = new Call(2);
		call3 = new Call(3);
		call4 = new Call(4);
		call5 = new Call(5);
		call6 = new Call(6);
		call7 = new Call(7);
		call8 = new Call(8);
		call9 = new Call(9);
		call10 = new Call(10);
	}	
	
	
	
	@Test
	public void endCall(){
		dispatcher.dispatchCall(call0);
		assertEquals(dispatcher.idleOperators().intValue(),4);
		assertEquals(dispatcher.busyOperators().intValue(),1);
		dispatcher.endCall(call0);
		assertEquals(dispatcher.idleOperators().intValue(),5);
	}
	
	
	@Test
	public void callReceivedByOperator(){
		dispatcher.dispatchCall(call0);
		assertEquals(dispatcher.idleOperators().intValue(),4);
		assertEquals(dispatcher.busyOperators().intValue(),1);
		assertEquals(dispatcher.busySupervisors().intValue(),0);
		assertEquals(dispatcher.busyDirectors().intValue(),0);
	}
	
	/**
	 * All operators are busy, so the call must be taken by a Supervisor
	 */
	@Test
	public void callReceivedBySupervisor(){
		dispatcher.dispatchCall(call0);
		dispatcher.dispatchCall(call1);
		dispatcher.dispatchCall(call2);
		dispatcher.dispatchCall(call3);
		dispatcher.dispatchCall(call4);
		dispatcher.dispatchCall(call5);
		assertEquals(dispatcher.idleSupervisors().intValue(),3);
		assertEquals(dispatcher.busyOperators().intValue(),5);
		assertEquals(dispatcher.busySupervisors().intValue(),1);
		assertEquals(dispatcher.busyDirectors().intValue(),0);
	}
	
	/**
	 * All operators and supervisors are busy, so the call must be taken by the Director
	 */	
	@Test
	public void callReceivedDirector(){
		dispatcher.dispatchCall(call0);
		dispatcher.dispatchCall(call1);
		dispatcher.dispatchCall(call2);
		dispatcher.dispatchCall(call3);
		dispatcher.dispatchCall(call4);
		dispatcher.dispatchCall(call5);
		dispatcher.dispatchCall(call6);
		dispatcher.dispatchCall(call7);
		dispatcher.dispatchCall(call8);
		dispatcher.dispatchCall(call9);
		assertEquals(dispatcher.idleOperators().intValue(),0);
		assertEquals(dispatcher.idleSupervisors().intValue(),0);
		assertEquals(dispatcher.idleDirectors().intValue(),0);
		assertEquals(dispatcher.busyOperators().intValue(),5);
		assertEquals(dispatcher.busySupervisors().intValue(),4);
		assertEquals(dispatcher.busyDirectors().intValue(),1);
	}
	
	/**
	 * All employees are busy so the call is 'on Hold'
	 */	
	@Test
	public void onHoldCall(){
		dispatcher.dispatchCall(call0);
		dispatcher.dispatchCall(call1);
		dispatcher.dispatchCall(call2);
		dispatcher.dispatchCall(call3);
		dispatcher.dispatchCall(call4);
		dispatcher.dispatchCall(call5);
		dispatcher.dispatchCall(call6);
		dispatcher.dispatchCall(call7);
		dispatcher.dispatchCall(call8);
		dispatcher.dispatchCall(call9);
		dispatcher.dispatchCall(call10);
		assertEquals(dispatcher.onHoldCalls().intValue(),1);
	}
	
}
