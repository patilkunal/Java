package com.inovision.util.predicate;

public class Audit {
	
	private int retention;

	Audit(int retention) {
		this.retention = retention;
	}
	
	public int getRetention() {
		return retention;
	}
	
	public String toString() {
		return "Audit with retention " + retention;
	}	
}