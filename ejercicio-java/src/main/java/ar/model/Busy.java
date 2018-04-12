package ar.model;

public class Busy extends State {

	@Override
	public boolean isBusy() {
		return true;
	}

	@Override
	public boolean isIdle() {
		return false;
	}

	@Override
	public State changeState() {
		return new Idle();
	}

	

}
