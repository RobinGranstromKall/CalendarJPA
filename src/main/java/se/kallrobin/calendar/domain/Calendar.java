package se.kallrobin.calendar.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Calendar {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Event> events = new ArrayList<>();

	public Calendar() {
		super();
	}

	public Calendar(String name) {
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

	public void addEvent(Event event){this.events.add(event);}

	//En toString på delarna (events) triggar inte uppläsning. Så vi får göra det lite mer komplicerat...
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append("Calendar{" +
				"id=" + id +
				", name='" + name);
        buffer.append("\nNr of events: " + getEvents().size());
		for (Event event : getEvents()) {
			buffer.append("\n[(" + event.getName() + "):");
			buffer.append(event);
			buffer.append("]");
		}
		buffer.append("}");
		return buffer.toString();
	}
}
