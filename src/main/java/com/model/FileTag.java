package com.model;


/**
 * User: ken.cui
 * Date: 13-5-14
 * Time: 上午9:27
 */
public class FileTag extends IdEntity {
	private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
