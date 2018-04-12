package ar.service;

import ar.model.Call;

public interface DispatcherService {

	
	
	public void dispatchCall(Call call);
	public void endCall(Call call);
	public Long idleOperators();
	public Long idleDirectors();
	public Long idleSupervisors();
	public Long busySupervisors();
	public Long busyOperators();
	public Long busyDirectors();
	public Integer onHoldCalls();
}
