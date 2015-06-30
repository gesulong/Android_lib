package com.bjanch.www.kinmentv.bean;

import java.io.Serializable;

/**
 * Created by wxy on 2015/5/11.
 */
public class ActorBean implements Serializable {
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
