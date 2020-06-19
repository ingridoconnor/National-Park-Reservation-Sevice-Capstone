package com.techelevator.site;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

    public List<Site> getAllSites();

	List<Site> getSitesByDate(LocalDate fromDate, LocalDate toDateLong, Long campgroundId);
}
