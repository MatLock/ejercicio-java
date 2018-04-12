package ar.model;

public class Idle  extends State{

	@Override
	public boolean isBusy() {
		return false;
	}

	@Override
	public boolean isIdle() {
		return true;
	}

	@Override
	public State changeState() {
		return new Busy();
	}

	
	
}
