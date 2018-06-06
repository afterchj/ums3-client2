package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

//@Entity
//@DiscriminatorValue("diyInfo")
public class DiyInfo extends CateItem {

	private Diy diy;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "parent_id")
	public Diy getDiy() {
		return diy;
	}

	public void setDiy(Diy diy) {
		this.diy = diy;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
