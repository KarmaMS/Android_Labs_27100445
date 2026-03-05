package com.example.listycity;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CityListTest {

    @Test
    public void hasCity_returnsTrueForExistingCity() {
        CityList cityList = new CityList();
        City city = new City("Edmonton", "AB");
        cityList.addCity(city);

        assertTrue(cityList.hasCity(new City("Edmonton", "AB")));
    }

    @Test
    public void hasCity_returnsFalseForMissingCity() {
        CityList cityList = new CityList();
        cityList.addCity(new City("Edmonton", "AB"));

        assertFalse(cityList.hasCity(new City("Calgary", "AB")));
    }

    @Test
    public void delete_removesCityFromList() {
        CityList cityList = new CityList();
        City city = new City("Edmonton", "AB");
        cityList.addCity(city);

        cityList.delete(new City("Edmonton", "AB"));

        assertFalse(cityList.hasCity(city));
    }

    @Test
    public void delete_throwsExceptionWhenCityMissing() {
        CityList cityList = new CityList();

        assertThrows(NoSuchElementException.class, () -> cityList.delete(new City("Edmonton", "AB")));
    }

    @Test
    public void countCities_returnsTotalCities() {
        CityList cityList = new CityList();
        cityList.addCity(new City("Edmonton", "AB"));
        cityList.addCity(new City("Calgary", "AB"));
        cityList.addCity(new City("Vancouver", "BC"));

        assertEquals(3, cityList.countCities());
    }
}
