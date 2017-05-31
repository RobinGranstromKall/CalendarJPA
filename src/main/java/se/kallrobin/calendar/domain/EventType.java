package se.kallrobin.calendar.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dessa delar är i stort sett som originalet (efter mvn archetype:generate osv)
 */
@Entity
public class EventType {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private List<Event> events = new ArrayList<Event>();

    public EventType() {
        super();
    }

    public EventType(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
