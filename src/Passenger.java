public class Passenger {
     String name;
     int age;
     String gender;
     String ticketId;
     String  preferredBerth;
     String allocatedBerth;

    Passenger(String name,int age,String gender,String ticketId,String preferredBerth,String allocatedBerth){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.ticketId = ticketId;
        this.preferredBerth = preferredBerth;
        this.allocatedBerth = allocatedBerth;
    }

    public String toString(){
        return "Ticket ID: " + ticketId + ", Name: " + name + ", Age: " + age +
                ", Gender: " + gender + ", Berth: " + allocatedBerth;
    }
}
