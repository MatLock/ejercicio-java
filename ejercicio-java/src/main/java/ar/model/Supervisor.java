package ar.model;

public class Supervisor extends Employee {

	@Override
	public boolean isDirector() {
		return false;
	}

	@Override
	public boolean isSupervisor() {
		return true;
	}

	@Override
	public boolean isOperator() {
		return false;
	}
	
	public Supervisor(Integer id){
		super(id);
	}
	
	@Override
	public String toString(){
		return "Supervisor ID: " + this.id;
	}


}
