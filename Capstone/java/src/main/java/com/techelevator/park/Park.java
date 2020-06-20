package com.techelevator.park;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;


public class Park {

    private Long parkId;
    private String parkName;
    private String location;
    private Date estDate;
    private int area;
    private String description;
    private int visitors;

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEstDate() {
        return estDate;
    }

    public void setEstDate(Date estDate) {
        this.estDate = estDate;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {

        return parkName + " National Park " + "\n" +
                "Location: " + location + "\n" +
                "Established: " + estDate + "\n" +
                "Area: " + area + " sq km" + "\n" +
                "Annual Visitors: " + visitors + "\n" +
                "\n" +
                multiLine(getDescription(), " ", 80);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Park)) return false;
        Park park = (Park) o;
        return getArea() == park.getArea() &&
                getVisitors() == park.getVisitors() &&
                Objects.equals(getParkId(), park.getParkId()) &&
                Objects.equals(getParkName(), park.getParkName()) &&
                Objects.equals(getLocation(), park.getLocation()) &&
                Objects.equals(getEstDate(), park.getEstDate()) &&
                Objects.equals(getDescription(), park.getDescription());
    }


    // thank you stackOverflow
	private static String multiLine(String longString, String splitter, int maxLength) {
		return Arrays.stream(longString.split(splitter))
				.collect(
						ArrayList<String>::new,
						(l, s) -> {
							Function<ArrayList<String>, Integer> id = list -> list.size() - 1;
							if(l.size() == 0 || (l.get(id.apply(l)).length() != 0 && l.get(id.apply(l)).length() + s.length() >= maxLength)) l.add("");
							l.set(id.apply(l), l.get(id.apply(l)) + (l.get(id.apply(l)).length() == 0 ? "" : splitter) + s);
						},
						(l1, l2) -> l1.addAll(l2))
				.stream().reduce((s1, s2) -> s1 + "\n" + s2).get();
	}

}

