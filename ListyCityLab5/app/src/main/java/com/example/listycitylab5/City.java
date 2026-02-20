package com.example.listycitylab5;

import java.io.Serializable;

public class City implements Serializable {
    private String id;
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public City(String id, String name, String province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getProvince() { return province; }

    // Needed for edit
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProvince(String province) { this.province = province; }
}
