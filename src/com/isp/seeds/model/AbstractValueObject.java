package com.isp.seeds.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractValueObject implements ValueObject {

	public AbstractValueObject() {

	} 

	@Override

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
