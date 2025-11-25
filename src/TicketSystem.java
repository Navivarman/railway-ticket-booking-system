import java.util.*;

public class TicketSystem {
    private final List<String> availableBerths = new ArrayList<>(Arrays.asList("L","M","U"));
    private final Queue<Passenger> racQueue = new LinkedList<>();
    private final Queue<Passenger> waitingListQueue = new LinkedList<>();
    private final List<Passenger> confirmedPassenger = new ArrayList<>();
    private int ticketCounter = 1;

    public void bookingTickets(String name,int age,String gender,String berthPreference){
        String ticketId = "T"+ticketCounter++;
        Passenger passenger;
        if(!availableBerths.isEmpty()){
            String allocated = allocatedBerth(age,gender,berthPreference);
            availableBerths.remove(allocated);
            passenger = new Passenger(name,age,gender,ticketId,berthPreference,allocated);
            confirmedPassenger.add(passenger);
            System.out.println("Ticket Confirmed: " + passenger);
        }else if(racQueue.size() < 1){
            passenger = new Passenger(name,age,gender,ticketId,berthPreference,"RAC");
            racQueue.offer(passenger);
            System.out.println("Tickets in RAC: " + passenger);
        }else if(waitingListQueue.size() < 1){
            passenger = new Passenger(name,age,gender,ticketId,berthPreference,"WaitingList");
            waitingListQueue.offer(passenger);
            System.out.println("Ticket in Waiting List: " + passenger);
        }else{
            System.out.println("No tickets available");
        }
    }

    private String allocatedBerth(int age,String gender,String berthPerference){
        if(age > 60 || gender.equalsIgnoreCase("female") || availableBerths.contains("L")){
            return "L";
        }else if(availableBerths.contains(berthPerference)){
            return berthPerference;
        }
        return availableBerths.get(0);
    }

    public void cancelTickets(String ticketId){
       Optional<Passenger> passengerOpt = confirmedPassenger.stream().filter(p -> p.ticketId.equals(ticketId)).findFirst();
       if(passengerOpt.isPresent()){
            Passenger passenger = passengerOpt.get();
            confirmedPassenger.remove(passenger);
            availableBerths.add(passenger.allocatedBerth);

            if(!racQueue.isEmpty()){
                Passenger racPass = racQueue.poll();
                racPass.allocatedBerth = allocatedBerth(racPass.age,racPass.gender, racPass.preferredBerth);
                confirmedPassenger.add(racPass);
                availableBerths.remove(racPass.allocatedBerth);
                System.out.println("RAC ticket moved to confirmed: " + racPass);
            }
            if(!waitingListQueue.isEmpty()){
                Passenger waitPass = waitingListQueue.poll();
                waitPass.allocatedBerth = "RAC";
                racQueue.offer(waitPass);
                System.out.println("Waiting list ticket moved to RAC: " + waitPass);
            }
           System.out.println("Ticket cancelled successfully for ID: " + ticketId);
       }else{
           System.out.println("No ticket found with ID: " + ticketId);
       }
    }

    public void viewConfirmedTickets(){
        if(confirmedPassenger.isEmpty()){
            System.out.println("No confirmed tickets.");
        }else{
            System.out.println("Confirmed Tickets List");
            for(Passenger p : confirmedPassenger){
                System.out.println(p);
            }
        }
    }

    public void viewAvailableTickets(){
        System.out.println("Available Berths: " + availableBerths.size());
        System.out.println("Available RAC Tickets: " + (1 - racQueue.size()));
        System.out.println("Available Waiting List Tickets: " + (1 - waitingListQueue.size()));
    }

    public void viewRacTickets(){
        if (racQueue.isEmpty()) {
            System.out.println("No RAC tickets.");
        } else {
            System.out.println("RAC Tickets:");
            for (Passenger passenger : racQueue) {
                System.out.println(passenger);
            }
        }
    }

    public void viewWaitingListTickets() {
        if (waitingListQueue.isEmpty()) {
            System.out.println("No Waiting List tickets.");
        } else {
            System.out.println("Waiting List Tickets:");
            for (Passenger passenger : waitingListQueue) {
                System.out.println(passenger);
            }
        }
    }
}
