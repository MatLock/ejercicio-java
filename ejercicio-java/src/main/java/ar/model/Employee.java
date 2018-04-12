package ar.model;

public abstract class Employee {

	protected State state;
	protected Integer id;
	private Call call;
	
	public abstract boolean isDirector();
	public abstract boolean isSupervisor();
	public abstract boolean isOperator();
	
	
	public Employee(Integer id){
		this.id = id;
		this.state = new Idle();
	}
	
	public void changeState() {
		this.state = this.state.changeState();
	}
	
	public boolean isIdle(){
		return this.state.isIdle();
	}
	
	public boolean isBusy(){
		return this.state.isBusy();
	}
	
	public Integer withCall(){
		return call != null ? call.getId() : null;
	}	
	public Call getCall() {
		return call;
	}
	public void setCall(Call call) {
		this.call = call;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	
	
	
}
