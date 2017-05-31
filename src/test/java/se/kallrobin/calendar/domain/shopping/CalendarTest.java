package se.kallrobin.calendar.domain.shopping;

import org.junit.Before;
import org.junit.Test;
import se.kallrobin.calendar.domain.Calendar;
import se.kallrobin.calendar.domain.Event;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Enkla tester.
 * Vi gör inga rena enhetstester utan testar bara JPA-funktionaliteten.
 */
public class CalendarTest {
    EntityManager manager;

    @Before
    public void setUp() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        clear();
    }

    @Test
    public void testNoShoppinglists() throws Exception {
        List<Calendar> calendars = getAllCalendars();
        assertTrue(calendars.isEmpty());
    }

    @Test
    public void testEmptyShoppinglist() throws Exception {
        Calendar calendar = new Calendar("Lista 1");
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<Calendar> calendars = getAllCalendars();
        assertEquals(1L, calendars.size());
    }

    @Test
    public void testAddingAnItem() throws Exception {
        Calendar calendar = new Calendar("Lista 2");
        calendar.addEvent(new Event("item 1", "EventTest2"));
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<Calendar> calendars = getAllCalendars();
        assertEquals(1L, calendars.size());
        assertEquals(1L, calendars.get(0).getEvents().size());
    }

    @Test
    public void testAddingSeveralItem() throws Exception {
        Calendar calendar = new Calendar("Lista 2");
        addSomeItemsToCalendar(calendar);
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<Calendar> calendars = getAllCalendars();
        assertEquals(1L, calendars.size());
        assertEquals(5L, calendars.get(0).getEvents().size());
    }

    @Test
    public void testFinding() throws Exception {
        Calendar calendar = new Calendar("Lista 2");
        addSomeItemsToCalendar(calendar);
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<Calendar> calendars = getAllCalendars();
        assertEquals(1L, calendars.size());
        Long id = calendars.get(0).getId();
        Calendar found = manager.find(Calendar.class, id);
        assertEquals(id, found.getId());
    }

    @Test
    public void testRemovingItem() throws Exception {
        Calendar calendar = new Calendar("Lista 2");
        addSomeItemsToCalendar(calendar);
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        EntityTransaction tx2 = manager.getTransaction();
        List<Calendar> calendars = getAllCalendars();
        Calendar calendar1 = calendars.get(0);
        Event event = calendar1.getEvents().remove(0);
        tx2.begin();
        try {
            //Variant 1: manager.remove(event);
            manager.merge(calendar1);//Variant 2: men då behöver vi annotera items med orphanRemoval = true i Calendar
                    //Fast orphanRemoval anses samtidigt lite tveksamt
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx2.commit();
        List<Calendar> shoppingListsAgain = getAllCalendars();
        assertEquals(4L, shoppingListsAgain.get(0).getEvents().size());
    }

    @Test
    public void testRemovingShoppingList() throws Exception {
        Calendar calendar = new Calendar("Lista 3");
        calendar.addEvent(new Event("item 3.1", "Test333232"));
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        EntityTransaction tx2 = manager.getTransaction();
        tx2.begin();
        try {
            manager.remove(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx2.commit();
        List<Calendar> calendars = getAllCalendars();
        assertTrue(calendars.isEmpty());
    }

    private void addSomeItemsToCalendar(Calendar calendar) {
        List<Event> events = calendar.getEvents();
        for(int i = 1; i <= 5; i++) {
            events.add(new Event("item " + i, "eventTest"));
        }
    }

    private List<Calendar> getAllCalendars() {
        Query query = manager.createQuery("SELECT calendar FROM Calendars calendar", Calendar.class);
        return query.getResultList();
    }

    private void clear() {
        clearShoppingList();
    }

    private void clearShoppingList() {
        EntityTransaction tx = manager.getTransaction();
        List<Calendar> resultList = getAllCalendars();
        tx.begin();
        try {
            for (Calendar next : resultList) {
                manager.remove(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }
}