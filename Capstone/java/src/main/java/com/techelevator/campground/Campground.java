package com.techelevator.campground;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

public class Campground {

    private Long campgroundId;
    private Long parkId;
    private String name;
    private String openFromMonth;
    private String openToMonth;
    private BigDecimal dailyFee;
    
    
    public boolean isOpen(int d1, int d2) {
    	return (Integer.parseInt(openFromMonth) <= d1 && Integer.parseInt(openToMonth) >= d2);
    		
    	
    }

	public Long getCampgroundId() {
        return campgroundId;
    }

    public void setCampgroundId(Long campgroundId) {
        this.campgroundId = campgroundId;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenFromMonth() {
        return (Month.of(Integer.parseInt(openFromMonth)).toString());
    }

    public void setOpenFromMonth(String openFromMonth) {
        this.openFromMonth = openFromMonth;
    }


    public String getOpenToMonth() {
        return (Month.of(Integer.parseInt(openToMonth)).toString());
    }

    public void setOpenToMonth(String openToMonth) {
        this.openToMonth = openToMonth;
    }

    public BigDecimal getDailyFee() {
        return dailyFee;
    }

    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }

    @Override
    public String toString() {
        return name + " " + getOpenFromMonth() + " " + getOpenToMonth() + " " + dailyFee;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Campground)) return false;
		Campground that = (Campground) o;
		return Objects.equals(getCampgroundId(), that.getCampgroundId()) &&
				Objects.equals(getParkId(), that.getParkId()) &&
				Objects.equals(getName(), that.getName()) &&
				Objects.equals(getOpenFromMonth(), that.getOpenFromMonth()) &&
				Objects.equals(getOpenToMonth(), that.getOpenToMonth()) &&
				Objects.equals(getDailyFee(), that.getDailyFee());
	}

}
