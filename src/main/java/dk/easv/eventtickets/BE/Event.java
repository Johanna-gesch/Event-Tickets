package dk.easv.eventtickets.BE;

import java.util.List;

public class Event {
    private String name;
    private String date;
    private String time;
    private String location;
    private String notes;
    private List<String> coordinators;

    public Event (String name, String date, String time, String location, String notes, List<String> coordinators) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.notes = notes;
        this.coordinators = coordinators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(List<String> coordinators) {
        this.coordinators = coordinators;
    }
}
