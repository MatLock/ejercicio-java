package ar.model;

public abstract class State {

	public abstract boolean isBusy();
	public abstract boolean isIdle();
	public abstract State changeState();
	
}
