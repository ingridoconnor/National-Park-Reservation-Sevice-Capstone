package com.techelevator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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

    private static final String CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
    private static final String CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Reservation";
    private static final String CAMPGROUND_MENU_OPTION_RETURN = "Return to Previous Screen";
    private static final String[] CAMPGROUND_MENU_OPTIONS = new String[]{CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS,
			                                                       CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION,
                                                                   CAMPGROUND_MENU_OPTION_RETURN};

    private static final String CAMPGROUND_SUBMENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Available Reservation";
    private static final String CAMPGROUND_SUBMENU_OPTION_RETURN = "Return to Previous Screen";
    private static final String[] CAMPGROUND_SUBMENU_OPTIONS = new String[] {CAMPGROUND_SUBMENU_OPTION_SEARCH_FOR_RESERVATION,
                                                                          CAMPGROUND_SUBMENU_OPTION_RETURN};

    private Menu menu;
    private Park park;
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
        dataSource.setPassword("G0dmanthing");

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
            park = parkDAO.getParkByName(choice);
            System.out.println();
            printParkInformation();
        }
    }

    private void printParkInformation() {

        System.out.println(park);
        handleCampgroundMenu();

    }

    private void handleCampgroundMenu() {

        while (true) {

            printHeading("Select a Command");

            String parkMenuChoice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);

            if (parkMenuChoice.equals(CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS)) {
                listCampgroundsInPark(park.getParkId());
                handleCampgroundSubMenu();
            } else if (parkMenuChoice.equals(CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
                listCampgroundsInPark(park.getParkId());
                handleSearchReservations();
            } else if (parkMenuChoice.equals(CAMPGROUND_MENU_OPTION_RETURN)) {
                break;
            }
        }
    }

    private void handleCampgroundSubMenu() {
        String campgroundMenuChoice = (String) menu.getChoiceFromOptions(CAMPGROUND_SUBMENU_OPTIONS);
        if (campgroundMenuChoice.equals(CAMPGROUND_SUBMENU_OPTION_SEARCH_FOR_RESERVATION)) {
            handleSearchReservations();
        } else if (campgroundMenuChoice.equals(CAMPGROUND_SUBMENU_OPTION_RETURN)) {
            return;
        }
    }

    private void handleSearchReservations() {

		System.out.println("\nWhich campground are you interested in? (enter '0' to return to previous screen)");
		String answer = menu.getUserInput();
        long answerAsId = verifyCampgroundSelection(answer);

		System.out.println("What is the arrival date? (YYYY-MM-DD)");
		String arrivalDate = menu.getUserInput();
		int monthOfArrivalDate = handleNotInRange(arrivalDate);
		System.out.println("What is the departure date? (YYYY-MM-DD)");
        String departureDate = menu.getUserInput();
        int monthOfDepartureDate = handleNotInRange(departureDate);

        if (isValidDate(arrivalDate) && isValidDate(departureDate)) {
            // empty if statement who dis
            // in all seriousness when we tried inverting this logic
            // if (!isValidDate(arrivalDate) || !isValidDate(departureDate))
            // we would get parsing errors on the local dates created below
        } else {
            System.out.println("Not a valid date. Please try again");
            System.out.flush();
            handleSearchReservations();
        }

        LocalDate arrivalDateAsLocalDate = LocalDate.parse(arrivalDate);
        LocalDate departureDateAsLocalDate = LocalDate.parse(departureDate);
        long durationOfStay = ChronoUnit.DAYS.between(arrivalDateAsLocalDate, departureDateAsLocalDate);

        Period intervalPeriod = Period.between(arrivalDateAsLocalDate, departureDateAsLocalDate);
        BigDecimal daysInIntervalPeriod = BigDecimal.valueOf(intervalPeriod.getDays());

        List<Site> sitesAvailableDuringSelectedDates = siteDAO.getSitesByDate(answerAsId, arrivalDateAsLocalDate, departureDateAsLocalDate);
        Campground campground = campgroundDAO.getCampgroundByCampgroundId(answerAsId);
        boolean campgroundIsOpen = campground.isOpen(monthOfArrivalDate, monthOfDepartureDate);

        if (sitesAvailableDuringSelectedDates.isEmpty() || !campgroundIsOpen) {
            System.out.println("No campsites available during selected dates. Please enter different dates, or select a different campsite.");
        } else {
            printHeading("Results Matching Your Search Criteria");
            System.out.println(String.format("%-10s%-15s%-15s%15s%15s%10s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost"));
            
            for (Site site : sitesAvailableDuringSelectedDates) {
                System.out.println(String.format("%-10s%-15s%-15s%15s%15s%10s", site.getSiteNumber(), site.getMaxOccupancy(),
                        site.getAccessible(), site.getMaxRvLength(), site.getUtilities(), site.getDailyFee().multiply(BigDecimal.valueOf(durationOfStay))));
            }

            System.out.println("\nWhich site should be reserved?");
            //
        	String ans = menu.getUserInput();
        	long siteId = verifySiteSelection(ans, sitesAvailableDuringSelectedDates);
        	handleAddReservation(siteId, arrivalDateAsLocalDate, departureDateAsLocalDate);
        }

	}

	private long verifySiteSelection(String siteNumberInput, List<Site> availableSites) {

        int siteNumber = -1;
        boolean siteNumCheckIsLegit = false;

        try {
            siteNumber = Integer.parseInt(siteNumberInput);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid entry. Please select another value (or enter '0' to return to previous screen");

        }

        for (Site s : availableSites) {
            if (siteNumber == s.getSiteNumber()) {
                siteNumCheckIsLegit = true;
                break;
            }
        }

        if (siteNumber == 0) {
            handleSearchReservations();
        } else if (siteNumber < 0 || !siteNumCheckIsLegit) {
            System.out.println("Invalid entry.");
            handleSearchReservations();
        }

        siteNumber--;

        return availableSites.get(siteNumber).getSiteId();
    }

    private long verifyCampgroundSelection(String userCampgroundIdInput) {

        long campgroundId = 0;
        List<Campground> campgroundsInPark = campgroundDAO.getCampgroundByParkId(park.getParkId());
        boolean campgroundIdCheck = false;

        try {
            campgroundId = Long.parseLong(userCampgroundIdInput);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid entry.");
            handleSearchReservations();
        }

        for (Campground c : campgroundsInPark) {
            if (c.getCampgroundId().equals(campgroundId)) {
                campgroundIdCheck = true;
                break;
            }
        }

        if (campgroundId == 0) {
            handleCampgroundMenu();
        } else if (campgroundId < 0 || !campgroundIdCheck) {
            System.out.println("Invalid entry.");
            handleSearchReservations();
        }

        return campgroundId;
    }

    private int handleNotInRange(String arrivalDate) {
    	if(arrivalDate.length() < 7) {
    		return 0;
    	}
    	return Integer.parseInt(arrivalDate.substring(5, 7));
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
        printHeading(String.format("   %-35s%-9s%10s%20s", "Name", "Open", "Close", "Daily Fee"));
        List<Campground> campgroundsAtPark = campgroundDAO.getCampgroundByParkId(parkId);
        for (Campground campground : campgroundsAtPark) {
            System.out.print("#" + campground.getCampgroundId() + " ");
            System.out.println(String.format("%-35s%-12s%10s%15s", campground.getName(), campground.getOpenFromMonth(),
                               campground.getOpenToMonth(), campground.getDailyFee()));
        }
    }


    private boolean isValidDate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(input.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private void printHeading(String headingText) {
        System.out.println("\n" + headingText);
        for (int i = 0; i < headingText.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

}
