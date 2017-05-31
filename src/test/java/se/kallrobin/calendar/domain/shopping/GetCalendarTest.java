package se.kallrobin.calendar.domain.shopping;

import org.junit.Before;
import org.junit.Test;
import se.kallrobin.calendar.domain.Calendar;
import se.kallrobin.calendar.domain.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;


/**
 * Bara för att vi ska kunna se hur databasen ser ut (så inga egentliga tester...)
 */
public class GetCalendarTest {
    EntityManager manager;

    @Before
    public void setUp() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
    }

    @Test
    public void printGetAllCalendars() throws Exception {
        List<Calendar> calendars = getAllCalendars();
        for (Calendar calendar : calendars) {
            System.out.println(calendar);
        }
    }

    @Test
    public void printGetAllEvents() throws Exception {
        List<Event> events = getAllEvents();
        for (Event event : events) {
            System.out.println(event);
        }
    }

    private List<Calendar> getAllCalendars() {
        Query query = manager.createQuery("SELECT calendar FROM Calendar calendar", Calendar.class);
        return query .getResultList();
    }

    private List<Event> getAllEvents() {
        Query query = manager.createQuery("SELECT e FROM Event e", Event.class);
        return query .getResultList();
    }

}