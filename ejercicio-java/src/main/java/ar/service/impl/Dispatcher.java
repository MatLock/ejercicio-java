package ar.service.impl;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import ar.model.Call;
import ar.model.Director;
import ar.model.Employee;
import ar.model.Operator;
import ar.model.Supervisor;
import ar.service.DispatcherService;

public class Dispatcher implements DispatcherService {

	
	private List<Employee> employees;
	private Queue<Call> onHoldCalls;
		
	/**
	 * Creates a dispatcher instance with 1 director, 4 supervisors and 5 operators
	 */
	public Dispatcher(){
		employees = Arrays.asList(new Director(1),
				new Supervisor(2),new Supervisor(3),new Supervisor(4),new Supervisor(5),
				new Operator(6),new Operator(7),new Operator(8),new Operator(9),new Operator(10));
		onHoldCalls = new ArrayDeque<Call>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public synchronized void dispatchCall(Call call)  {
		Employee employee = findIdleEmployee();
		if(employee != null){
			employee.setCall(call);
			employee.changeState();
			call.getSemaphore().release();
			System.out.println("Call with Id: " + call.getId() + " is taken by " + employee.toString());
		}else{
			System.out.println("Call with Id: " + call.getId() + " is on Hold ");
			onHoldCalls.add(call);
		}
	}
	
	@Override
	public synchronized void endCall(Call call){
		Employee employee = findEmployeeWithCall(call);
		if(!onHoldCalls.isEmpty()){
			System.out.println("Call with id: "+call.getId() + " ended...");
			Call onHoldCall = onHoldCalls.poll(); 
			employee.setCall(onHoldCall);
			System.out.println("Call with id: "+onHoldCall.getId() + " is taken by " + employee.toString());
			onHoldCall.getSemaphore().release();
		}else{
			employee.setCall(null);
			employee.changeState();			
		}
	}
	
	@Override
	public Integer onHoldCalls(){
		return onHoldCalls.size();
	}
	
	private Employee findEmployeeWithCall(Call call) {
		return employees.stream().filter(e -> e.getCall() != null ? e.getCall().equals(call):false).findFirst().get();
	}

	public Long idleOperators(){
		return employees.stream().filter(e -> e.isOperator() && e.isIdle()).count();
	}
	
	public Long idleDirectors(){
		return employees.stream().filter(e -> e.isDirector() && e.isIdle()).count();
	}
	
	public Long idleSupervisors(){
		return employees.stream().filter(e -> e.isSupervisor() && e.isIdle()).count();
	}
	
	public Long busySupervisors(){
		return employees.stream().filter(e -> e.isSupervisor() && e.isBusy()).count();
	}
	
	public Long busyOperators(){
		return employees.stream().filter(e -> e.isOperator() && e.isBusy()).count();
	}
	
	public Long busyDirectors(){
		return employees.stream().filter(e -> e.isDirector() && e.isBusy()).count();
	}
	
	private Employee findIdleEmployee(){		
		Employee employee = findIdleOperator();
		if(employee == null){
			employee = findIdleSupervisor();
		}
		if(employee == null){
			employee = findIdleDirector();
		}
		return employee;
	}
	
	private Employee findIdleOperator(){
		Optional<Employee> optional = employees.stream().filter(e -> e.isOperator() && e.isIdle()).findFirst();
		return optional.isPresent() ? optional.get() : null ;
	}
	
	private Employee findIdleSupervisor(){
		Optional<Employee> optional = employees.stream().filter(e -> e.isSupervisor() && e.isIdle()).findFirst();
		return optional.isPresent() ? optional.get() : null ;
	}
	
	private Employee findIdleDirector(){
		Optional<Employee> optional = employees.stream().filter(e -> e.isDirector() && e.isIdle()).findFirst();
		return optional.isPresent() ? optional.get() : null ;
	}

}
