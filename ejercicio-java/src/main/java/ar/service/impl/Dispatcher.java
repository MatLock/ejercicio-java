package ar.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import ar.model.Call;
import ar.model.Employee;
import ar.service.DispatcherService;

public class Dispatcher implements DispatcherService {

	
	private List<Employee> employees;
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
		
	
	
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public void dispatchCall(Call call)  {
		try{
			lock.lock();
			Employee employee =findIdleEmployee();
			while (employee == null){
				System.out.println("Call with Id: " + call.getId() + " is on Hold ");									
				condition.awaitUninterruptibly();
				employee = findIdleEmployee();
			}
			employee.setCall(call);
			employee.changeState();
			System.out.println("Call with Id: " + call.getId() + " is taken by " + employee.toString());				
		}finally{
			lock.unlock();
		}
	}
	
	@Override
	public void endCall(Call call){
		try{
			lock.lock();
			Employee employee = findEmployeeWithCall(call);
			employee.setCall(null);
			employee.changeState();			
			System.out.println("Call with id: "+call.getId() + " ended...");
			condition.signal();			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(call.getId());
		}finally{
			lock.unlock();
		}
	}
	
	private  Employee findEmployeeWithCall(Call call) {
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
