package travels;

public class UserJourneyDetails {
    String emailID,source,destination,travelDate;
    int bill,seats;
    UserJourneyDetails(String emailID,String source,String destination,int seats,String travelDate,int bill)
    {
        this.destination=destination;
        this.source=source;
        this.travelDate= travelDate;
        this.seats=seats;
        this.emailID=emailID;
        this.bill=bill;
    }
}
