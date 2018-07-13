package tw.lanyitin.zools;

import tw.lanyitin.zools.context.MappingContext;

public class Binding {
	private final String lValue;
	private final String rValue;
	private final MappingContext context;

	public Binding(String lValue, String rValue, MappingContext context) {
		this.lValue = lValue;
		this.rValue = rValue;
		this.context = context;
	}
	
	
	public String getLValue() {
		return lValue;
	}
	
	public String getRValue() {
		return rValue;
	}

	public MappingContext getContext() {
		return context;
	}

}
