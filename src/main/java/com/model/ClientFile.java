package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClientFile extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String pkName;
	private String path;
	private Long size;
	private String version;
    private String description;
	private String createTime;
	private String modifyTime;
	private String dtype;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
