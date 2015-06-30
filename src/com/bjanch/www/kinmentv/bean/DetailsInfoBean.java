package com.bjanch.www.kinmentv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wxy on 2015/5/11.
 */
public class DetailsInfoBean implements Serializable {

    private List<ActorBean> actors;
    private List<TypeBean> types;

    public List<ActorBean> getActors() {
        return actors;
    }

    public void setActors(List<ActorBean> actors) {
        this.actors = actors;
    }

    public List<TypeBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypeBean> types) {
        this.types = types;
    }
}
