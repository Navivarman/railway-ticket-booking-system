import java.util.*;

public class TicketSystem {
    private final List<String> availableBerths = new ArrayList<>(Arrays.asList("L","M","U"));
    private final Queue<Passenger> racQueue = new LinkedList<>();
    private final Queue<Passenger> waitingListQueue = new LinkedList<>();
    private final List<Passenger> confirmedPassenger = new ArrayList<>();
    private int ticketCounter = 1;

    void bookingTickets(String name,int age,String gender,String berthPreference){
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

    String allocatedBerth(int age,String gender,String berthPerference){
        if(age > 60 || gender.equalsIgnoreCase("female") || availableBerths.contains("L")){
            return "L";
        }else if(availableBerths.contains(berthPerference)){
            return berthPerference;
        }
        return availableBerths.get(0);
    }
}
