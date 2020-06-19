package com.techelevator.park;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

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
	public String toString() {
		return parkId + " " + parkName + " " + location + " " + estDate + " " + " " 
	+ visitors + " " + description;
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

//	@Override
//	public int hashCode() {
//		return Objects.hash(getParkId(), getParkName(), getLocation(), getEstDate(), getArea(), getDescription(), getVisitors());
//	}
}
