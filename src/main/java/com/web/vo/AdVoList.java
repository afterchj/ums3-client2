package com.web.vo;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("ads")
public class AdVoList implements Serializable{
	
	public AdVoList(List<AdVo> adVos) {
		super();
		this.adVos = adVos;
	}

	@XStreamOmitField
	private static final long serialVersionUID = 1L;
	
	@XStreamImplicit
	private List<AdVo> adVos;

	public List<AdVo> getAdVos() {
		return adVos;
	}

	public void setAdVos(List<AdVo> adVos) {
		this.adVos = adVos;
	}
}
