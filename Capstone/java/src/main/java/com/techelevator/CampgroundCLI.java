package com.techelevator;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.park.ParkDAO;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.Reservation;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.Site;
import com.techelevator.site.SiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

    private String[] parkNames;

    private static final String PARK_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
    private static final String PARK_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Reservation";
    private static final String PARK_MENU_OPTION_RETURN = "Return to Previous Screen";
    private static final String[] PARK_MENU_OPTIONS = new String[]{PARK_MENU_OPTION_VIEW_CAMPGROUNDS,
			                                                       PARK_MENU_OPTION_SEARCH_FOR_RESERVATION,
                                                                   PARK_MENU_OPTION_RETURN};
    private static final String CAMPGROUND_MENU_OPTION_SEARCH_FOR_RES = "Search For Available Reservation";
    private static final String CAMPGROUND_MENU_OPTION_RETURN = "Return To Previous Screen";
    private static final String[] CAMPGROUND_MENU_OPTIONS = new String[]{CAMPGROUND_MENU_OPTION_SEARCH_FOR_RES,
                                                                         CAMPGROUND_MENU_OPTION_RETURN};
    private Menu menu;
    private ParkDAO parkDAO;
    private CampgroundDAO campgroundDAO;
    private SiteDAO siteDAO;
    private ReservationDAO reservationDAO;

    public static void main(String[] args) {

        CampgroundCLI application = new CampgroundCLI();
        application.run();

    }

    public CampgroundCLI() {

        menu = new Menu(System.in, System.out);

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        parkDAO = new JDBCParkDAO(dataSource);
        campgroundDAO = new JDBCCampgroundDAO(dataSource);
        siteDAO = new JDBCSiteDAO(dataSource);
        reservationDAO = new JDBCReservationDAO(dataSource);
        parkNames = new String[parkDAO.getAllParks().size()];

        for (int i = 0; i < parkNames.length; i++) {
            parkNames[i] = parkDAO.getAllParks().get(i).getParkName();
        }

    }

    public void run() {

        while (true) {

            printHeading("Select a Park for Further Details");
            String choice = (String) menu.getChoiceFromOptions(parkNames);
            System.out.println();
            printParkInfoAndGetNextChoice(choice);

        }
    }

    private void printParkInfoAndGetNextChoice(String parkName) {
        Park park = parkDAO.getParkByName(parkName);
        System.out.println(park);

        printHeading("Select a Command");

		String parkMenuChoice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);

		if (parkMenuChoice.equals(PARK_MENU_OPTION_VIEW_CAMPGROUNDS)) {
			listCampgroundsInPark(park.getParkId());
		} else if (parkMenuChoice.equals(PARK_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
			listCampgroundsInPark(park.getParkId());
			handleSearchReservations();
		} else if (parkMenuChoice.equals(PARK_MENU_OPTION_RETURN)) {
			return;
		}
    }

    private void handleSearchReservations() {

		System.out.println("\nWhich campground are you interested in? (enter '0' to return to home screen)");
		String answer = menu.getUserInput();
		Long answerAsId = Long.parseLong(answer);

		if (answerAsId == 0) {
		    return;
        }

		System.out.println("What is the arrival date? (YYYY-MM-DD)");
		String arrivalDate = menu.getUserInput();
		LocalDate arrivalDateAsLocalDate = LocalDate.parse(arrivalDate);
		System.out.println("What is the departure date? (YYYY-MM-DD)");
        String departureDate = menu.getUserInput();
        LocalDate departureDateAsLocalDate = LocalDate.parse(departureDate);

        List<Site> sitesAvailableDuringSelectedDates = siteDAO.getSitesByDate(answerAsId, arrivalDateAsLocalDate, departureDateAsLocalDate);

        if (sitesAvailableDuringSelectedDates.isEmpty()) {
            System.out.println("No campsites available during selected dates. Please enter different dates, or select a different campsite.");
        } else {
            printHeading("Results Matching Your Search Criteria");
            System.out.println("Site No. Max Occup. Accessible? Max RV Length Utilities Cost");
            for (Site site : sitesAvailableDuringSelectedDates) {
                System.out.println(site);
            }
            System.out.println("\nWhich site should be reserved?");
        	String ans = menu.getUserInput();
        	Long ansAsId = Long.parseLong(ans);
        	handleAddReservation(ansAsId, arrivalDateAsLocalDate, departureDateAsLocalDate);
        }

	}
    private void handleAddReservation(Long siteId, LocalDate fromDate, LocalDate toDate) {
    	
    	System.out.println("\nWhat name should we reserve under?");
    	String name = menu.getUserInput();
    	Reservation res = new Reservation();
    	res.setSiteId(siteId);
    	res.setName(name);
    	res.setFromDate(fromDate);
    	res.setToDate(toDate);
    	res = reservationDAO.createReservation(res);
    	System.out.println("reservation have been made and your confirmation ID is: " + res.getReservationId());
    	
    	
    	
    	
    }


    private void listCampgroundsInPark(Long parkId) {
        printHeading("Name Open Close Daily Fee");
        List<Campground> campgroundsAtPark = campgroundDAO.getCampgroundByParkId(parkId);
        for (Campground campground : campgroundsAtPark) {
            System.out.print("#" + campground.getCampgroundId() + " ");
            System.out.println(campground);
        }
    }

    private void printHeading(String headingText) {
        System.out.println("\n" + headingText);
        for (int i = 0; i < headingText.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

}
