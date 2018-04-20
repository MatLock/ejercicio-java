package ar.model;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Call {

	private Integer id;
	private Integer duration;
	
	public Call(Integer id){
		this.id=id;
		Random rn = new Random();
		duration = rn.nextInt(11 - 5) + 5;
	}
	
	public Integer getId() {
		return id;
	}	
	public void setId(Integer id) {
		this.id = id;
	}	
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Call other = (Call) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
