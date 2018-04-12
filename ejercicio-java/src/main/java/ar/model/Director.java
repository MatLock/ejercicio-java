package ar.model;

public class Director extends Employee {

	@Override
	public boolean isDirector() {
		return true;
	}

	@Override
	public boolean isSupervisor() {
		return false;
	}

	@Override
	public boolean isOperator() {
		return false;
	}
	
	
	@Override
	public String toString(){
		return "Director ID: " + this.id;
	}

	public Director(Integer id){
		super(id);
	}
	

}
