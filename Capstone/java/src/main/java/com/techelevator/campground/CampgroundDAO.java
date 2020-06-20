package com.techelevator.campground;

import java.util.List;


public interface CampgroundDAO {
	
	public List<Campground> getAllCampgrounds();
	
	public Campground getCampgroundByCampgroundId(Long campgroundId);
	
	public List<Campground>getCampgroundByParkId(Long parkId);

}
