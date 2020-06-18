package com.techelevator.campground;

import java.util.List;


public interface CampgroundDAO {
	
	public List<Campground> getAllCamgrounds();
	
	public List<Campground> getCampgroundById(Long campgroundId);

}
