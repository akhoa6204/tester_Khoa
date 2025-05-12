package dataObjects;

import java.util.Date;

public class Ticket {
    String id, date, depart, arrive, type, amount;
    public Ticket(String date, String depart, String arrive, String type, String amount){
        this.date=date;
        this.depart =depart;
        this.arrive=arrive;
        this.type=type;
        this.amount=amount;
    }
    public Ticket(){};
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}


//in PageObject class:
//
//bookTicket(date, depart, arrive, type, amount) return ticketID;
//
//====> bookTicket(ticket)
//{
//    select(ticket.getDepart());
//....
//    setID(ticketID);
//}
//
//}
