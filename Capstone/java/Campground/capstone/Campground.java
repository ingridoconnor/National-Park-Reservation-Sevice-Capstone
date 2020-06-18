package capstone;

import java.math.BigDecimal;

public class Campground {

private Long campgroundId;
private Long parkId;
private String parkName;
private int openFromMonth;
private int openToMonth;
private BigDecimal dailyFee;


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
public String getParkName() {
	return parkName;
}
public void setParkName(String parkName) {
	this.parkName = parkName;
}
public int getOpenFromMonth() {
	return openFromMonth;
}
public void setOpenFromMonth(int openFromMonth) {
	this.openFromMonth = openFromMonth;
}
public int getOpenToMonth() {
	return openToMonth;
}
public void setOpenToMonth(int openToMonth) {
	this.openToMonth = openToMonth;
}
public BigDecimal getDailyFee() {
	return dailyFee;
}
public void setDailyFee(BigDecimal dailyFee) {
	this.dailyFee = dailyFee;
}
//public String toString() {
//	return parkName;
//}

}
