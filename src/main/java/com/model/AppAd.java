package com.model;

import java.util.Date;

//@Entity
//@Table(name="f_ad_app", uniqueConstraints={@UniqueConstraint(columnNames={"f_id", "offset"})})
public class AppAd extends LongIDEntity {

	private static final long serialVersionUID = 1L;
	private String dtype;
	private Integer offset;
	private ThemeFile file;
	private Date createTime;
	private Integer status;

	// @Column(name="dtype", length=10, nullable=false)
	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	// @Column(name="offset", nullable=false)
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	// @ManyToOne(fetch=FetchType.EAGER, targetEntity=ThemeFile.class)
	// @JoinColumn(name="f_id", nullable=false)
	public ThemeFile getFile() {
		return file;
	}

	public void setFile(ThemeFile file) {
		this.file = file;
	}

	// @Column(name="status", nullable=false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
