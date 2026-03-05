package com.example.listycity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CityTest {

    @Test
    public void constructor_setsFields() {
        City city = new City("Edmonton", "AB");

        assertEquals("Edmonton", city.getName());
        assertEquals("AB", city.getProvince());
    }

    @Test
    public void setName_updatesName() {
        City city = new City("Edmonton", "AB");

        city.setName("Calgary");

        assertEquals("Calgary", city.getName());
    }

    @Test
    public void setProvince_updatesProvince() {
        City city = new City("Edmonton", "AB");

        city.setProvince("BC");

        assertEquals("BC", city.getProvince());
    }

    @Test
    public void toString_returnsNameAndProvince() {
        City city = new City("Edmonton", "AB");

        assertEquals("Edmonton, AB", city.toString());
    }
}
