package tw.lanyitin.zools;

import tw.lanyitin.zools.context.MappingContext;

public class Binding {
	private final String lValue;
	private final String rValue;
	private final MappingContext context;
	private final boolean optional;

	public Binding(String lValue, String rValue, boolean optional, MappingContext context) {
		this.lValue = lValue;
		this.rValue = rValue;
		this.optional = optional;
		this.context = context;
	}
	
	
	public String getLValue() {
		return lValue;
	}
	
	public String getRValue() {
		return rValue;
	}

	public boolean isOptional() {
		return optional;
	}

	public MappingContext getContext() {
		return context;
	}

}
