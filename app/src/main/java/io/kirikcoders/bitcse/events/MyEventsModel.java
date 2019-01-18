package io.kirikcoders.bitcse.events;

public class MyEventsModel {

    private String name;
    private String participants;
    private String Phone;

    public MyEventsModel() {
    }

    public MyEventsModel(String name, String Participants, String Phone) {
        this.name = name;
        this.participants =Participants;
        this.Phone =Phone;
    }

    public  String getName() {
        return name;
    }

    public String getParticipants() { return participants;}

    public String getPhone() { return Phone;}

    public void setName(String name) {
        this.name = name;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }
}


