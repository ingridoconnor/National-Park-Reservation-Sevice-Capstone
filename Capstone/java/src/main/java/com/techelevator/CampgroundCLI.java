package com.techelevator;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.park.ParkDAO;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.SiteDAO;
import com.techelevator.view.Menu;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDate;
import java.util.List;

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
            printParkInfoAndGetNextChoice(choice);

        }
    }

    private void printParkInfoAndGetNextChoice(String parkName) {
        Park park = parkDAO.getParkByName(parkName);
        System.out.println(park);

		String parkMenuChoice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);

		if (parkMenuChoice.equals(PARK_MENU_OPTION_VIEW_CAMPGROUNDS)) {
			listCampgroundsInPark(park.getParkId());
		} else if (parkMenuChoice.equals(PARK_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
			listCampgroundsInPark(park.getParkId());
			handleReservation();
		} else if (parkMenuChoice.equals(PARK_MENU_OPTION_RETURN)) {
			return;
		}


    }

    private void handleReservation() {

		System.out.println("\nWhich campground (enter 0 to cancel)?");

		String answer = menu.getUserInput();
		Long answerAsId = Long.parseLong(answer);
        System.out.println(answerAsId);
		System.out.println("What is the arrival date? (YYYY-MM-DD)");
		String arrivalDate = menu.getUserInput();
		LocalDate arrivalDateAsLocalDate = LocalDate.parse(arrivalDate);
		System.out.println("What is the departure date? (YYYY-MM-DD)");
        String departureDate = menu.getUserInput();
        LocalDate departureDateAsLocalDate = LocalDate.parse(departureDate);


	}


    private void listCampgroundsInPark(Long parkId) {
        List<Campground> campgroundsAtPark = campgroundDAO.getCampgroundByParkId(parkId);
        for (Campground c : campgroundsAtPark) {
            System.out.println(c);
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
