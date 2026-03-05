package com.example.listycity;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Stores and manages a list of cities.
 *
 * <p>Lab Notes: Implements and tests city lookup, deletion with exception handling, and city
 * counting for Lab 06.
 *
 * @author Maaz Shahid - 27100445
 * @version 1.0 (Lab 06, 3/3/2026, CS-360)
 * @since 1.0
 */
public class CityList {
    private final ArrayList<City> cities;

    /**
     * Creates an empty city list.
     */
    public CityList() {
        cities = new ArrayList<>();
    }

    /**
     * Adds a city to the list.
     *
     * @param city city to add
     */
    public void addCity(City city) {
        cities.add(city);
    }

    /**
     * Returns whether a given city exists in the list.
     *
     * @param city city to look for
     * @return true if the city is present, otherwise false
     */
    public boolean hasCity(City city) {
        return cities.contains(city);
    }

    /**
     * Removes a city if it exists, otherwise throws an exception.
     *
     * @param city city to remove
     * @throws NoSuchElementException if the city does not exist in the list
     */
    public void delete(City city) {
        if (!hasCity(city)) {
            throw new NoSuchElementException("City not found.");
        }
        cities.remove(city);
    }

    /**
     * Returns the number of cities in the list.
     *
     * @return total city count
     */
    public int countCities() {
        return cities.size();
    }
}
