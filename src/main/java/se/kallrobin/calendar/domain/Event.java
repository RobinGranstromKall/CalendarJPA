package se.kallrobin.calendar.domain;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String info;
    private Date start;
    private Date stop;

    @ManyToOne
    private Calendar calendar;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private EventType eventType;

    public Event() {
    }

    public Event(String name, String info) {
        this.name = name;
        this.info = info;
        this.start = new Date();
        this.stop = new Date();
    }

    public Event(String name, String info, Date start, Date stop) {
        this.name = name;
        this.info = "No information was provided.";
        this.start = start;
        this.stop = stop;
    }

    public Event(String name, String info, Date start, Date stop, Calendar calendar, EventType eventType) {

        this.name = name;
        this.info = info;
        this.start = start;
        this.stop = stop;
        this.calendar = calendar;
        this.eventType = eventType;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Date getStart() {
        return start;
    }


    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", start=" + start +
                ", stop=" + stop +
                ", eventType=" + eventType +
                '}';
    }
}
