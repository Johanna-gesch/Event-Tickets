package dk.easv.eventtickets.BE;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String notes;
    private List<String> coordinators;
    private int eventId;

    public Event (String name, LocalDateTime startDateTime, String location, String notes, List<String> coordinators, int eventId) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.location = location;
        this.notes = notes;
        this.coordinators = coordinators;
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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

    public void setEventId(int eventId){
        this.eventId = eventId;
    };

    public int getEventId(){return eventId;}
}
