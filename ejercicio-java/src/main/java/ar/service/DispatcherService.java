package ar.service;

import java.util.List;

import ar.model.Call;
import ar.model.Employee;

public interface DispatcherService {

	
	
	public void dispatchCall(Call call);
	public void endCall(Call call);
	public Long idleOperators();
	public Long idleDirectors();
	public Long idleSupervisors();
	public Long busySupervisors();
	public Long busyOperators();
	public Long busyDirectors();
	public void setEmployees(List<Employee> asList);
}
