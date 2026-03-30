import controllers.SmartDispatchSystem;
import models.Building;
import models.Floor;
import models.Passenger;
import models.Request;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Building building = Building.getInstance(15, 3);
        SmartDispatchSystem dispatcher = building.getSmartDispatchSystem();

        System.out.println("=== Elevator System Simulation Started ===");
        System.out.println("Building: 15 Floors, 3 Elevators");
        System.out.println("Max Capacity per Elevator: 8 people / 680 kg");
        System.out.println("==========================================\n");

        Floor floor3 = building.getFloor(3);
        floor3.pressUpButton();

        Floor floor10 = building.getFloor(10);
        floor10.pressDownButton();

        Floor floor7 = building.getFloor(7);
        floor7.pressUpButton();

        List<Request> requests = new ArrayList<>();

        Request req1 = new Request(3, 12);
        Request req2 = new Request(10, 2);
        Request req3 = new Request(7, 15);

        requests.add(req1);
        requests.add(req2);
        requests.add(req3);

        System.out.println("--- Dispatching Elevators ---");
        dispatcher.dispatchAll(requests);

        System.out.println("\n--- Passenger Boarding Simulation ---");

        Building building2 = Building.getInstance(15, 3);

        Passenger p1 = new Passenger(1, 75.0, 1, 8);
        Passenger p2 = new Passenger(2, 80.0, 1, 5);
        Passenger p3 = new Passenger(3, 70.0, 1, 12);
        Passenger p4 = new Passenger(4, 85.0, 1, 3);
        Passenger p5 = new Passenger(5, 90.0, 1, 9);
        Passenger p6 = new Passenger(6, 65.0, 1, 14);
        Passenger p7 = new Passenger(7, 72.0, 1, 6);
        Passenger p8 = new Passenger(8, 78.0, 1, 11);
        Passenger p9 = new Passenger(9, 88.0, 1, 4);

        List<Request> passengerRequests = new ArrayList<>();
        passengerRequests.add(new Request(1, 8));
        passengerRequests.add(new Request(1, 5));
        passengerRequests.add(new Request(1, 12));
        passengerRequests.add(new Request(1, 3));
        passengerRequests.add(new Request(1, 9));
        passengerRequests.add(new Request(1, 14));
        passengerRequests.add(new Request(1, 6));
        passengerRequests.add(new Request(1, 11));
        passengerRequests.add(new Request(1, 4));

        System.out.println("\n--- Multi-Passenger Dispatch ---");
        dispatcher.dispatchAll(passengerRequests);

        System.out.println("\n--- Final Display State ---");
        building.getFloor(1).showAllDisplays();
        building.getFloor(7).showAllDisplays();
        building.getFloor(15).showAllDisplays();

        System.out.println("\n=== Simulation Complete ===");
    }
}
