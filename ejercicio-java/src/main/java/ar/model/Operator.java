package ar.model;


public class Operator extends Employee {

	@Override
	public boolean isDirector() {
		return false;
	}

	@Override
	public boolean isSupervisor() {
		return false;
	}

	@Override
	public boolean isOperator() {
		return true;
	}
	
	public Operator(Integer id){
		super(id);
	}
	
	@Override
	public String toString(){
		return "Operator ID: " + this.id;
	}
}
