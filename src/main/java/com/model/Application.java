package com.model;

import java.io.Serializable;

/**
 * Created by nannan.li on 2018/7/19.
 */
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String descr;
    private String serialno;

    public Application() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getSerialno() {
        return this.serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
}

