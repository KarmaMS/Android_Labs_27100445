package com.example.listycity;

import java.util.Objects;

/**
 * Represents a city and its province code.
 *
 * <p>Lab Notes: This model is used in Lab 06 for list membership, deletion, and counting logic.
 *
 * @author Maaz Shahid - 27100445
 * @version 1.0 (Lab 06, 3/3/2026, CS-360)
 * @since 1.0
 */
public class City {
    private String name;
    private String province;

    /**
     * Creates a city object.
     *
     * @param name City name
     * @param province Province abbreviation
     */
    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    /**
     * Returns the city name.
     *
     * @return city name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the city name.
     *
     * @param name new city name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the province abbreviation.
     *
     * @return province abbreviation
     */
    public String getProvince() {
        return province;
    }

    /**
     * Updates the province abbreviation.
     *
     * @param province new province abbreviation
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Formats a city for list display.
     *
     * @return city string in "Name, Province" format
     */
    @Override
    public String toString() {
        return name + ", " + province;
    }

    /**
     * Compares two city objects by city name and province.
     *
     * @param o object to compare
     * @return true if both objects represent the same city and province
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(province, city.province);
    }

    /**
     * Returns a hash code based on city name and province.
     *
     * @return hash code for this city
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, province);
    }
}
