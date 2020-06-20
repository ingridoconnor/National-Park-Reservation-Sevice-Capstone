package com.techelevator.site;

import java.math.BigDecimal;

public class Site {

    private Long siteId;
    private Long campgroundId;
    private int siteNumber;
    private int maxOccupancy;
    private boolean accessible;
    private int maxRvLength;
    private boolean utilities;
    private BigDecimal dailyFee;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteID(Long siteID) {
        this.siteId = siteID;
    }

    public Long getCampgroundId() {
        return campgroundId;
    }

    public void setCampgroundId(Long campgroundId) {
        this.campgroundId = campgroundId;
    }

    public int getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(int siteNumber) {
        this.siteNumber = siteNumber;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public String getAccessible() {
        return (accessible ? "Yes" : "No");
    }

    public String getUtilities() {
        return (utilities ? "Yes" : "N/A");
    }

    public int getMaxRvLength() {
        return maxRvLength;
    }

    public void setMaxRvLength(int maxRvLength) {
        this.maxRvLength = maxRvLength;
    }

    public boolean isUtilities() {
        return utilities;
    }

    public void setUtilities(boolean utilities) {
        this.utilities = utilities;
    }

    public BigDecimal getDailyFee() {
        return dailyFee;
    }
    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }

	@Override
	public String toString() {
		return siteNumber + " " + maxOccupancy + " " + (accessible ? "Yes" : "No") + " " + maxRvLength + " " + (utilities ? "Yes" : "N/A") + " " + getDailyFee();
	}
     
}
